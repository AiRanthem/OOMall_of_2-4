package xmu.oomall.user.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.user.controller.vo.LoginVo;
import xmu.oomall.user.controller.vo.ResetPasswordVo;
import xmu.oomall.user.domain.Admin;
import xmu.oomall.user.domain.AdminPo;
import xmu.oomall.user.service.AdminService;
import xmu.oomall.user.util.JwtUtil;
import xmu.oomall.user.util.LogUtil;
import xmu.oomall.user.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * 管理员Controller
 * @author 谢逸翔
 * @date: Created in 21:12 2019/12/06
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LogUtil logUtil;

    /**
     * 登出
     * @return
     */
    @PostMapping("/admin/logout")
    public Object logout() {
        HashMap<String, String> map = new HashMap<String, String>(3);
        map.put("token", "logout");
        return ResponseUtil.ok(map);
    }

    /**
     * tested
     * @param vo
     * @param response
     * @return
     */
    @PostMapping("/admin/login")
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
        if (!adminService.validatePassWord(username, passWord)) {

            return ResponseUtil.errPassword();
        }
        AdminPo admin;
        try {
            admin = adminService.getAdminByName(username);
        } catch (Exception e) {
            return ResponseUtil.serious();
        }

        /**
         * jwt相关
         */
        String token = JwtUtil.generateToken(admin.getUsername(), admin.getRoleId(), admin.getId());
        response.setHeader("authorization", token);

        HashMap<String, String> map = new HashMap<String, String>(3);
        map.put("token", token);
        map.put("tokenHead", "{alg:HS256,typ:JWT}");
        map.put("data", admin.toString());

        return ResponseUtil.ok(map);
    }

    /**
     * tested
     * @param vo
     * @param request
     * @returns
     */
    @PutMapping("/admin/password")
    public Object password(@RequestBody ResetPasswordVo vo, HttpServletRequest request) {

        if (vo == null) {
            return ResponseUtil.badArgument();
        } else if (vo.getPassword() == null) {
            return ResponseUtil.badArgumentValue();
        }


        String password = vo.getPassword();

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("data"));

            Integer adminId = Convert.convert(Integer.class, jsonObject.get("adminId"));

            AdminPo adminPo = adminService.getAdminById(adminId);

            adminPo.setPassword(password);
            Admin admin = new Admin(adminPo);
            admin.setGmtModified(LocalDateTime.now());

            if (adminService.updateAdmin(admin) == 0) {
                return ResponseUtil.updateAdminFailed();
            }
            return ResponseUtil.ok(admin);
        } catch (Exception e) {

            return ResponseUtil.adminNoPermission();
        }
    }

    /**
     * 添加管理员
     * @param adminPo 管理员信息
     * @return 新管理员
     */
    @PostMapping("/admin")
    public Object createAdmin(@RequestBody AdminPo adminPo,HttpServletRequest request) {

        if (adminPo == null) {
            return ResponseUtil.badArgument();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            logger.debug("createAdmin参数： admin=" + adminPo);

            Admin admin = new Admin(adminPo);
            admin.setGmtCreate(LocalDateTime.now());
            admin.setGmtModified(LocalDateTime.now());
            logger.debug("createAdmin处理后： admin=" + admin);

            try {
                Integer beSuccess = adminService.addAdmin(admin);
                if (beSuccess == 0) {
                    logger.debug("管理员增加失败");
                    logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.insertAdminFailed();
                } else {
                    logger.debug("管理员添加成功： admin=" + admin);
                    logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.ok(adminPo);
                }
            } catch (Exception e) {
                return ResponseUtil.operaterFailed();
            }


        }catch (Exception e){

            return ResponseUtil.serious();
        }



    }

    /**
     * 修改管理员信息
     * @param id 管理员id
     * @param adminPo 新的管理员信息
     * @return 修改后的管理员信息
     */
    @PutMapping("/admin/{id}")
    public Object updateAdmin(@PathVariable Integer id, @RequestBody AdminPo adminPo) {

        if (adminPo == null||id==null) {
            return ResponseUtil.badArgument();
        } else if (adminPo.getId() == null || id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            logger.debug("updateAdmin参数： id=" + id + ", admin=" + adminPo);

            Admin admin = new Admin(adminPo);
            admin.setId(id);
            admin.setGmtModified(LocalDateTime.now());
            logger.debug("updateAdmin处理后： id=" + id + ", admin=" + admin);

            try {
                Integer beSuccess = adminService.updateAdmin(admin);
                if (beSuccess == 0) {
                    logger.debug("管理员信息修改失败");
                    logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.updateAdminFailed();
                } else {
                    logger.debug("管理员信息修改成功： admin=" + adminService.getAdminById(id));
                    logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.ok(adminService.getAdminById(id));
                }
            } catch (Exception e) {
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){

            return ResponseUtil.serious();
        }



    }

    /**
     * 根据id获得管理员信息
     * @param id 管理员id
     * @return 管理员信息
     */
    @GetMapping("/admin/{id}")
    public Object getAdminInfo(@PathVariable Integer id) {

        if (id == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            logger.debug("getAdminInfo参数：" + id);

            try {
                AdminPo admin = adminService.getAdminById(id);
                logger.debug("getAdminInfo结果：" + admin);
                logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                if (admin == null) {
                    return ResponseUtil.adminNameNotFound();
                } else {
                    return ResponseUtil.ok(admin);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.serious();
            }

        }catch (Exception e){

            return ResponseUtil.serious();
        }
    }

    /**
     * 获得所有拥有特定名字的管理员
     * @return 管理员信息
     */
    @GetMapping("/admin")
    public Object adminList(@RequestParam String adminName, @RequestParam Integer page, @RequestParam Integer limit) {

        if (page == null || limit == null) {
            return ResponseUtil.badArgument();
        } else if (page <= 0 || limit <= 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            try {
                List<AdminPo> adminList = adminService.getAllAdminByName((page - 1) * limit, limit, adminName);
                logger.debug("adminList返回结果：" + adminList);
                logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());
                if (adminList == null) {
                    return ResponseUtil.adminNameNotFound();
                } else {
                    return ResponseUtil.ok(adminList);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.serious();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 删除管理员
     * @return 删除管理员情况
     */
    @DeleteMapping("/admin/{id}")
    public Object deleteAdmin(@PathVariable Integer id) {

        if (id == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            logger.debug("deleteAdmin参数：  id=" + id);
            try {
                Integer beSuccess = adminService.deleteAdmin(id);

                if (beSuccess == 0) {
                    logger.debug("管理员删除失败");
                    logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),3,"删除",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.deleteAdminFailed();
                } else {
                    logger.debug("管理员删除成功");
                    logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),3,"删除",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.ok();
                }
            } catch (Exception e) {
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

}
