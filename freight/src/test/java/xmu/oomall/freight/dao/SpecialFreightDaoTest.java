//package xmu.oomall.freight.dao;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import xmu.oomall.freight.FreightApplication;
//import xmu.oomall.freight.domain.SpecialFreight;
//import xmu.oomall.freight.domain.SpecialFreightPo;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(classes = FreightApplication.class)
//@Transactional
//class SpecialFreightDaoTest {
//    @Autowired
//    SpecialFreightDao dao;
//
//    @Test
//    void findAllSpecialFreight() {
//    }
//
//    @Test
//    void findSpecialFreightById() {
//        SpecialFreightPo freight = dao.findSpecialFreightById(1);
//        System.out.println(freight);
//
//        freight = dao.findSpecialFreightById(2);
//        System.out.println(freight);
//
//        freight = dao.findSpecialFreightById(3);
//        System.out.println(freight);
//
//        freight = dao.findSpecialFreightById(4);
//        System.out.println(freight);
//    }
//
//    @Test
//    void addSpecialFreight() {
//    }
//
//    @Test
//    void updateSpecialFreight() {
//    }
//
//    @Test
//    void deleteSpecialFreight() {
//    }
//}