package xmu.oomall.address.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xmu.oomall.address.domain.Region;

import java.util.List;

/**
 * @author  陆俊伟
 * @Description: 地区Mapper接口
 * @date  Created in 15:21 2019/12/05
 **/

@Repository
@Mapper
public interface RegionMapper {

    /**
     * 以父级地区id获得子级地区列表
     * @param pid 父级地区id
     * @return 地区列表
     */
    List<Region> getRegionsByPid(Integer pid);

    /**
     * 地区id获得地区对象
     * @param id 地区id
     * @return 地区对象I
     */
    Region getRegionById(Integer id);

    /**
     * 获得所有地区
     * @return 地区列表
     */
    List<Region> getAllRegion();

}
