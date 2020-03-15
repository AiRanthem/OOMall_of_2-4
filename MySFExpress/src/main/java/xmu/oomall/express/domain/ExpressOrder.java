package xmu.oomall.express.domain;

import java.util.List;

/**
 * @author zty
 */

public class ExpressOrder {
    /**
     * 快递单号
     */
    String exNum;

    /**
     * 运费
     */
    Integer price;

    /**
     * 货物信息
     */
    List<String> things;

    /**
     * 加钱
     * @param add
     */
    public void addMoney(Integer add) {
        price += add;
    }

    /**
     * 默认构造函数
     */
    public ExpressOrder() {
        this.exNum = null;
        this.price = 0;
        this.things = null;
    }

    /****************************************************
     * 生成代码
     ****************************************************/

    public ExpressOrder(String exNum, Integer price, List<String> things) {
        this.exNum = exNum;
        this.price = price;
        this.things = things;
    }

    public String getExNum() {
        return exNum;
    }

    public void setExNum(String exNum) {
        this.exNum = exNum;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<String> getThings() {
        return things;
    }

    public void setThings(List<String> things) {
        this.things = things;
    }
}
