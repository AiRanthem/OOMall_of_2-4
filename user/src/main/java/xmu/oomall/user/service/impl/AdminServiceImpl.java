package xmu.oomall.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import xmu.oomall.user.dao.AdminDao;
import xmu.oomall.user.domain.Admin;
import xmu.oomall.user.domain.AdminPo;
import xmu.oomall.user.service.AdminService;
import xmu.oomall.user.util.Config;

import java.util.List;

/**
 * @author: 谢逸翔
 * @description: 管理员有关的服务实现
 * @date: Created in 23:26 2019/12/10
 * @modified By:
 **/
@Repository
@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    Config config;

    @Autowired
    AdminDao adminDao;

    @Override
    public Integer addAdmin(Admin admin) {
        logger.debug("addAdmin参数： admin=" + admin);
        return adminDao.addAdmin(admin);
    }

    @Override
    public Integer updateAdmin(Admin admin) {
        logger.debug("updateAdmin参数： admin=" + admin);
        return adminDao.updateAdmin(admin);
    }

    @Override
    public List<AdminPo> getAllAdminByName(Integer offSet, Integer limit, String username) {
        return adminDao.getAllAdminByName(offSet, limit, username);
    }

    @Override
    public AdminPo getAdminById(Integer id) {
        return adminDao.getAdminById(id);
    }

    @Override
    public List<AdminPo> getAdminByRoleId(Integer roleId) {
        return adminDao.getAdminByRoleId(roleId);
    }

    @Override
    public AdminPo getAdminByName(String username) {
        return adminDao.getAdminByName(username);
    }

    @Override
    public Integer deleteAdmin(Integer id) {
        logger.debug("deleteAdmin参数： id=" + id);
        return adminDao.deleteAdmin(id);
    }

    @Override
    public Boolean validatePassWord(String username, String passWord) {
        try {
            String truePassWord = adminDao.getAdminByName(username).getPassword();
            return truePassWord.equals(passWord);
        }
        catch(Exception e) {
            return false;
        }
    }
}
