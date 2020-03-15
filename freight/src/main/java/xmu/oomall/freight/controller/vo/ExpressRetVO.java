package xmu.oomall.freight.controller.vo;

import java.math.BigDecimal;

/**
 * @author zty
 * get /expressFee 方法返回的VO
 */
public class ExpressRetVO {
    private BigDecimal fee;

    @Override
    public String toString() {
        return "ExpressRetVO{" +
                "fee=" + fee +
                '}';
    }

    public ExpressRetVO(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}


