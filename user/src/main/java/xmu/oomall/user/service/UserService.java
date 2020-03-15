package xmu.oomall.user.service;

import org.springframework.stereotype.Service;
import xmu.oomall.user.domain.User;
import xmu.oomall.user.domain.UserPo;

import java.util.List;

/**
 * @Author: 谢逸翔
 * @Description: 用户有关的服务
 * @Date: Created in 00:58 2019/12/7
 * @Modified By:
 **/
@Service
public interface UserService {

    /**
     * 创建用户
     *
     * @param user 新用户
     * @return 新用户
     */
    Integer addUser(User user);

    /**
     * 修改用户信息
     *
     * @param user 新用户信息
     * @return
     */
    Integer updateUser(User user);

    /**
     * 获得所有用户对象
     *
     * @return 用户对象列表
     */
    List<UserPo> getAllUser(Integer offSet, Integer limit);

    /**
     * 用Id获得User对象
     *
     * @param id 用户Id
     * @return user对象
     */
    UserPo findUserById(Integer id);

    /**
     * 用Id获得User对象
     *
     * @param name 用户名
     * @return user对象
     */
    UserPo findUserByName(String name);

    /**
     * 用手机号获得用户
     * @author zty
     * @param mobile
     * @return
     */
    UserPo findUserByMobile(String mobile);

    /**
     * 删除指定用户
     *
     * @param id 待删除的用户id
     * @return
     */
    Integer deleteUser(Integer id);

    /**
     * 验证密码
     *
     * @param username 用户名
     * @param passWord   密码
     * @return 是否正确w
     */
    Boolean validatePassWord(String username, String passWord);

    /**
     * @param code  验证码
     * @param phone 手机号
     * @return 是否正确
     */
    Boolean validateCode(String code, String phone);

    /**
     * 注册
     *
     * @param user 用户信息
     * @return 是否注册成功
     */
    Boolean register(User user);
}
