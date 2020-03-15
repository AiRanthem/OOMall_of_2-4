package xmu.oomall.address.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.address.domain.Address;
import xmu.oomall.address.domain.AddressPo;

import java.util.List;
import java.util.Map;

/**
 * @author 陆俊伟
 * @Description 地区Mapper接口
 * @date Created in 15:21 2019/12/05
 **/

@Mapper
public interface AddressMapper {

    /**
     * 获得某一用户的所有地址
     *
     * @param data 选择条件
     * @return 地址列表
     */
    List<AddressPo> getAllAddress(Map<String,Integer> data);

    /**
     * 获得某一条地址的详细信息
     *
     * @param id 地址id
     * @return 地址信息
     */
    AddressPo getAddressById(Integer id);

    /**
     * 添加一条新的地址
     *
     * @param address 地址信息
     * @return int
     */
    Integer insertAddress(Address address);

    /**
     * 更新一条地址信息
     *
     * @param address 地址信息
     * @return int
     */
    Integer updateAddress(Address address);

    /**
     * 删除选定地址
     *
     * @param data 修改信息
     * @return int
     */
    Integer deleteAddress(Map<String,Object> data);
}
