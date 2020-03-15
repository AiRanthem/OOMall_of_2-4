package xmu.oomall.user.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.user.domain.Privilege;
import xmu.oomall.user.domain.Role;
import xmu.oomall.user.mapper.PrivilegeMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限Dao
 *
 * @author 陆俊伟
 * @date Created in 11:31 2019/12/18
 **/

@Repository
public class PrivilegeDao {

    private static final Logger logger = LoggerFactory.getLogger(PrivilegeDao.class);

    @Autowired
    private PrivilegeMapper privilegeMapper;

    /**
     * 获得所有角色
     *
     * @return 角色列表
     */
    public List<Role> findAllRoles() {
        try {
            return privilegeMapper.getAllRoles();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获得所有角色
     *
     * @param id id
     * @return 角色列表
     */
    public Role findRoleById(Integer id) {
        try {
            return privilegeMapper.getRoleById(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 添加角色
     *
     * @param role 管理员
     * @return 添加情况
     */
    public Integer addRole(Role role) {
        logger.debug("addRole参数： role=" + role);
        try {
            return privilegeMapper.insertRole(role);
        } catch (Exception e) {
            logger.debug("角色添加失败");
            return 0;
        }
    }

    /**
     * 更新某个角色
     *
     * @param role 角色
     * @return 更新情况
     */
    public Integer updateRole(Role role) {
        logger.debug("updateRole参数： role=" + role);
        try {
            Integer beSuccess = privilegeMapper.updateRole(role);
            logger.debug("角色更新成功");
            return beSuccess;
        } catch (Exception e) {
            logger.debug("角色更新失败");
            return 0;
        }
    }

    /**
     * 删除某个角色
     *
     * @param id 角色id
     * @return 删除情况
     */
    public Integer deleteRole(Integer id) {
        logger.debug("deleteRole参数： id=" + id);
        Map<String, Object> data = new HashMap<>(2);
        data.put("id", id);
        data.put("gmtModified", LocalDateTime.now());
        try {
            Integer beSuccess = privilegeMapper.deleteRole(data);
            logger.debug("角色删除成功");
            return beSuccess;
        } catch (Exception e) {
            logger.debug("角色删除失败");
            return 0;
        }
    }

    /**
     * 获得某一roleId的权限
     *
     * @param roleId roleId
     * @return 权限列表
     */
    public List<Privilege> findAllPrivilegesByRoleId(Integer roleId) {
        logger.debug("findAllPrivilegesByRoleId参数：roleId=" + roleId);
        try {
            return privilegeMapper.findAllPrivilegesByRoleId(roleId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 更新权限表
     *
     * @param roleId        角色id
     * @param privilegeList 权限
     * @return 权限
     */
    public List<Privilege> updatePrivileges(Integer roleId, List<Privilege> privilegeList) {
        logger.debug("updatePrivileges参数：privilegeList:" + privilegeList);
        try {
            Map<String, Object> data = new HashMap<>(2);
            data.put("roleId", roleId);
            data.put("gmtModified", LocalDateTime.now());
            privilegeMapper.deletePrivileges(data);
            privilegeMapper.insertPrivilege(privilegeList);
            return privilegeMapper.findAllPrivilegesByRoleId(roleId);
        } catch (Exception e) {
            return null;
        }
    }
}
