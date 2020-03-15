package xmu.oomall.freight.service;

import org.springframework.stereotype.Service;
import xmu.oomall.freight.domain.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zty
 * @author 陆俊伟
 * 运费模块服务接口
 */
@Service
public interface FreightService {

    /**
     * 计算运费
     *
     * @param goodsList   商品列表
     * @param numList     数量列表
     * @param destination 目的地
     * @return 运费
     */
    BigDecimal cacuFee(List<Goods> goodsList, List<Integer> numList, AddressPo destination);

    /**
     * 获得所有计重运费模板
     * @param offSet 地址偏移量
     * @param limit 选择条数
     * @return 计重运费模板列表
     */
    List<DefaultFreightPo> findAllDefaultFreight(Integer offSet, Integer limit);

    /**
     * 获得所有计件运费模板
     * @param offSet 地址偏移量
     * @param limit 选择条数
     * @return 计件运费模板列表
     */
    List<DefaultPieceFreightPo> findAllDefaultPieceFreight(Integer offSet, Integer limit);

    /**
     * 获得所有特殊运费模板
     * @param offSet 地址偏移量
     * @param limit 选择条数
     * @return 特殊运费模板列表
     */
    List<SpecialFreightPo> findAllSpecialFreight(Integer offSet, Integer limit);

    /**
     * 获得某一条计重运费模板详细信息
     *
     * @param id 模板id
     * @return 运费模板
     */
    DefaultFreightPo findDefaultFreightById(Integer id);

    /**
     * 获得某一条计件运费模板详细信息
     *
     * @param id 模板id
     * @return 运费模板
     */
    DefaultPieceFreightPo findDefaultPieceFreightById(Integer id);

    /**
     * 获得某一条特殊运费模板详细信息
     *
     * @param id 模板id
     * @return 运费模板
     */
    SpecialFreightPo findSpecialFreightById(Integer id);

    /**
     * 添加一条计重运费模板
     *
     * @param defaultFreight 运费模板信息
     * @return int
     */
    Integer addDefaultFreight(DefaultFreight defaultFreight);

    /**
     * 添加一条计件运费模板
     *
     * @param defaultPieceFreight 运费模板信息
     * @return int
     */
    Integer addDefaultPieceFreight(DefaultPieceFreight defaultPieceFreight);

    /**
     * 添加一条特殊运费模板
     *
     * @param specialFreight 运费模板信息
     * @return int
     */
    Integer addSpecialFreight(SpecialFreight specialFreight);

    /**
     * 修改一条计重运费模板
     *
     * @param defaultFreight 运费模板信息
     * @return int
     */
    Integer updateDefaultFreight(DefaultFreight defaultFreight);

    /**
     * 修改一条计件运费模板
     *
     * @param defaultPieceFreight 运费模板信息
     * @return int
     */
    Integer updateDefaultPieceFreight(DefaultPieceFreight defaultPieceFreight);

    /**
     * 修改一条特殊运费模板
     *
     * @param specialFreight 运费模板信息
     * @return int
     */
    Integer updateSpecialFreight(SpecialFreight specialFreight);

    /**
     * 删除选定运费模板
     *
     * @param id 选定运费模板的id列表
     * @return int
     */
    Integer deleteDefaultFreight(Integer id);

    /**
     * 删除选定运费模板
     *
     * @param id 选定运费模板的id列表
     * @return int
     */
    Integer deleteDefaultPieceFreight(Integer id);

    /**
     * 删除选定运费模板
     *
     * @param id 选定运费模板的id列表
     * @return int
     */
    Integer deleteSpecialFreight(Integer id);
}
