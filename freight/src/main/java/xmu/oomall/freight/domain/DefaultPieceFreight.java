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

public class DefaultPieceFreight {

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
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean beDeleted;

    @Override
    public String toString() {
        return "DefaultPieceFreight{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", unitRate=" + unitRate +
                ", requireDays=" + requireDays +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", beDeleted=" + beDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        DefaultPieceFreight that = (DefaultPieceFreight) o;
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

    public DefaultPieceFreight(Integer id, String destination, BigDecimal unitRate, String requireDays, LocalDateTime gmtCreate, LocalDateTime gmtModified, Boolean beDeleted) {
        this.id = id;
        this.destination = destination;
        this.unitRate = unitRate;
        this.requireDays = requireDays;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.beDeleted = beDeleted;
    }

    public DefaultPieceFreight(DefaultPieceFreightPo defaultPieceFreightPo) {
        this.id = defaultPieceFreightPo.getId();
        this.destination = defaultPieceFreightPo.getDestination();
        this.unitRate = defaultPieceFreightPo.getUnitRate();
        this.requireDays = defaultPieceFreightPo.getRequireDays();
        this.gmtCreate = null;
        this.gmtModified = null;
        this.beDeleted = false;
    }

    public String getRequireDays() {
        return requireDays;
    }

    public void setRequireDays(String requireDays) {
        this.requireDays = requireDays;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getBeDeleted() {
        return beDeleted;
    }

    public void setBeDeleted(Boolean beDeleted) {
        this.beDeleted = beDeleted;
    }
}
