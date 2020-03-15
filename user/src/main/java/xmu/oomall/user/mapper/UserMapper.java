package xmu.oomall.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xmu.oomall.user.domain.User;
import xmu.oomall.user.domain.UserPo;

import java.util.List;
import java.util.Map;

/**
 * @Author: 谢逸翔
 * @Description:用户Mapper接口
 * @Date: Created in 21:00 2019/12/06
 * @Modified By:
 **/

@Mapper
@Repository
public interface UserMapper {

    /**
     * 获得所有用户
     *
     * @param data 选择要求
     * @return 用户列表
     */
    List<UserPo> getAll(Map<String, Integer> data);

    /**
     * 获得某一个用户的详细信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    UserPo getUserById(Integer id);

    /**
     * 获得某一个用户的详细信息
     *
     * @param name 用户昵称
     * @return 用户信息
     */
    UserPo getUserByName(String name);

    /**
     * 添加一个新的用户
     *
     * @param user 用户信息
     * @return beSuccess
     */
    Integer insertUser(User user);

    /**
     * 更新一个用户信息
     *
     * @param user 用户信息
     * @return beSuccess
     */
    Integer updateUser(User user);

    /**
     * 删除选定用户
     *
     * @param data 修改信息
     * @return beSuccess
     */
    Integer deleteUser(Map<String, Object> data);

    /**
     * 通过手机获得用户
     * @param mobile 手机
     * @return 用户
     */
    UserPo getUserByMobile(String mobile);
}
