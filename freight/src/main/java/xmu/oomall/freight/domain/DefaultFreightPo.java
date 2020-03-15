package xmu.oomall.freight.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 数据库与对象模型标准组
 * @Description: 默认重量模板
 * @Date: Created in 16:00 2019/11/29
 * @Modified By:
 **/

public class DefaultFreightPo {
    private Integer id;
    /**
     * 货物运送的目的地（即收货地址）
     */
    private String destination;
    /**
     * 快递重量模板中快递首重0.5kg的价格
     */
    private BigDecimal firstHeavyPrice;
    /**
     * 续重的邮费价格
     */
    private BigDecimal continueHeavyPrice;
    /**
     * 10kg以上每kg的邮费价格（下一区间以下）
     */
    private BigDecimal over10Price;
    /**
     * 50kg以上每kg的邮费价格
     */
    private BigDecimal over50Price;
    /**
     * 100kg以上每kg的邮费价格
     */
    private BigDecimal over100Price;
    /**
     * 300kg以上每kg的邮费价格
     */
    private BigDecimal over300Price;
    /**
     * 快递送到需要的时间（次日 或者 1-2天等 ）
     */
    private String requireDays;


    /*****************************************************************
     * 实用方法
     *****************************************************************/

    /**
     * @param goodsList 商品
     * @param numList 数量
     * @return 总运费
     */
    public BigDecimal cacuFee(List<Goods> goodsList, List<Integer> numList) {
        BigDecimal fee = BigDecimal.valueOf(0);

        for (int i = 0; i < goodsList.size(); i++) {
            Integer num = numList.get(i);
            BigDecimal weight = goodsList.get(i).getWeight();
            fee = fee.add(cacuFeeForEach(num, weight));
        }
        return fee;
    }

    /**
     * @param num 数量
     * @param weight 重量
     * @return 运费
     */
    private BigDecimal cacuFeeForEach(Integer num, BigDecimal weight) {
        /**
         * 各个标准的重量阈值
         */
        BigDecimal firstHeavy = BigDecimal.valueOf(0.5);
        BigDecimal over10 = BigDecimal.valueOf(10);
        BigDecimal over50 = BigDecimal.valueOf(50);
        BigDecimal over100 = BigDecimal.valueOf(100);
        BigDecimal over300 = BigDecimal.valueOf(300);

        /**
         * 首重
         */
        BigDecimal fee = getFirstHeavyPrice();

        /**
         * 续重
         */
        if (weight.compareTo(firstHeavy) > 0) {
            fee = fee.add(this.getContinueHeavyPrice());
        }

        /**
         * 10kg以上
         */
        if (weight.compareTo(over10) > 0) {
            fee = fee.add(this.getOver10Price());
        }

        /**
         * 50kg以上
         */
        if (weight.compareTo(over50) > 0) {
            fee = fee.add(this.getOver50Price());
        }

        /**
         * 100kg以上
         */
        if (weight.compareTo(over100) > 0) {
            fee = fee.add(this.getOver100Price());
        }

        /**
         * 300kg以上
         */
        if (weight.compareTo(over300) > 0) {
            fee = fee.add(this.getOver300Price());
        }

        return fee.multiply(BigDecimal.valueOf(num));
    }

    /*****************************************************************
     * 生成代码
     *****************************************************************/

    @Override
    public String toString() {
        return "DefaultFreightPo{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", firstHeavyPrice=" + firstHeavyPrice +
                ", continueHeavyPrice=" + continueHeavyPrice +
                ", over10Price=" + over10Price +
                ", over50Price=" + over50Price +
                ", over100Price=" + over100Price +
                ", over300Price=" + over300Price +
                ", requireDays=" + requireDays +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        DefaultFreightPo that = (DefaultFreightPo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getFirstHeavyPrice() {
        return firstHeavyPrice;
    }

    public void setFirstHeavyPrice(BigDecimal firstHeavyPrice) {
        this.firstHeavyPrice = firstHeavyPrice;
    }

    public BigDecimal getContinueHeavyPrice() {
        return continueHeavyPrice;
    }

    public void setContinueHeavyPrice(BigDecimal continueHeavyPrice) {
        this.continueHeavyPrice = continueHeavyPrice;
    }

    public BigDecimal getOver10Price() {
        return over10Price;
    }

    public void setOver10Price(BigDecimal over10Price) {
        this.over10Price = over10Price;
    }

    public BigDecimal getOver50Price() {
        return over50Price;
    }

    public void setOver50Price(BigDecimal over50Price) {
        this.over50Price = over50Price;
    }

    public BigDecimal getOver100Price() {
        return over100Price;
    }

    public void setOver100Price(BigDecimal over100Price) {
        this.over100Price = over100Price;
    }

    public BigDecimal getOver300Price() {
        return over300Price;
    }

    public void setOver300Price(BigDecimal over300Price) {
        this.over300Price = over300Price;
    }

    public String getRequireDays() {
        return requireDays;
    }

    public void setRequireDays(String requireDays) {
        this.requireDays = requireDays;
    }
}
