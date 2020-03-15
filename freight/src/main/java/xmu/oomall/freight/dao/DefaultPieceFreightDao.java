package xmu.oomall.freight.dao;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import xmu.oomall.freight.domain.AddressPo;
import xmu.oomall.freight.domain.DefaultPieceFreight;
import xmu.oomall.freight.domain.DefaultPieceFreightPo;
import xmu.oomall.freight.mapper.DefaultPieceFreightMapper;
import xmu.oomall.freight.service.impl.FreightServiceImpl;
import xmu.oomall.freight.util.Config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * DefaultPieceFreight DAO 对象
 *
 * @author zty
 * @author 陆俊伟
 * @date Created in 23:44 2019/12/05
 */
@Repository
public class DefaultPieceFreightDao {

    private static final Logger logger = LoggerFactory.getLogger(FreightServiceImpl.class);

    @Autowired
    private DefaultPieceFreightMapper defaultPieceFreightMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Config config;

    /**
     * 获得所有计件运费规则
     *
     * @return 所有的计件运费规则列表
     */
    public List<DefaultPieceFreightPo> findAllDefaultPieceFreight(Integer offSet, Integer limit) {
        logger.debug("获得所有计件运费规则");
        List<DefaultPieceFreightPo> defaultPieceFreightPoList;
        Map<String, Integer> data = new HashMap<>();
        data.put("offSet", offSet);
        data.put("limit", limit);
        try {
            defaultPieceFreightPoList = defaultPieceFreightMapper.findAllDefaultPieceFreight(data);
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return null;
        }
        logger.debug("所有的计件运费规则为：" + defaultPieceFreightPoList);
        return defaultPieceFreightPoList;
    }

    /**
     * 获得某一条计件运费规则
     *
     * @param id 规则id
     * @return 计件运费规则对象
     */
    public DefaultPieceFreightPo findDefaultPieceFreightById(Integer id) {
        logger.debug("findDefaultPieceFreightById参数：" + id);
        String key = "DPFid" + id.toString();
        DefaultPieceFreightPo defaultPieceFreightPo = Convert.convert(DefaultPieceFreightPo.class, redisTemplate.opsForValue().get(key));
        if (defaultPieceFreightPo == null) {
            logger.debug("Redis中无DefaultPieceFreight对象：" + key);
            try {
                defaultPieceFreightPo = defaultPieceFreightMapper.findDefaultPieceFreightById(id);
            } catch (Exception e) {
                System.out.println("数据库中没有对象！");
                return null;
            }
            redisTemplate.opsForValue().set(key, defaultPieceFreightPo, config.getRedisExpireTime(), TimeUnit.MINUTES);
            logger.debug("Redis中存入DefaultPieceFreight对象：" + defaultPieceFreightPo);
        }
        return defaultPieceFreightPo;
    }

    /**
     * 通过目的地获取计件运费规则
     *
     * @param addressPo 目标地址
     * @return 计件运费规则对象
     */
    public DefaultPieceFreightPo findDefaultPieceFreightByDest(AddressPo addressPo) {
        logger.debug("需要查找运费模板的地址对象：" + addressPo);
        Integer destination = addressPo.getCountyId();
        DefaultPieceFreightPo defaultPieceFreightPo = null;
        String key = "DPFdest";
        if (destination == null) {
            destination = addressPo.getCityId();
            if (destination == null) {
                destination = addressPo.getProvinceId();
                if (destination == null) {
                    logger.debug("地址错误！");
                    return defaultPieceFreightPo;
                } else {
                    key = key + destination.toString();
                    defaultPieceFreightPo = Convert.convert(DefaultPieceFreightPo.class, redisTemplate.opsForValue().get(key));
                }
            } else {
                key = key + destination.toString();
                defaultPieceFreightPo = Convert.convert(DefaultPieceFreightPo.class, redisTemplate.opsForValue().get(key));
            }
        } else {
            key = key + destination.toString();
            defaultPieceFreightPo = Convert.convert(DefaultPieceFreightPo.class, redisTemplate.opsForValue().get(key));
        }
        if (defaultPieceFreightPo == null) {
            logger.debug("Redis中没有DefaultPieceFreight对象");
            List<DefaultPieceFreightPo> defaultPieceFreightPoList;
            try {
                Map<String, Integer> data = new HashMap<>();
                data.put("offSet", 0);
                data.put("limit", 1000000);
                defaultPieceFreightPoList = defaultPieceFreightMapper.findAllDefaultPieceFreight(data);
            } catch (Exception e) {
                System.out.println("数据库中没有对象！");
                return null;
            }
            for (DefaultPieceFreightPo pieceFreightPo : defaultPieceFreightPoList) {
                try {
                    String dest = pieceFreightPo.getDestination();
                    JSONObject jsonObject = JSONUtil.parseObj(dest);
                    List<Integer> destList = jsonObject.getJSONArray("dest").toList(Integer.class);
                    if (destList.contains(destination)) {
                        defaultPieceFreightPo = pieceFreightPo;
                        break;
                    }
                } catch (Exception e) {
                    logger.error(e.getCause().getMessage());
                }
            }
            redisTemplate.opsForValue().set(key, defaultPieceFreightPo, config.getRedisExpireTime(), TimeUnit.MINUTES);
            logger.debug("Redis中存入DefaultPieceFreight = " + defaultPieceFreightPo);
        }
        return defaultPieceFreightPo;
    }

    /**
     * 添加新的运费规则
     *
     * @param defaultPieceFreight 新的运费规则
     * @return success
     */
    public Integer addDefaultPieceFreight(DefaultPieceFreight defaultPieceFreight) {
        logger.debug("addDefaultPieceFreight参数：" + defaultPieceFreight);
        Integer success;

        try {
            success = defaultPieceFreightMapper.addDefaultPieceFreight(defaultPieceFreight);
        } catch (Exception e) {
            System.out.println("数据插入数据库失败！");
            return 0;
        }

        logger.debug("DefaultPieceFreight添加成功：" + success);
        return success;
    }

    /**
     * 修改某条运费规则
     *
     * @param defaultPieceFreight 新的运费规则
     * @return success
     */
    public Integer updateDefaultPieceFreight(DefaultPieceFreight defaultPieceFreight) {
        logger.debug("updateDefaultPieceFreight参数：" + defaultPieceFreight);
        Integer success;

        try {
            success = defaultPieceFreightMapper.updateDefaultPieceFreight(defaultPieceFreight);
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return 0;
        }

        if (success > 0) {
            String key = "DPFid" + defaultPieceFreight.getId().toString();
            DefaultPieceFreightPo defaultPieceFreightPo = defaultPieceFreightMapper.findDefaultPieceFreightById(defaultPieceFreight.getId());
            redisTemplate.opsForValue().set(key, defaultPieceFreightPo, config.getRedisExpireTime(), TimeUnit.MINUTES);
        }

        logger.debug("规则更新成功：" + success);
        return success;
    }

    /**
     * 删除选定运费规则
     *
     * @param defaultPieceFreightId 选定的运费规则的id
     * @return success
     */
    public Integer deleteDefaultPieceFreight(Integer defaultPieceFreightId) {
        logger.debug("deleteDefaultPieceFreight参数： addressIdList=" + defaultPieceFreightId);
        Integer success;
        Map<String, Object> data = new HashMap<>();
        data.put("id", defaultPieceFreightId);
        LocalDateTime localDateTime = LocalDateTime.now();
        data.put("gmtModified", localDateTime);
        try {
            success = defaultPieceFreightMapper.deleteDefaultPieceFreight(data);
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return null;
        }
        logger.debug("规则删除成功：" + success);
        return success;
    }
}
