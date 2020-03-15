package xmu.oomall.express.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import xmu.oomall.express.dao.SfDao;
import xmu.oomall.express.domain.ExpressOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author zty
 * @date created in 15:54 12/4
 * 顺丰系统的服务类
 */
@Service
public class SfService {

    @Autowired
    private SfDao sfDao;
    /**
     * 递送功能，根据传来的信息生成并保存订单
     * @param order 运单
     * @return String exNum
     */
    public String express(ExpressOrder order, List<String> things) {
        /**
         * 根据当前时间生成一个单号
         */
        DateTime date = DateUtil.date();
        String dateStr = DateUtil.format(date,"yyyyMMddHHssSSS");
        Integer randNum = new Random().nextInt() % 100;
        String exNum = "SF" + dateStr + randNum;
        order.setExNum(exNum);

        /**
         * 填运单
         */
        Integer price = cacuMoney();
        order.setPrice(price);
        order.setThings(things);

        /**
         * 存运单
         */
        sfDao.save(order);

        return exNum;
    }

    public String express() {
        /**
         * 根据当前时间生成一个单号
         */
        DateTime date = DateUtil.date();
        String dateStr = DateUtil.format(date,"yyyyMMddHHssSSS");
        Integer randNum = new Random().nextInt(100);
        String exNum = "SF" + dateStr + randNum;

        return exNum;
    }

    /**
     * todo
     * 算钱，暂时为1
     * @return 1
     */
    private Integer cacuMoney() {
        return 1;
    }
}
