package xmu.oomall.freight.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: 数据库与对象模型标准组
 * @Description: 特殊运费模板
 * @Date: Created in 16:00 2019/11/29
 * @Modified By:
 **/

public class SpecialFreightPo {

    private Integer id;
    /**
     * 首几件
     */
    private Integer firstNumPiece;
    /**
     * 首几件价格
     */
    private BigDecimal firstNumPiecePrice;
    /**
     * 续几件
     */
    private Integer continueNumPiece;
    /**
     * 续几件的价格
     */
    private BigDecimal continueNumPiecePrice;

    /*****************************************************************
     * 实用方法
     *****************************************************************/

    /**
     * 计算费用
     * @param num 几件
     * @param rate 几倍
     * @return 价格
     */
    public BigDecimal cacuSpecialFee(Integer num, BigDecimal rate){
        /**
         * 首件费用
         */
        BigDecimal fee = firstNumPiecePrice;

        /**
         * 续件费用
         * 先算出续了几件
         * 再算出增加了几次 “每增加x件”
         * 加费用
         */
        if(num > firstNumPiece){
            BigDecimal add = BigDecimal.valueOf(num - firstNumPiece);
            add = add.divide(BigDecimal.valueOf(continueNumPiece), BigDecimal.ROUND_CEILING);
            fee = fee.add(add.multiply(continueNumPiecePrice));

        }

        /**
         * 乘倍率返回
         */
        return fee.multiply(rate);
    }


    /*****************************************************************
     * 生成代码
     *****************************************************************/

    @Override
    public String toString() {
        return "SpecialFreightPo{" +
                "id=" + id +
                ", firstNumPiece=" + firstNumPiece +
                ", firstNumPiecePrice=" + firstNumPiecePrice +
                ", continueNumPiece=" + continueNumPiece +
                ", continueNumPiecePrice=" + continueNumPiecePrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        SpecialFreightPo that = (SpecialFreightPo) o;
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

    public Integer getFirstNumPiece() {
        return firstNumPiece;
    }

    public void setFirstNumPiece(Integer firstNumPiece) {
        this.firstNumPiece = firstNumPiece;
    }

    public BigDecimal getFirstNumPiecePrice() {
        return firstNumPiecePrice;
    }

    public void setFirstNumPiecePrice(BigDecimal firstNumPiecePrice) {
        this.firstNumPiecePrice = firstNumPiecePrice;
    }

    public Integer getContinueNumPiece() {
        return continueNumPiece;
    }

    public void setContinueNumPiece(Integer continueNumPiece) {
        this.continueNumPiece = continueNumPiece;
    }

    public BigDecimal getContinueNumPiecePrice() {
        return continueNumPiecePrice;
    }

    public void setContinueNumPiecePrice(BigDecimal continueNumPiecePrice) {
        this.continueNumPiecePrice = continueNumPiecePrice;
    }
}
