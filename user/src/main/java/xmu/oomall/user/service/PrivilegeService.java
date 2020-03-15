package xmu.oomall.user.service;

import org.springframework.stereotype.Service;
import xmu.oomall.user.domain.Privilege;
import xmu.oomall.user.domain.Role;

import java.util.List;

/**
 * @Author: 陆俊伟
 * @Description: 权限有关的服务
 * @Date: Created in 13:20 2019/12/18
 * @Modified By:
 **/
@Service
public interface PrivilegeService {

    /**
     * 创建角色
     *
     * @param role 新角色
     * @return 新角色
     */
    Integer addRole(Role role);

    /**
     * 修改角色信息
     *
     * @param role 新角色信息
     * @return int
     */
    Integer updateRole(Role role);

    /**
     * 获得所有角色对象
     *
     * @return 角色对象列表
     */
    List<Role> findAllRoles();

    /**
     * 获得所有角色对象
     *
     * @param id id
     * @return 角色对象列表
     */
    Role findRoleById(Integer id);

    /**
     * 删除指定角色
     *
     * @param id 待删除的角色id
     * @return int
     */
    Integer deleteRole(Integer id);

    /**
     * 获得某一角色的所有权限
     *
     * @param roleId 角色id
     * @return 权限列表
     */
    List<Privilege> findAllPrivilegesByRoleId(Integer roleId);

    /**
     * 更新某一角色的权限列表
     * @param roleId 角色Id
     * @param privilegeList 新的权限列表
     * @return 新的权限列表
     */
    List<Privilege> updatePrivilege(Integer roleId, List<Privilege> privilegeList);
}
