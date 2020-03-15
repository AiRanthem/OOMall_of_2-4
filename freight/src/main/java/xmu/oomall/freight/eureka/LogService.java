package xmu.oomall.freight.eureka;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import xmu.oomall.freight.domain.Log;

/**
 * Feign接口
 * @author 王一凡
 * @date Created in 11:12 2019/12/18
 */

@FeignClient(name = "LOGSERVICE")
public interface LogService {

    @PostMapping("/log")
    Object writeLog(@RequestBody Log log);
}
