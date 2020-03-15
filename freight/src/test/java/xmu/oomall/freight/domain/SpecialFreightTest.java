//package xmu.oomall.freight.domain;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import xmu.oomall.freight.FreightApplication;
//import xmu.oomall.freight.dao.SpecialFreightDao;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest(classes = FreightApplication.class)
//public class SpecialFreightTest {
//    @Autowired
//    private SpecialFreightDao dao;
//
//    BigDecimal rate = BigDecimal.valueOf(0.5);
//
//    @Test void specialOneTest() {
//        SpecialFreightPo freight = dao.findSpecialFreightById(1);
//
//        assertEquals(BigDecimal.valueOf(0.5),freight.cacuSpecialFee(4,rate));
//        assertEquals(BigDecimal.valueOf(0.5),freight.cacuSpecialFee(5,rate));
//        assertEquals(BigDecimal.valueOf(1.5),freight.cacuSpecialFee(7,rate));
//        assertEquals(BigDecimal.valueOf(2.5),freight.cacuSpecialFee(9,rate));
//    }
//
//    @Test void specialTwoTest() {
//        SpecialFreightPo freight = dao.findSpecialFreightById(2);
//
//        assertEquals(BigDecimal.valueOf(1.5), freight.cacuSpecialFee(7,rate));
//        assertEquals(BigDecimal.valueOf(1.0), freight.cacuSpecialFee(6,rate));
//        assertEquals(BigDecimal.valueOf(1.5), freight.cacuSpecialFee(8,rate));
//        assertEquals(BigDecimal.valueOf(0.5), freight.cacuSpecialFee(3,rate));
//        assertEquals(BigDecimal.valueOf(0.5), freight.cacuSpecialFee(4,rate));
//    }
//}
