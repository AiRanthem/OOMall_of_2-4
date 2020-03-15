package xmu.oomall.user.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.user.domain.Privilege;
import xmu.oomall.user.domain.Role;
import xmu.oomall.user.service.PrivilegeService;
import xmu.oomall.user.util.LogUtil;
import xmu.oomall.user.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限Controller
 *
 * @author 陆俊伟
 * @date Created in 13:32 2019/12/18
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class PrivilegeController {

    private static final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private LogUtil logUtil;

    /**
     * 获得所有角色
     *
     * @return List<Role>
     */
    @GetMapping("/roles")
    public Object findAllRoles(HttpServletRequest request) {
        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            try {
                logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                List<Role> roleList = privilegeService.findAllRoles();
                if (roleList == null) {
                    return ResponseUtil.findRoleFailed();
                } else {
                    return ResponseUtil.ok(roleList);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 添加角色
     *
     * @param name 角色名
     * @return 角色对象
     */
    @PostMapping("/roles")
    public Object addRole(@RequestBody String name, HttpServletRequest request) {
        if (name == null) {
            return ResponseUtil.badArgument();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            Role role = new Role();
            role.setName(name);
            LocalDateTime localDateTime = LocalDateTime.now();
            role.setGmtCreate(localDateTime);
            role.setGmtModified(localDateTime);

            try {
                Integer success = privilegeService.addRole(role);
                if (success == 0) {
                    logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.updateRoleFailed();
                } else {
                    role.setBeDeleted(false);
                    logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.ok(role);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }


    }

    /**
     * 修改角色
     *
     * @param id      id
     * @param name    角色名
     * @param request
     * @return role
     */
    @PutMapping("/roles/{id}")
    public Object updateRole(@PathVariable Integer id, @RequestBody String name, HttpServletRequest request) {
        if (id == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));


            try {
                Role role = privilegeService.findRoleById(id);
                if (role == null) {
                    logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.findRoleFailed();
                }
                role.setName(name);
                role.setGmtModified(LocalDateTime.now());
                logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());

                Integer success = privilegeService.updateRole(role);
                if (success == 0) {
                    return ResponseUtil.updateRoleFailed();
                } else {
                    return ResponseUtil.ok(role);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());

                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 删除指定角色
     *
     * @param id      id
     * @param request
     * @return 无
     */
    @DeleteMapping("/roles/{id}")
    public Object deleteRole(@PathVariable Integer id, HttpServletRequest request) {
        if (id == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));


            try {
                if (privilegeService.deleteRole(id) == 0) {
                    logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),3,"删除",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.deleteRoleFailed();
                } else {
                    logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),3,"删除",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.ok();
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),3,"删除",LocalDateTime.now(),LocalDateTime.now());

                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }


    }

    /**
     * 获得指定角色的所有权限
     * @param id roleId
     * @param request
     * @return 权限列表
     */
    @GetMapping("roles/{id}/privileges")
    public Object findAllPrivilegesByRoleId(@PathVariable Integer id, HttpServletRequest request) {
        if (id == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));
            try {
                logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());

                List<Privilege> privilegeList = privilegeService.findAllPrivilegesByRoleId(id);
                if (privilegeList == null) {
                    return ResponseUtil.findRoleFailed();
                } else {
                    return ResponseUtil.ok(privilegeList);
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
     * 修改角色权限
     * @param id roleId
     * @param privilegeList 权限列表
     * @param request
     * @return 权限列表
     */
    @PutMapping("/roles/{id}/privileges")
    public Object updatePrivileges(@PathVariable Integer id, @RequestBody List<Privilege> privilegeList, HttpServletRequest request) {
        if (id == null || privilegeList == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));
            try {
                logUtil.addLogInfoSuccess(request,adminId,InetAddress.getLocalHost().toString(),3,"修改",LocalDateTime.now(),LocalDateTime.now());
                privilegeList = privilegeService.updatePrivilege(id, privilegeList);
                if (privilegeList == null) {
                    return ResponseUtil.updateRoleFailed();
                } else {
                    return ResponseUtil.ok(privilegeList);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId,InetAddress.getLocalHost().toString(),3,"修改",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }
}
