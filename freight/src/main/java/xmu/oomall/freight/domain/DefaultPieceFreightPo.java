package xmu.oomall.freight.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: 数据库与对象模型标准组
 * @Description: 默认件数模板
 * @Date: Created in 16:00 2019/11/29
 * @Modified By:
 **/

public class DefaultPieceFreightPo {

    private Integer id;
    /**
     * 目的地，是一个id的list
     */
    private String destination;
    /**
     * 单位比例
     */
    private BigDecimal unitRate;

    /**
     * 快递送到需要的时间（次日 或者 1-2天等 ）
     */
    private String requireDays;

    @Override
    public String toString() {
        return "DefaultPieceFreightPo{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", unitRate=" + unitRate +
                ", requireDays=" + requireDays +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        DefaultPieceFreightPo that = (DefaultPieceFreightPo) o;
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

    public BigDecimal getUnitRate() {
        return unitRate;
    }

    public void setUnitRate(BigDecimal unitRate) {
        this.unitRate = unitRate;
    }

    public String getRequireDays() {
        return requireDays;
    }

    public void setRequireDays(String requireDays) {
        this.requireDays = requireDays;
    }
}
