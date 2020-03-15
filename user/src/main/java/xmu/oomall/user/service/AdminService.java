package xmu.oomall.user.service;

import org.springframework.stereotype.Service;
import xmu.oomall.user.domain.Admin;
import xmu.oomall.user.domain.AdminPo;

import java.util.List;

/**
 * @Author: 谢逸翔
 * @Description: 管理员有关的服务
 * @Date: Created in 00:58 2019/12/7
 * @Modified By:
 **/
@Service
public interface AdminService {
    /**
     * 创建管理员
     * @param admin 新管理员
     * @return 新管理员
     */
    Integer addAdmin(Admin admin);

    /**
     * 修改管理员信息
     * @param admin 新管理员信息
     * @return
     */
    Integer updateAdmin(Admin admin);

    /**
     * 获得所有管理员对象
     *
     * @param offSet 地址偏移量
     * @param limit 限制条数
     * @param username 管理员用户名
     * @return 管理员对象列表
     */
    List<AdminPo> getAllAdminByName(Integer offSet, Integer limit, String username);

    /**
     * 用Id获得Admin对象
     * @param id 管理员Id
     * @return admin对象
     */
    AdminPo getAdminById(Integer id);

    /**
     * 用roleId获得Admin对象
     * @param roleId 管理员角色Id
     * @return admin对象
     */
    List<AdminPo> getAdminByRoleId(Integer roleId);

    /**
     * 用Id获得Admin对象
     * @param username 管理员用户名
     * @return admin对象
     */
    AdminPo getAdminByName(String username);

    /**
     * 删除指定管理员
     * @param id 待删除的管理员id
     * @return
     */
    Integer deleteAdmin(Integer id);

    /**
     * 验证密码
     *
     * @param username 用户名
     * @param passWord   密码
     * @return 是否正确
     */
    Boolean validatePassWord(String username, String passWord);
}
