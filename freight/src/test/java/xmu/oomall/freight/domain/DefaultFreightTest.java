//package xmu.oomall.freight.domain;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import xmu.oomall.freight.FreightApplication;
//import xmu.oomall.freight.dao.DefaultFreightDao;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest(classes = FreightApplication.class)
//public class DefaultFreightTest {
//    @Autowired
//    private DefaultFreightDao dao;
//
//    private DefaultFreightPo defaultFreightPo;
//
//    private List<Goods> goodsList = new ArrayList<>();
//    private List<Integer> numList = new ArrayList<>();
//
//    @BeforeEach
//    public void before() {
//        AddressPo address = new AddressPo();
//        address.setCityId(10);
//
//        defaultFreightPo = dao.findDefaultFreightByDest(address);
//        Goods goods;
//
//        /**
//         *  15
//         */
//        goods=new Goods();
//        goods.setWeight(BigDecimal.valueOf(0.3));
//        goodsList.add(goods);
//        numList.add(1);
//
//        /**
//         * 20*2=40
//         */
//        goods=new Goods();
//        goods.setWeight(BigDecimal.valueOf(8));
//        goodsList.add(goods);
//        numList.add(2);
//
//        /**
//         * 30*3=90
//         */
//        goods=new Goods();
//        goods.setWeight(BigDecimal.valueOf(15));
//        goodsList.add(goods);
//        numList.add(3);
//
//        /**
//         * 38*4=152
//         */
//        goods=new Goods();
//        goods.setWeight(BigDecimal.valueOf(55));
//        goodsList.add(goods);
//        numList.add(4);
//
//        /**
//         * 51.5
//         */
//        goods=new Goods();
//        goods.setWeight(BigDecimal.valueOf(305));
//        goodsList.add(goods);
//        numList.add(1);
//
//        /**
//         * total = 348.5
//         */
//    }
//
//    @Test
//    public void cacuFeeTest() {
////        BigDecimal fee = defaultFreightPo.cacuFee(goodsList, numList);
////
////        assertEquals(BigDecimal.valueOf(348.5), fee.setScale(1));
//    }
//}
