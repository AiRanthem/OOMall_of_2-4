package xmu.oomall.user.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.user.domain.Admin;
import xmu.oomall.user.domain.AdminPo;
import xmu.oomall.user.mapper.AdminMapper;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 管理员Dao
 *
 * @author: 谢逸翔
 * @date: Created in 21:12 2019/12/06
 **/

@Repository
public class AdminDao {

    private static final Logger logger = LoggerFactory.getLogger(AdminDao.class);

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 添加管理员
     *
     * @param admin 管理员
     * @return 添加情况
     */
    public Integer addAdmin(Admin admin) {
        logger.debug("addAdmin参数： admin=" + admin);
        try {
            Integer beSuccess = adminMapper.insertAdmin(admin);
            logger.debug("管理员添加成功，返回： id=" + admin.getId());
            return beSuccess;
        } catch (Exception e) {
            logger.debug("管理员添加失败");
            return 0;
        }
    }

    /**
     * 获取某个管理员详细信息
     *
     * @param id 管理员id
     * @return 管理员
     */
    public AdminPo getAdminById(Integer id) {
        logger.debug("getAdminById参数： id=" + id);
        try {
            AdminPo adminPo = adminMapper.getAdminById(id);
            logger.debug("getAdminById返回： admin=" + adminPo);
            return adminPo;
        } catch (Exception e) {
            logger.debug("管理员获取失败");
            return null;
        }
    }

    /**
     * 获取某个管理员详细信息
     *
     * @param roleId 管理员id
     * @return 管理员
     */
    public List<AdminPo> getAdminByRoleId(Integer roleId) {
        logger.debug("getAdminByRoleId参数： roleId=" + roleId);
        try {
            List<AdminPo> adminList = adminMapper.getAdminByRoleId(roleId);
            logger.debug("getAdminByRoleId返回： admin=" + adminList);
            return adminList;
        } catch (Exception e) {
            logger.debug("管理员获取失败");
            return null;
        }
    }

    /**
     * 获取某个管理员详细信息
     *
     * @param username 管理员用户名
     * @return 管理员
     */
    public AdminPo getAdminByName(String username) {
        logger.debug("getAdminByName参数： username=" + username);
        try {
            AdminPo adminPo = adminMapper.getAdminByName(username);
            logger.debug("getAdminByName返回： admin=" + adminPo);
            return adminPo;
        } catch (Exception e) {
            logger.debug("管理员获取失败");
            return null;
        }
    }

    /**
     * 获取所有拥有特定名字的管理员详细信息
     *
     * @param offSet 地址偏移量
     * @param limit 限制条数
     * @param username 管理员用户名
     * @return 管理员列表
     */
    public List<AdminPo> getAllAdminByName(Integer offSet, Integer limit, String username) {
        Map<String, Object> data = new HashMap<>(3);
        data.put("offSet", offSet);
        data.put("limit", limit);
        data.put("username", username);
        try {
            List<AdminPo> adminList = adminMapper.getAllByAdminName(data);
            logger.debug("getAllAdmin返回： adminList=" + adminList);
            return adminList;
        } catch (Exception e) {
            logger.debug("管理员列表获取失败");
            return null;
        }
    }

    /**
     * 更新某个管理员详细信息
     *
     * @param admin 管理员
     * @return 更新情况
     */
    public Integer updateAdmin(Admin admin) {
        logger.debug("updateAdmin参数： admin=" + admin);
        try {
            Integer beSuccess = adminMapper.updateAdmin(admin);
            logger.debug("管理员更新成功");
            return beSuccess;
        } catch (Exception e) {
            logger.debug("管理员更新失败");
            return 0;
        }
    }

    /**
     * 删除某个管理员
     *
     * @param id 管理员id
     * @return 删除情况
     */
    public Integer deleteAdmin(Integer id) {
        logger.debug("deleteAdmin参数： id=" + id);
        Map<String, Object> data = new HashMap<>(2);
        data.put("id", id);
        LocalDateTime localDateTime = LocalDateTime.now();
        data.put("gmtModified", localDateTime);
        try {
            Integer beSuccess = adminMapper.deleteAdmin(data);
            logger.debug("管理员删除成功");
            return beSuccess;
        } catch (Exception e) {
            logger.debug("管理员删除失败");
            return 0;
        }
    }
}
