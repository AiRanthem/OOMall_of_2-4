package xmu.oomall.freight.controller.vo;

import xmu.oomall.freight.domain.AddressPo;
import xmu.oomall.freight.domain.Goods;

import java.util.List;

/**
 * @author zty
 * 计算运费API的VO对象
 */
public class ExpressVO {
    List<Goods> goodsList;
    List<Integer> numList;
    AddressPo address;

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public List<Integer> getNumList() {
        return numList;
    }

    public void setNumList(List<Integer> numList) {
        this.numList = numList;
    }

    public AddressPo getAddress() {
        return address;
    }

    public void setAddress(AddressPo address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ExpressVO{" +
                "goodsList=" + goodsList +
                ", numList=" + numList +
                ", address=" + address +
                '}';
    }
}
