package xmu.oomall.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xmu.oomall.user.domain.Privilege;
import xmu.oomall.user.domain.Role;

import java.util.List;
import java.util.Map;

/**
 * @author 陆俊伟
 * @Description 管理员Mapper接口
 * @date  Created in 11:00 2019/12/18
 * @Modified By:
 **/

@Mapper
@Repository
public interface PrivilegeMapper {

    /**
     * 获得所有角色
     *
     * @return 角色列表
     */
    List<Role> getAllRoles();

    /**
     * 获得角色
     *
     * @param id id
     * @return 角色列表
     */
    Role getRoleById(Integer id);

    /**
     * 添加一个新的角色
     *
     * @param role 角色
     * @return beSuccess
     */
    Integer insertRole(Role role);

    /**
     * 更新一个角色信息
     *
     * @param role 角色信息
     * @return beSuccess
     */
    Integer updateRole(Role role);

    /**
     * 删除选定角色
     *
     * @param data 修改条件
     * @return beSuccess
     */
    Integer deleteRole(Map<String, Object> data);

    /**
     * 获得某一role的所有权限
     * @param roleId roleId
     * @return privileges
     */
    List<Privilege> findAllPrivilegesByRoleId(Integer roleId);

    /**
     * 添加权限
     * @param privilegeList 权限对象
     * @return beSuccess
     */
    Integer insertPrivilege(List<Privilege> privilegeList);

    /**
     * 删除权限
     * @param data 修改条件
     * @return beSuccess
     */
    Integer deletePrivileges(Map<String,Object> data);
}
