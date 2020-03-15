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
import xmu.oomall.freight.domain.DefaultFreight;
import xmu.oomall.freight.domain.DefaultFreightPo;
import xmu.oomall.freight.mapper.DefaultFreightMapper;
import xmu.oomall.freight.service.impl.FreightServiceImpl;
import xmu.oomall.freight.util.Config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * DefaultFreight DAO 对象
 * @author 陆俊伟
 * @date Created in 15:50 2019/12/05
 */
@Repository
public class DefaultFreightDao {

    private static final Logger logger = LoggerFactory.getLogger(FreightServiceImpl.class);

    @Autowired
    private DefaultFreightMapper defaultFreightMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Config config;

    /**
     * 获得所有默认运费规则
     *
     * @return 所有的默认运费规则列表
     */
    public List<DefaultFreightPo> findAllDefaultFreight(Integer offSet, Integer limit) {
        logger.debug("获得所有默认运费规则");
        Map<String, Integer> data = new HashMap<>();
        data.put("offSet", offSet);
        data.put("limit", limit);
        try {
            List<DefaultFreightPo> defaultFreightListPo = defaultFreightMapper.findAllDefaultFreight(data);
            logger.debug("所有的默认运费规则为：" + defaultFreightListPo);
            return defaultFreightListPo;
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return null;
        }
    }

    /**
     * 获得莫一条默认运费规则
     *
     * @param id 规则id
     * @return 默认运费规则对象
     */
    public DefaultFreightPo findDefaultFreightById(Integer id) {
        logger.debug("findDefaultFreightById参数：" + id);
        String key = "DFid" + id.toString();
        DefaultFreightPo defaultFreightPo = Convert.convert(DefaultFreightPo.class, redisTemplate.opsForValue().get(key));
        if (defaultFreightPo == null) {
            logger.debug("Redis中无defaultFreight对象：" + key);
            try {
                defaultFreightPo = defaultFreightMapper.findDefaultFreightById(id);
            } catch (Exception e) {
                System.out.println("数据库中没有对象！");
                return null;
            }
            redisTemplate.opsForValue().set(key, defaultFreightPo, config.getRedisExpireTime(), TimeUnit.MINUTES);
            logger.debug("Redis中存入defaultFreight对象：" + defaultFreightPo);
        }
        return defaultFreightPo;
    }

    /**
     * 通过目的地获取默认运费规则
     *
     * @param addressPo 目标地址
     * @return 默认运费规则对象
     */
    public DefaultFreightPo findDefaultFreightByDest(AddressPo addressPo) {
        logger.debug("需要查找运费模板的地址对象：" + addressPo);
        Integer destination = addressPo.getCountyId();
        DefaultFreightPo defaultFreightPo = null;
        String key = "DFdest";
        if (destination == null) {
            destination = addressPo.getCityId();
            if (destination == null) {
                destination = addressPo.getProvinceId();
                if (destination == null) {
                    logger.debug("地址错误！");
                    return null;
                } else {
                    key = key + destination.toString();
                    defaultFreightPo = Convert.convert(DefaultFreightPo.class, redisTemplate.opsForValue().get(key));
                }
            } else {
                key = key + destination.toString();
                defaultFreightPo = Convert.convert(DefaultFreightPo.class, redisTemplate.opsForValue().get(key));
            }
        } else {
            key = key + destination.toString();
            defaultFreightPo = Convert.convert(DefaultFreightPo.class, redisTemplate.opsForValue().get(key));
        }
        if (defaultFreightPo == null) {
            logger.debug("Redis中没有defaultFreight对象");
            List<DefaultFreightPo> defaultFreightPoList;
            try {
                Map<String, Integer> data = new HashMap<>();
                data.put("offSet", 0);
                data.put("limit", 100000);
                defaultFreightPoList = defaultFreightMapper.findAllDefaultFreight(data);
            } catch (Exception e) {
                System.out.println("数据库中没有对象！");
                return null;
            }
            for (DefaultFreightPo freight : defaultFreightPoList) {
                try {
                    String dest = freight.getDestination();
                    JSONObject jsonObject = JSONUtil.parseObj(dest);
                    List<Integer> destList = jsonObject.getJSONArray("dest").toList(Integer.class);
                    if (destList.contains(destination)) {
                        defaultFreightPo = freight;
                        break;
                    }
                } catch (Exception e) {
                    logger.error(e.getCause().getMessage());
                }
            }
            redisTemplate.opsForValue().set(key, defaultFreightPo, config.getRedisExpireTime(), TimeUnit.MINUTES);
            logger.debug("Redis中存入defaultFreight = " + defaultFreightPo);
        }
        return defaultFreightPo;
    }

    /**
     * 添加新的运费规则
     *
     * @param defaultFreight 新的运费规则
     * @return success
     */
    public Integer addDefaultFreight(DefaultFreight defaultFreight) {
        logger.debug("addDefaultFreight参数：" + defaultFreight);
        Integer success;
        try {
            success = defaultFreightMapper.addDefaultFreight(defaultFreight);
        } catch (Exception e) {
            System.out.println("数据插入数据库失败！");
            return 0;
        }
        logger.debug("defaultFreight添加成功：" + success);
        return success;
    }

    /**
     * 修改某条运费规则
     *
     * @param defaultFreight 新的运费规则
     * @return success
     */
    public Integer updateDefaultFreight(DefaultFreight defaultFreight) {
        logger.debug("updateDefaultFreight参数：" + defaultFreight);
        Integer success;
        try {
            success = defaultFreightMapper.updateDefaultFreight(defaultFreight);
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return 0;
        }
        if (success > 0) {
            String key = "DFid" + defaultFreight.getId().toString();
            DefaultFreightPo defaultFreightPo = defaultFreightMapper.findDefaultFreightById(defaultFreight.getId());
            redisTemplate.opsForValue().set(key, defaultFreightPo, config.getRedisExpireTime(), TimeUnit.MINUTES);
        }
        logger.debug("规则更新成功：" + success);
        return success;
    }

    /**
     * 删除选定运费规则
     *
     * @param defaultFreightId 选定的运费规则的id
     * @return success
     */
    public Integer deleteDefaultFreight(Integer defaultFreightId) {
        logger.debug("deleteDefaultFreight参数： addressIdList=" + defaultFreightId);
        Integer success;
        LocalDateTime localDateTime = LocalDateTime.now();
        Map<String, Object> data = new HashMap<>();
        data.put("gmtModified", localDateTime);
        data.put("id", defaultFreightId);
        try {
            success = defaultFreightMapper.deleteDefaultFreight(data);
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return 0;
        }
        logger.debug("规则删除成功：" + success);
        return success;
    }
}
