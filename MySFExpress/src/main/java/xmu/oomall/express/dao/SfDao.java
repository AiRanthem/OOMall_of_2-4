package xmu.oomall.express.dao;

import xmu.oomall.express.domain.ExpressOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * 顺丰Dao对象
 * @author zty
 */
@Repository
public class SfDao {
    private static final Logger logger = LoggerFactory.getLogger(SfDao.class);

    /**
     * 存订单
     * todo: 数据库存储
     */
    public Boolean save(ExpressOrder order) {
        return true;
    }
}
