package xmu.oomall.user.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.user.domain.User;
import xmu.oomall.user.domain.UserPo;
import xmu.oomall.user.mapper.UserMapper;
import xmu.oomall.user.service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 用户Dao
 *
 * @author: 谢逸翔
 * @date: Created in 21:12 2019/12/06
 **/

@Repository
public class UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加用户
     *
     * @param user 用户
     * @return 添加情况
     */
    public Integer addUser(User user) {
        logger.debug("addUser参数： user=" + user);
        try {
            Integer beSuccess = userMapper.insertUser(user);
            logger.debug("用户添加成功，返回： id=" + user.getId());
            return beSuccess;
        } catch (Exception e) {
            logger.debug("用户添加失败");
            return 0;
        }
    }

    /**
     * 获取某个用户详细信息
     *
     * @param id 用户id
     * @return 用户
     */
    public UserPo getUserById(Integer id) {
        logger.debug("getUserById参数： id=" + id);
        try {
            UserPo userPo = userMapper.getUserById(id);
            logger.debug("getUserById返回： user=" + userPo);
            return userPo;
        } catch (Exception e) {
            logger.debug("用户获取失败");
            return null;
        }
    }

    public UserPo getUserByMobile(String mobile) {
        logger.debug("getUserByMobile参数： mobile=" + mobile);
        try {
            UserPo userPo = userMapper.getUserByMobile(mobile);
            logger.debug("getUserByMobile返回： user=" + userPo);
            return userPo;
        } catch (Exception e) {
            logger.debug("用户获取失败");
            return null;
        }
    }

    /**
     * 获取某个用户详细信息
     *
     * @param name 用户名
     * @return 用户
     */
    public UserPo getUserByName(String name) {
        logger.debug("getUserByName参数：name=" + name);
        try {
            UserPo userPo = userMapper.getUserByName(name);
            logger.debug("getUserByName返回： user=" + userPo);
            return userPo;
        } catch (Exception e) {
            logger.debug("用户获取失败");
            return null;
        }
    }

    /**
     * 获取所有用户详细信息
     *
     * @return 用户列表
     */
    public List<UserPo> getAllUser(Integer offSet, Integer limit) {
        Map<String, Integer> data = new HashMap<>(2);
        data.put("offSet", offSet);
        data.put("limit", limit);
        try {
            List<UserPo> userPoList = userMapper.getAll(data);
            logger.debug("getAllUser返回： userList=" + userPoList);
            return userPoList;
        } catch (Exception e) {
            logger.debug("用户列表获取失败");
            return null;
        }
    }

    /**
     * 更新某个用户详细信息
     *
     * @param user 用户
     * @return 更新情况
     */
    public Integer updateUser(User user) {
        logger.debug("updateUser参数： user=" + user);
        try {
            Integer beSuccess = userMapper.updateUser(user);
            logger.debug("用户更新成功");
            return beSuccess;
        } catch (Exception e) {
            logger.debug("用户更新失败");
            return 0;
        }
    }

    /**
     * 删除某个用户
     *
     * @param id 用户id
     * @return 删除情况
     */
    public Integer deleteUser(Integer id) {
        logger.debug("deleteUser参数： id=" + id);
        Map<String, Object> data = new HashMap<>(2);
        data.put("id", id);
        LocalDateTime localDateTime = LocalDateTime.now();
        data.put("gmtModified", localDateTime);
        try {
            Integer beSuccess = userMapper.deleteUser(data);
            logger.debug("用户删除成功");
            return beSuccess;
        } catch (Exception e) {
            logger.debug("用户删除失败");
            return 0;
        }
    }
}
