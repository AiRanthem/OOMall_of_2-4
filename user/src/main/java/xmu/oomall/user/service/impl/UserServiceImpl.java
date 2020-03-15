package xmu.oomall.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import xmu.oomall.user.dao.UserDao;
import xmu.oomall.user.domain.User;
import xmu.oomall.user.domain.UserPo;
import xmu.oomall.user.service.UserService;
import xmu.oomall.user.util.Config;

import java.util.List;

/**
 * @author: 谢逸翔
 * @description: 用户有关的服务实现
 * @date: Created in 23:26 2019/12/10
 * @modified By:
 **/
@Repository
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    Config config;

    @Autowired
    UserDao userDao;

    @Override
    public Integer addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public Integer updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public List<UserPo> getAllUser(Integer offSet, Integer limit) {
        return userDao.getAllUser(offSet,limit);
    }

    @Override
    public UserPo findUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public UserPo findUserByMobile(String mobile) { return userDao.getUserByMobile(mobile); }

    @Override
    public UserPo findUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public Integer deleteUser(Integer id) {
        logger.debug("deleteUser参数：" + id);
        return userDao.deleteUser(id);
    }

    @Override
    public Boolean validatePassWord(String username, String passWord) {
        try {
            String truePassWord = userDao.getUserByName(username).getPassword();
            return truePassWord.equals(passWord);
        }
        catch(Exception e) {
            return false;
        }
    }

    @Override
    public Boolean validateCode(String code, String phone) {
        String trueCode = "123456";
        return trueCode.equals(code);
    }

    @Override
    public Boolean register(User user) {
        Integer success = userDao.addUser(user);
        return success == 1;
    }
}
