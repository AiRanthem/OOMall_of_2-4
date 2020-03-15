package xmu.oomall.freight.mapper;

import org.apache.ibatis.annotations.Mapper;
import xmu.oomall.freight.domain.DefaultPieceFreight;
import xmu.oomall.freight.domain.DefaultPieceFreightPo;

import java.util.List;
import java.util.Map;

/**
 * @Author: 陆俊伟
 * @Description: 按件数计费规则Mapper接口
 * @Date: Created in 11:24 2019/12/11
 **/

@Mapper
public interface DefaultPieceFreightMapper {

    /**
     * 获得所有规则
     *
     * @param data 选择条件
     * @return 规则列表
     */
    List<DefaultPieceFreightPo> findAllDefaultPieceFreight(Map<String, Integer> data);

    /**
     * 获得某一条规则的详细信息
     *
     * @param id 规则id
     * @return 规则信息
     */
    DefaultPieceFreightPo findDefaultPieceFreightById(Integer id);

    /**
     * 添加一条新的规则
     *
     * @param defaultPieceFreight 规则信息
     * @return int
     */
    Integer addDefaultPieceFreight(DefaultPieceFreight defaultPieceFreight);

    /**
     * 更新一条规则信息
     *
     * @param defaultPieceFreight 规则信息
     * @return int
     */
    Integer updateDefaultPieceFreight(DefaultPieceFreight defaultPieceFreight);

    /**
     * 删除选定规则（一条或者多条）
     *
     * @param data 修改信息
     * @return int
     */
    Integer deleteDefaultPieceFreight(Map<String, Object> data);
}
