package xmu.oomall.freight.dao;

import cn.hutool.core.convert.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import xmu.oomall.freight.domain.SpecialFreight;
import xmu.oomall.freight.domain.SpecialFreightPo;
import xmu.oomall.freight.mapper.SpecialFreightMapper;
import xmu.oomall.freight.service.impl.FreightServiceImpl;
import xmu.oomall.freight.util.Config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * SpecialFreight Dao 对象
 *
 * @author 陆俊伟
 * @date Created in 23:32 2019/12/11
 **/
@Repository
public class SpecialFreightDao {

    private static final Logger logger = LoggerFactory.getLogger(FreightServiceImpl.class);

    @Autowired
    private SpecialFreightMapper specialFreightMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Config config;

    /**
     * 获得所有特殊运费规则
     *
     * @return 所有的特殊运费规则列表
     */
    public List<SpecialFreightPo> findAllSpecialFreight(Integer offSet, Integer limit) {
        logger.debug("获得所有特殊运费规则");
        List<SpecialFreightPo> specialFreightPoList;
        Map<String, Integer> data = new HashMap<>();
        data.put("offSet", offSet);
        data.put("limit", limit);
        try {
            specialFreightPoList = specialFreightMapper.findAllSpecialFreight(data);
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return null;
        }
        logger.debug("所有的特殊运费规则为：" + specialFreightPoList);
        return specialFreightPoList;
    }

    /**
     * 获得某一条特殊运费规则
     *
     * @param id 规则id
     * @return 特殊运费规则对象
     */
    public SpecialFreightPo findSpecialFreightById(Integer id) {
        logger.debug("findSpecialFreightById参数：" + id);
        String key = "SFid" + id.toString();
        SpecialFreightPo specialFreightPo = Convert.convert(SpecialFreightPo.class, redisTemplate.opsForValue().get(key));
        if (specialFreightPo == null) {
            logger.debug("Redis中无SpecialFreight对象：" + key);
            try {
                specialFreightPo = specialFreightMapper.findSpecialFreightById(id);
            } catch (Exception e) {
                System.out.println("数据库中没有对象！");
                return null;
            }
            redisTemplate.opsForValue().set(key, specialFreightPo, config.getRedisExpireTime(), TimeUnit.MINUTES);
            logger.debug("Redis中存入SpecialFreight对象：" + specialFreightPo);
        }
        return specialFreightPo;
    }

    /**
     * 添加新的运费规则
     *
     * @param specialFreight 新的运费规则
     * @return success
     */
    public Integer addSpecialFreight(SpecialFreight specialFreight) {
        logger.debug("addSpecialFreight参数：" + specialFreight);
        Integer success = 0;
        try {
            success = specialFreightMapper.addSpecialFreight(specialFreight);
        } catch (Exception e) {
            System.out.println("数据插入数据库失败！");
            return 0;
        }
        logger.debug("SpecialFreight添加成功：" + success);
        return success;
    }

    /**
     * 修改某条运费规则
     *
     * @param specialFreight 新的运费规则
     * @return success
     */
    public Integer updateSpecialFreight(SpecialFreight specialFreight) {
        logger.debug("updateSpecialFreight参数：" + specialFreight);
        Integer success;
        try {
            success = specialFreightMapper.updateSpecialFreight(specialFreight);
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return 0;
        }
        String key = "SFid" + specialFreight.getId().toString();
        SpecialFreightPo specialFreightPo = specialFreightMapper.findSpecialFreightById(specialFreight.getId());
        redisTemplate.opsForValue().set(key, specialFreightPo, config.getRedisExpireTime(), TimeUnit.MINUTES);
        logger.debug("规则更新成功：" + success);
        return success;
    }

    /**
     * 删除选定运费规则
     *
     * @param specialFreightId 选定的运费规则的id
     * @return success
     */
    public Integer deleteSpecialFreight(Integer specialFreightId) {
        logger.debug("deleteSpecialFreight参数： addressIdList=" + specialFreightId);
        Integer success;
        Map<String, Object> data = new HashMap<>();
        data.put("id", specialFreightId);
        LocalDateTime localDateTime = LocalDateTime.now();
        data.put("gmtModified", localDateTime);
        try {
            success = specialFreightMapper.deleteSpecialFreight(data);
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return null;
        }
        logger.debug("规则删除成功：" + success);
        return success;
    }
}
