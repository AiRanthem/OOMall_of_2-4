package xmu.oomall.user.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.user.domain.Log;
import xmu.oomall.user.eureka.LogService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @program: oomall.ver 1.1
 * @description: 包装log
 * @version：
 * @author: Mr.Wang
 * @create: 2019-12-18 14:25
 **/

@Repository
public class LogUtil {
    @Autowired
    LogService logService;

    public void addLogInfoSuccess(HttpServletRequest request, Integer adminId, String ip, Integer type, String actions, LocalDateTime gmtCreate, LocalDateTime gmtModified){

        try{
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer roleId = Convert.convert(Integer.class, jsonObject.get("roleId"));
            if(roleId != 0 && roleId != 4){
                Log log = new Log();
                log.setAdminId(adminId);
                log.setIp(ip);
                log.setType(type);
                log.setActions(actions);
                log.setGmtCreate(gmtCreate);
                log.setStatusCode(1);
                log.setGmtModified(gmtModified);
                logService.writeLog(log);
            }else {
                return;
            }
        }catch (Exception e){
            return;
        }

    }

    public void addLogInfoFail(HttpServletRequest request,Integer adminId,String ip,Integer type,String actions,LocalDateTime gmtCreate,LocalDateTime gmtModified){
        try{
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer roleId = Convert.convert(Integer.class, jsonObject.get("roleId"));
            if(roleId != 0 && roleId != 4){
                Log log = new Log();
                log.setAdminId(adminId);
                log.setIp(ip);
                log.setType(type);
                log.setActions(actions);
                log.setGmtCreate(gmtCreate);
                log.setStatusCode(0);
                log.setGmtModified(gmtModified);
                logService.writeLog(log);
            }else {
                return;
            }
        }catch (Exception e){
            return;
        }
    }

}
