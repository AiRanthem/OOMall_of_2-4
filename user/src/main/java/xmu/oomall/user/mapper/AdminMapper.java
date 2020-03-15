package xmu.oomall.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xmu.oomall.user.domain.Admin;
import xmu.oomall.user.domain.AdminPo;

import java.util.List;
import java.util.Map;

/**
 * @Author: 谢逸翔
 * @Description:管理员Mapper接口
 * @Date: Created in 21:00 2019/12/06
 * @Modified By:
 **/

@Mapper
@Repository
public interface AdminMapper {

    /**
     * 获得所有管理员
     *
     * @param data 选择条件
     * @return 管理员列表
     */
    List<AdminPo> getAllByAdminName(Map<String, Object> data);

    /**
     * 获得某一个管理员的详细信息
     *
     * @param id 管理员id
     * @return 管理员信息
     */
    AdminPo getAdminById(Integer id);

    /**
     * 获得某一个管理员的详细信息
     *
     * @param username 管理员用户名
     * @return 管理员信息
     */
    AdminPo getAdminByName(String username);

    /**
     * 获得某一类管理员的详细信息
     *
     * @param roleId 管理员角色id
     * @return 管理员信息
     */
    List<AdminPo> getAdminByRoleId(Integer roleId);

    /**
     * 添加一个新的管理员
     *
     * @param admin 管理员信息
     * @return beSuccess
     */
    Integer insertAdmin(Admin admin);

    /**
     * 更新一个管理员信息
     *
     * @param admin 管理员信息
     * @return beSuccess
     */
    Integer updateAdmin(Admin admin);

    /**
     * 删除选定管理员
     *
     * @param data 修改条件
     * @return beSuccess
     */
    Integer deleteAdmin(Map<String,Object> data);
}
