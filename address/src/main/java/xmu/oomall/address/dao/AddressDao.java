package xmu.oomall.address.dao;

import cn.hutool.core.convert.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.address.domain.Region;
import xmu.oomall.address.mapper.AddressMapper;
import xmu.oomall.address.mapper.RegionMapper;
import xmu.oomall.address.service.impl.AddressServiceImpl;
import xmu.oomall.address.util.Config;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 地址Dao & 地区Dao
 *
 * @author 陆俊伟
 * @date Created in 15:50 2019/12/05
 **/
@Repository
public class AddressDao {

    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Config config;

    /**
     * 添加地址
     *
     * @param address 地址
     * @return 新地址，带id的
     */
    public Integer addAddress(Address address) {
        logger.debug("addAddress参数： address=" + address);
        try {
            Integer success = addressMapper.insertAddress(address);
            logger.debug("地址增加成功：" + success);
            return success;
        } catch (Exception e) {
            System.out.println("数据插入数据库失败！");
            return 0;
        }
    }

    /**
     * 用户获取地址列表
     *
     * @param userId 用户id
     * @return 地址列表
     */
    public List<AddressPo> getAllAddress(Integer userId, Integer offSet, Integer limit) {
        logger.debug("getAllAddress参数： userId=" + userId);
        String key = "uId" + userId.toString() + offSet.toString() + limit.toString();
        List<String> resultList = Convert.convert(List.class, redisTemplate.opsForValue().get(key));
        List<AddressPo> addressPoList = new ArrayList<AddressPo>();

        Map<String, Integer> data = new HashMap<>(3);
        data.put("userId", userId);
        data.put("offSet", offSet);
        data.put("limit", limit);

        if (resultList != null) {
            for (int i = 0; i < resultList.size(); i++) {
                addressPoList.add(Convert.convert(AddressPo.class, resultList.get(i)));
            }
        } else {
            logger.debug("Redis中无address对象" + key);
            try {
                addressPoList = addressMapper.getAllAddress(data);
                redisTemplate.opsForValue().set(key, addressPoList, config.getRedisExpireTime(), TimeUnit.MINUTES);
                logger.debug("Redis中存入 address = " + addressPoList);
            } catch (Exception e) {
                System.out.println("数据库中没有对象！");
                return null;
            }
        }
        return addressPoList;
    }

    /**
     * 获取某条地址详细信息
     *
     * @param id 地址id
     * @return 地址信息
     */
    public AddressPo getAddressById(Integer id) {
        logger.debug("getAddressById参数： id=" + id);
        String key = "aId" + id.toString();
        AddressPo addressPo = Convert.convert(AddressPo.class, redisTemplate.opsForValue().get(key));
        if (addressPo == null) {
            logger.debug("Redis中无address对象");
            try {
                addressPo = addressMapper.getAddressById(id);
                redisTemplate.opsForValue().set(key, addressPo, config.getRedisExpireTime(), TimeUnit.MINUTES);
                logger.debug("Redis中存入 address = " + addressPo);
            } catch (Exception e) {
                System.out.println("数据库中没有对象！");
                return null;
            }
        }
        return addressPo;
    }

    /**
     * 修改某条地址
     *
     * @param address 新的地址信息
     * @return success
     */
    public Integer updateAddress(Address address) {
        logger.debug("updateAddress参数： address=" + address);
        try {
            Integer success = addressMapper.updateAddress(address);
            logger.debug("地址修改成功：" + success);
            String key = "aId" + address.getId().toString();
            AddressPo addressPo = addressMapper.getAddressById(address.getId());
            redisTemplate.opsForValue().set(key, addressPo, config.getRedisExpireTime(), TimeUnit.MINUTES);
            return success;
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return 0;
        }
    }

    /**
     * 删除地址
     *
     * @param addressId 要删除的地址的id列表
     * @return success
     */
    public Integer deleteAddress(Integer addressId) {
        logger.debug("deleteAddress参数： addressId=" + addressId);
        LocalDateTime localDateTime = LocalDateTime.now();
        Map<String, Object> data = new HashMap<>(2);
        data.put("id", addressId);
        data.put("gmtModified", localDateTime);
        try {
            Integer success = addressMapper.deleteAddress(data);
            logger.debug("地址删除成功：" + success);
            return success;
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return 0;
        }
    }

    /**
     * 根据父级地区id获取所有的子级地区
     *
     * @param pid 父级地区id
     * @return region对象列表
     */
    public List<Region> getRegionsByPid(Integer pid) {
        logger.debug("getRegionByPid参数： pid=" + pid);
        String key = "rPid" + pid.toString();
        List<String> resultList = Convert.convert(List.class, redisTemplate.opsForValue().get(key));
        List<Region> regionList = new ArrayList<Region>();
        if (resultList != null) {
            for (int i = 0; i < resultList.size(); i++) {
                regionList.add(Convert.convert(Region.class, resultList.get(i)));
            }
        } else {
            logger.debug("Redis中无region对象");
            try {
                regionList = regionMapper.getRegionsByPid(pid);
                redisTemplate.opsForValue().set(key, regionList, config.getRedisExpireTime(), TimeUnit.MINUTES);
                logger.debug("Redis中存入 region = " + regionList);
            } catch (Exception e) {
                System.out.println("数据库中没有对象！");
                return null;
            }
        }
        return regionList;
    }

    /**
     * 根据地区id获取地区对象
     *
     * @param id 地区id
     * @return region对象
     */
    public Region getRegionById(Integer id) {
        logger.debug("getRegionByPid参数： id=" + id);
        String key = "rId" + id.toString();
        Region region = Convert.convert(Region.class, redisTemplate.opsForValue().get(key));
        if (region == null) {
            logger.debug("Redis中无region对象");
            try {
                region = regionMapper.getRegionById(id);
                redisTemplate.opsForValue().set(key, region, config.getRedisExpireTime(), TimeUnit.MINUTES);
                logger.debug("Redis中存入 region = " + region);
            } catch (Exception e) {
                System.out.println("数据库中没有对象！");
                return null;
            }
        }
        return region;
    }
}
