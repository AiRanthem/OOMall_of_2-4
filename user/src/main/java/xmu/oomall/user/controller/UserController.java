package xmu.oomall.user.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.user.controller.vo.*;
import xmu.oomall.user.domain.User;
import xmu.oomall.user.domain.UserPo;
import xmu.oomall.user.service.UserService;
import xmu.oomall.user.util.JwtUtil;
import xmu.oomall.user.util.LogUtil;
import xmu.oomall.user.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 *
 * @author 谢逸翔
 * @author 王一凡
 * @date Created in 21:12 2019/12/06
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private LogUtil logUtil;

    /**
     * tested
     * @param vo
     * @param response
     * @return
     */
    @PostMapping("/login")
    public Object login(@RequestBody LoginVo vo, HttpServletResponse response) {

        if (vo == null) {
            return ResponseUtil.badArgument();
        } else if (vo.getUsername() == null || vo.getPassword() == null) {
            return ResponseUtil.badArgumentValue();
        }

        System.out.println(vo);

        String username = vo.getUsername();
        String passWord = vo.getPassword();

        /**
         * 判断密码是否正确
         */
        if (!userService.validatePassWord(username, passWord)) {
            return ResponseUtil.errPassword();
        }

        UserPo userPo;
        try {
            userPo = userService.findUserByName(username);
        } catch (Exception e) {
            return ResponseUtil.usernameNotFound();
        }

        /**
         * 获取登录ip和登陆时间并更新数据库
         */
        try {
            userPo.setLastLoginIp(InetAddress.getLocalHost().toString());
            userPo.setLastLoginTime(LocalDateTime.now());
            User user = new User(userPo);
            user.setGmtModified(LocalDateTime.now());
            userService.updateUser(user);
        } catch (Exception e) {
            return ResponseUtil.updateUserFailed();
        }

        /**
         * jwt相关
         */
        String token = JwtUtil.generateToken(userPo.getName(), userPo.getRoleId(), userPo.getId());
        response.setHeader("authorization", token);

        HashMap<String, String> map = new HashMap<String, String>(3);
        map.put("token", token);
        map.put("tokenHead", "{alg:HS256,typ:JWT}");
        map.put("data", userPo.toString());

        return ResponseUtil.ok(map);
    }

    @PostMapping("/captcha")
    public Object captcha(@RequestBody String mobile, @RequestBody Integer type) {
        /**
         * todo
         * 目前做的是直接return 123456
         */

        return ResponseUtil.ok("123456");
    }

    /**
     * tested
     * @param vo
     * @param request
     * @return
     */
    @PutMapping("/password")
    public Object password(@RequestBody ResetPasswordVo vo, HttpServletRequest request) {

        if (vo == null) {
            return ResponseUtil.badArgument();
        } else if (vo.getCode() == null || vo.getTelephone() == null || vo.getPassword() == null) {
            return ResponseUtil.badArgumentValue();
        }

        String code = vo.getCode();
        String mobile = vo.getTelephone();
        String password = vo.getPassword();


        /**
         * 判断验证码是否正确
         * @param code 注册码
         */
        if (!userService.validateCode(code, mobile)) {
            return ResponseUtil.errCaptcha();
        }
        try {
            UserPo userPo = userService.findUserByMobile(mobile);
            userPo.setPassword(password);
            User user = new User(userPo);
            user.setGmtModified(LocalDateTime.now());

            if (userService.updateUser(user) == 0) {
                return ResponseUtil.updateUserFailed();
            }

            userPo.setPassword("null");

            return ResponseUtil.ok(userPo);
        } catch (Exception e) {
            return ResponseUtil.userNoPermission();
        }
    }

    /**
     * tested
     * @param vo
     * @param request
     * @return
     */
    @PutMapping("/phone")
    public Object phone(@RequestBody ResetPhoneVo vo, HttpServletRequest request) {

        if (vo == null) {
            return ResponseUtil.badArgument();
        } else if (vo.getCode() == null || vo.getNewTelephone() == null || vo.getPassword() == null || vo.getTelephone() == null) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            String id = request.getHeader("id");
            Integer userId = Convert.convert(Integer.class, id);
            String password = vo.getPassword();
            String newTelephone = vo.getNewTelephone();
            String telephone = vo.getTelephone();
            String code = vo.getCode();

            if (!userService.validateCode(code, telephone)) {
                return ResponseUtil.errCaptcha();
            }

            UserPo userPo = userService.findUserById(userId);
            String truePassword = userPo.getPassword();
            if (!truePassword.equals(password)) {
                return ResponseUtil.errPassword();
            }

            userPo.setMobile(newTelephone);
            User user = new User(userPo);
            user.setGmtModified(LocalDateTime.now());
            if (userService.updateUser(user) == 0) {
                return ResponseUtil.updateUserFailed();
            } else {
                return ResponseUtil.ok(userPo);
            }
        } catch (Exception e) {
            return ResponseUtil.userNoPermission();
        }
    }

    /**
     * tested
     * @param vo
     * @return
     */
    @PostMapping("/register")
    public Object register(@RequestBody UserRegisterVo vo) {

        if (vo == null) {
            return ResponseUtil.badArgument();
        }

        String username = vo.getUsername();
        String password = vo.getPassword();
        String code = vo.getCode();
//        Integer id;
//        /**
//         * 判断验证码是否正确
//         * @param code 注册码
//         */
//        if (!userService.validateCode(code, mobile)) {
//            return ResponseUtil.badArgumentValue();
//        }
        UserPo userPo = new UserPo();
        User user = new User(userPo);
        user.setName(username);
        user.setPassword(password);
        user.setRoleId(4);
        LocalDateTime localDateTime = LocalDateTime.now();
        user.setGmtCreate(localDateTime);
        user.setGmtModified(localDateTime);

        if (!userService.register(user)) {
            return ResponseUtil.usernameBeRegistered();
        }
        /**
         * 获取ID
         */
        user.setId(userService.findUserByName(username).getId());


        /**
         * jwt相关
         */
        String token = JwtUtil.generateToken(user.getName(), user.getRoleId(), user.getId());

        Long tokenExpire = JwtUtil.TOKEN_EXPIRE_TIME;
        Map<String, String> data = new HashMap<>(3);

        data.put("token", token);
        data.put("tokenHead", "{alg:HS256,typ:JWT}");
        data.put("userInfo", user.toString());
        System.out.println(data);

        return ResponseUtil.ok(data);
    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 修改用户信息结果
     */
    @PutMapping("/user")
    public Object updateUser(@RequestBody User user,HttpServletRequest request) {

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));
            logger.debug("updateUser参数： user=" + user);
            user.setGmtModified(LocalDateTime.now());
            logger.debug("updateUser处理后： user=" + user);

            Integer beSuccess = userService.updateUser(user);

            if (beSuccess == 1) {
                logger.debug("用户信息修改成功： user=" + user);
                logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),3,"修改",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.ok(user);
            } else {
                logger.debug("用户信息修改失败");
                logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),3,"修改",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.updateUserFailed();
            }

        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 根据HTTP Request中用户id获得用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/user/{id}")
    public Object userInfo(@PathVariable Integer id,HttpServletRequest request) {

        if (id == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            try {
                UserPo user = userService.findUserById(id);
                logger.debug("userInfo结果：" + user);
                logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                if (user == null) {
                    return ResponseUtil.findUserInfoFailed();
                } else {
                    return ResponseUtil.ok(user);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());

                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){

            return ResponseUtil.serious();
        }




    }

    /**
     * 获得所有用户
     *
     * @return 用户信息列表
     */
    @GetMapping("/users")
    public Object findAllUser(@RequestParam Integer page, @RequestParam Integer limit,HttpServletRequest request) {

        if (page == null || limit == null) {
            return ResponseUtil.badArgument();
        } else if (page <= 0 || limit <= 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            try {
                List<UserPo> userList = userService.getAllUser((page - 1) * limit, limit);
                logger.debug("userList结果：" + userList);
                logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                if (userList == null) {
                    return ResponseUtil.findUserInfoFailed();
                } else {
                    return ResponseUtil.ok(userList);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());

                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){

            return ResponseUtil.serious();
        }


    }

    /**
     * 删除用户
     *
     * @return 删除用户情况
     */
    @DeleteMapping("/user/{id}")
    public Object deleteUser(@PathVariable Integer id,HttpServletRequest request) {

        if (id == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            try {
                logger.debug("deleteUser参数：  id=" + id);
                Integer beSuccess = userService.deleteUser(id);
                if (beSuccess == 0) {
                    logger.debug("用户删除失败");
                    logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),3,"删除",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.updateUserFailed();
                } else {
                    logger.debug("用户删除成功");
                    logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),3,"删除",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.ok();
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),3,"删除",LocalDateTime.now(),LocalDateTime.now());

                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){

            return ResponseUtil.serious();
        }
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public Object logout() {
        logger.info("登出");
        HashMap<String, String> map = new HashMap<String, String>(3);
        map.put("token", "logout");
        return ResponseUtil.ok(map);
    }


    /**
     * @author zty
     * @param rebate
     * @param request
     * @return
     */
    @PutMapping("/user/rebate")
    public Object updateRebate(@RequestParam Integer rebate, HttpServletRequest request) {
        if (rebate == null) {
            return ResponseUtil.badArgument();
        } else if (rebate < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer userId = Convert.convert(Integer.class, jsonObject.get("userId"));

            UserPo userPo = userService.findUserById(userId);

            userPo.setRebate(rebate);

            User user = new User(userPo);
            user.setGmtModified(LocalDateTime.now());

            if (userService.updateUser(user) == 0) {
                return ResponseUtil.ok(0);
            } else {
                return ResponseUtil.ok(rebate);
            }

        } catch (Exception e) {
            return ResponseUtil.serious();
        }
    }

    @GetMapping("/user/validate")
    public Object validateUser(@RequestParam Integer userId) {
        UserPo user = userService.findUserById(userId);
        return ResponseUtil.ok(user != null);
    }
}
