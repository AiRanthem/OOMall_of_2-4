package xmu.oomall.zuul.domain;

import java.util.Objects;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:地址
 * @Data:Created in 14:50 2019/11/29
 * @Modified By:
 **/

public class AddressPo {

    private Integer id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 特定地区id（对应行政区域表）
     */
    private Integer cityId;
    /**
     * 省份
     */
    private Integer provinceId;
    /**
     * 县区
     */
    private Integer countyId;
    /**
     * 地址详情
     */
    private String addressDetail;
    /**
     * 联系电话
     */
    private String mobile;
    /**
     * 邮政编码
     */
    private String postalCode;
    /**
     * 收件人
     */
    private String consignee;
    /**
     * 是否是默认地址
     */
    private boolean beDefault;

    @Override
    public String toString() {
        return "AddressPo{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", cityId=" + cityId +
                ", provinceId=" + provinceId +
                ", countyId=" + countyId +
                ", addressDetail='" + addressDetail + '\'' +
                ", mobile='" + mobile + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", consignee='" + consignee + '\'' +
                ", beDefault=" + beDefault +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        AddressPo addressPo = (AddressPo) o;
        return Objects.equals(id, addressPo.id);
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public boolean isBeDefault() {
        return beDefault;
    }

    public void setBeDefault(boolean beDefault) {
        this.beDefault = beDefault;
    }

}
