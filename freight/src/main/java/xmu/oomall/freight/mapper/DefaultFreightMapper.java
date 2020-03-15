package xmu.oomall.freight.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.freight.domain.DefaultFreight;
import xmu.oomall.freight.domain.DefaultFreightPo;

import java.util.List;
import java.util.Map;

/**
 * @Author: 陆俊伟
 * @Description: 默认运费规则Mapper接口
 * @Date: Created in 00:17 2019/12/11
 **/

@Mapper
public interface DefaultFreightMapper {

    /**
     * 获得所有规则
     *
     * @param data 选择条件
     * @return 规则列表
     */
    List<DefaultFreightPo> findAllDefaultFreight(Map<String, Integer> data);

    /**
     * 获得某一条规则的详细信息
     *
     * @param id 规则id
     * @return 规则信息
     */
    DefaultFreightPo findDefaultFreightById(Integer id);

    /**
     * 添加一条新的规则
     *
     * @param defaultFreight 规则信息
     * @return int
     */
    Integer addDefaultFreight(DefaultFreight defaultFreight);

    /**
     * 更新一条规则信息
     *
     * @param defaultFreight 规则信息
     * @return int
     */
    Integer updateDefaultFreight(DefaultFreight defaultFreight);

    /**
     * 删除选定规则
     *
     * @param data 修改信息
     * @return int
     */
    Integer deleteDefaultFreight(Map<String, Object> data);
}
