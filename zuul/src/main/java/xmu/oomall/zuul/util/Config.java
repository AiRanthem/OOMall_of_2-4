package xmu.oomall.zuul.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 配置信息
 * @author Jason Lu
 * @date Created in 20:14 2019/11/17
 */
@Component
@Configuration
public class Config {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){ return new RestTemplate();}

    /**
     * 最大付款间隔,，单位分钟
     */
    @Value("${oomall.maxpaytime}")
    private Integer maxPayTime;

    /**
     * redis缓存失效时间，单位分钟
     */
    @Value("${oomall.redisexpiretime}")
    private Integer redisExpireTime;

    /**
     * 预提库存数量
     */
    @Value("${oomall.predeductQty}")
    private Integer preDeductQty;

    public Integer getMaxPayTime(){
        return this.maxPayTime;
    }

    public Integer getRedisExpireTime() {
        return redisExpireTime;
    }

    public Integer getPreDeductQty() {
        return preDeductQty;
    }
}
