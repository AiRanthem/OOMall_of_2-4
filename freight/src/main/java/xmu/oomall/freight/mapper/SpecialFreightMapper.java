package xmu.oomall.freight.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.freight.domain.SpecialFreight;
import xmu.oomall.freight.domain.SpecialFreightPo;

import java.util.List;
import java.util.Map;

/**
 * @Author: 陆俊伟
 * @Description: 默认运费规则Mapper接口
 * @Date: Created in 11:35 2019/12/11
 **/

@Mapper
public interface SpecialFreightMapper {

    /**
     * 获得所有规则
     *
     * @param data 选择条件
     * @return 规则列表
     */
    List<SpecialFreightPo> findAllSpecialFreight(Map<String, Integer> data);

    /**
     * 获得某一条规则的详细信息
     *
     * @param id 规则id
     * @return 规则信息
     */
    SpecialFreightPo findSpecialFreightById(Integer id);

    /**
     * 添加一条新的规则
     *
     * @param specialFreight 规则信息
     * @return int
     */
    Integer addSpecialFreight(SpecialFreight specialFreight);

    /**
     * 更新一条规则信息
     *
     * @param specialFreight 规则信息
     * @return int
     */
    Integer updateSpecialFreight(SpecialFreight specialFreight);

    /**
     * 删除选定规则（一条或者多条）
     *
     * @param data 修改信息
     * @return int
     */
    Integer deleteSpecialFreight(Map<String, Object> data);
}
