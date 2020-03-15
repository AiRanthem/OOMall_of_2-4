package xmu.oomall.express.controller.vo;

import java.util.List;

/**
 * @Author zty
 * @Description SfController.getExpressNumber的VO对象
 * @Date  created in 12/4 15:26
 */
public class GetExNumVO {
    /**
     * 货物
     */
    private List<String> things;

    /****************************************************
     * 生成代码
     ****************************************************/

    public List<String> getThings() {
        return things;
    }

    public void setThings(List<String> things) {
        this.things = things;
    }

    @Override
    public String toString() {
        return "GetExNumVO{" +
                "things=" + things +
                '}';
    }
}
