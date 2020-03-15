package xmu.oomall.address.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.address.domain.Region;

import java.util.List;

/**
 * @author 陆俊伟
 * @description 地址相关服务
 * @date Created in 15:55 2019/12/05
 **/

@Repository
@Service
public interface AddressService {

    /**
     * 添加地址
     *
     * @param address 新地址
     * @return int
     */
    Integer addAddress(Address address);

    /**
     * 修改地址
     *
     * @param address 新地址信息
     * @return int
     */
    Integer updateAddress(Address address);

    /**
     * 用户获得所有地址
     *
     * @param userId 用户ID
     * @param offSet 地址偏移量
     * @param limit  选择条数
     * @return 地址列表
     */
    List<AddressPo> findAllAddress(Integer userId, Integer offSet, Integer limit);

    /**
     * 用Id获得Address对象
     *
     * @param id 对象Id
     * @return address对象
     */
    AddressPo findAddressById(Integer id);

    /**
     * 清除选定地址
     *
     * @param id 待清除的地址
     * @return int
     */
    Integer clearAddress(Integer id);

    /**
     * 用Id获得Region对象
     *
     * @param id 对象Id
     * @return region对象
     */
    Region findRegionById(Integer id);

    /**
     * 用父级地区id获得Region对象
     *
     * @param pid 父级地区Id
     * @return region对象列表
     */
    List<Region> findRegionsByPid(Integer pid);
}
