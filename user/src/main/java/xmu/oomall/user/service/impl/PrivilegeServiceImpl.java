package xmu.oomall.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import xmu.oomall.user.dao.PrivilegeDao;
import xmu.oomall.user.domain.Privilege;
import xmu.oomall.user.domain.Role;
import xmu.oomall.user.service.PrivilegeService;

import java.util.List;

/**
 * @author: 陆俊伟
 * @description: 权限有关的服务实现
 * @date: Created in 13:20 2019/12/18
 * @modified By:
 **/
@Repository
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeDao privilegeDao;

    @Override
    public Integer addRole(Role role) {
        return privilegeDao.addRole(role);
    }

    @Override
    public Integer updateRole(Role role) {
        return privilegeDao.updateRole(role);
    }

    @Override
    public List<Role> findAllRoles() {
        return privilegeDao.findAllRoles();
    }

    @Override
    public Role findRoleById(Integer id) {
        return privilegeDao.findRoleById(id);
    }

    @Override
    public Integer deleteRole(Integer id) {
        return privilegeDao.deleteRole(id);
    }

    @Override
    public List<Privilege> findAllPrivilegesByRoleId(Integer roleId) {
        return privilegeDao.findAllPrivilegesByRoleId(roleId);
    }

    @Override
    public List<Privilege> updatePrivilege(Integer roleId, List<Privilege> privilegeList) {
        return privilegeDao.updatePrivileges(roleId, privilegeList);
    }
}
