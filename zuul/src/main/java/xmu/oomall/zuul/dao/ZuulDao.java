package xmu.oomall.zuul.dao;

import cn.hutool.core.convert.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import xmu.oomall.zuul.domain.Privilege;
import xmu.oomall.zuul.mapper.ZuulMapper;
import xmu.oomall.zuul.util.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zty
 * Zuul dao 对象
 */
@Repository
public class ZuulDao {

    @Autowired
    ZuulMapper mapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Config config;

    /**
     * 获得权限表
     */
    public Map<String, List<Integer>> getAuthTable(String method) {

        Map<String, List<Integer>> map = Convert.convert(Map.class, redisTemplate.opsForValue().get(method));

        if (map != null) {
            return map;
        }

        map = new HashMap<>();

        List<Privilege> privileges = mapper.getAllPrivilegeByMethod(method);

        List<Integer> roleList = new ArrayList<>();
        try {
            for (Privilege privilege : privileges) {
                String url = privilege.getUrl();

                Integer roleId = privilege.getRoleId();

                roleList = map.get(url);
                if (roleList == null) {
                    roleList = new ArrayList<>();
                }
                roleList.add(roleId);

                map.put(url, roleList);
            }

            redisTemplate.opsForValue().set(method, map, config.getRedisExpireTime(), TimeUnit.HOURS);

            return map;
        } catch (Exception e) {
            return null;
        }
    }

}
