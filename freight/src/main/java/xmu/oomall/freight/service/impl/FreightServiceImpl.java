package xmu.oomall.freight.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import xmu.oomall.freight.dao.DefaultFreightDao;
import xmu.oomall.freight.dao.DefaultPieceFreightDao;
import xmu.oomall.freight.dao.SpecialFreightDao;
import xmu.oomall.freight.domain.*;
import xmu.oomall.freight.service.FreightService;

import java.math.BigDecimal;
import java.util.*;


/**
 * @author zty
 * @author 陆俊伟
 * FreightService的接口实现
 */
@Repository
@Service
public class FreightServiceImpl implements FreightService {

    @Autowired
    private DefaultFreightDao defaultFreightDao;

    @Autowired
    private DefaultPieceFreightDao defaultPieceFreightDao;

    @Autowired
    private SpecialFreightDao specialFreightDao;

    /**
     * 计算运费
     *
     * @param goodsList   商品列表
     * @param numList     数量列表
     * @param destination 目的地
     * @return 运费
     */
    @Override
    public BigDecimal cacuFee(List<Goods> goodsList, List<Integer> numList, AddressPo destination) {

        Map<Integer, SpecialFreightPo> specialFreightPos = pickSpecial(goodsList);

        ArrayList<BigDecimal> fees = new ArrayList<>();

        /**
         * 计重
         */
        if (specialFreightPos.containsKey(-1)) {
            DefaultFreightPo defaultFreightPo;
            try {
                defaultFreightPo = defaultFreightDao.findDefaultFreightByDest(destination);
            } catch (Exception e) {
                System.out.println("数据库中没有对象！");
                return null;
            }
            fees.add(defaultFreightPo.cacuFee(goodsList, numList));
        }
        /**
         * 计件
         */
        BigDecimal rate;
        try {
            rate = defaultPieceFreightDao.findDefaultPieceFreightByDest(destination).getUnitRate();
        } catch (Exception e) {
            System.out.println("数据库中没有对象！");
            return null;
        }
        for (SpecialFreightPo specialFreightPo : specialFreightPos.values()) {
            if (specialFreightPo == null) {
                continue;
            }
            BigDecimal f = BigDecimal.ZERO;
            for (Integer num : numList) {
                f = f.add(specialFreightPo.cacuSpecialFee(num, rate));
            }
            fees.add(f);
        }

        BigDecimal fee = Collections.max(fees);

        return fee;
    }

    /**
     * 获得所有特殊模板
     *
     * @param goodsList 商品列表
     * @return 模板列表, 有key -1说明不是全都计件
     */
    private HashMap<Integer, SpecialFreightPo> pickSpecial(List<Goods> goodsList) {
        HashMap<Integer, SpecialFreightPo> specialFreightPos = new HashMap<>(10);

        for (Goods goods : goodsList) {
            Boolean beSpecial = goods.getBeSpecial();
            if (beSpecial == null || !beSpecial) {
                specialFreightPos.put(-1, null);
            }
            else  {
                specialFreightPos.put(goods.getSpecialFreightId()
                        , specialFreightDao.findSpecialFreightById(goods.getSpecialFreightId()));
            }
        }
        return specialFreightPos;
    }

    @Override
    public List<DefaultFreightPo> findAllDefaultFreight(Integer offSet, Integer limit) {
        return defaultFreightDao.findAllDefaultFreight(offSet, limit);
    }

    @Override
    public List<DefaultPieceFreightPo> findAllDefaultPieceFreight(Integer offSet, Integer limit) {
        return defaultPieceFreightDao.findAllDefaultPieceFreight(offSet, limit);
    }

    @Override
    public List<SpecialFreightPo> findAllSpecialFreight(Integer offSet, Integer limit) {
        return specialFreightDao.findAllSpecialFreight(offSet, limit);
    }

    @Override
    public DefaultFreightPo findDefaultFreightById(Integer id) {
        return defaultFreightDao.findDefaultFreightById(id);
    }

    @Override
    public DefaultPieceFreightPo findDefaultPieceFreightById(Integer id) {
        return defaultPieceFreightDao.findDefaultPieceFreightById(id);
    }

    @Override
    public SpecialFreightPo findSpecialFreightById(Integer id) {
        return specialFreightDao.findSpecialFreightById(id);
    }

    @Override
    public Integer addDefaultFreight(DefaultFreight defaultFreight) {
        return defaultFreightDao.addDefaultFreight(defaultFreight);
    }

    @Override
    public Integer addDefaultPieceFreight(DefaultPieceFreight defaultPieceFreight) {
        return defaultPieceFreightDao.addDefaultPieceFreight(defaultPieceFreight);
    }

    @Override
    public Integer addSpecialFreight(SpecialFreight specialFreight) {
        return specialFreightDao.addSpecialFreight(specialFreight);
    }

    @Override
    public Integer updateDefaultFreight(DefaultFreight defaultFreight) {
        return defaultFreightDao.updateDefaultFreight(defaultFreight);
    }

    @Override
    public Integer updateDefaultPieceFreight(DefaultPieceFreight defaultPieceFreight) {
        return defaultPieceFreightDao.updateDefaultPieceFreight(defaultPieceFreight);
    }

    @Override
    public Integer updateSpecialFreight(SpecialFreight specialFreight) {
        return specialFreightDao.updateSpecialFreight(specialFreight);
    }

    @Override
    public Integer deleteDefaultFreight(Integer id) {
        return defaultFreightDao.deleteDefaultFreight(id);
    }

    @Override
    public Integer deleteDefaultPieceFreight(Integer id) {
        return defaultPieceFreightDao.deleteDefaultPieceFreight(id);
    }

    @Override
    public Integer deleteSpecialFreight(Integer id) {
        return specialFreightDao.deleteSpecialFreight(id);
    }
}
