//package xmu.oomall.freight.dao;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import xmu.oomall.freight.FreightApplication;
//import xmu.oomall.freight.domain.AddressPo;
//import xmu.oomall.freight.domain.DefaultPieceFreight;
//import xmu.oomall.freight.domain.DefaultPieceFreightPo;
//
//@SpringBootTest(classes = FreightApplication.class)
//@Transactional
//class DefaultPieceFreightDaoTest {
//    @Autowired
//    DefaultPieceFreightDao defaultPieceFreightDao;
//
//    @Test
//    void findAllDefaultPieceFreight() {
//    }
//
//    @Test
//    void findDefaultPieceFreightById() {
//    }
//
//    @Test
//    void findDefaultPieceFreightByDest() {
//        AddressPo address = new AddressPo();
//        address.setCityId(147);
//        address.setProvinceId(13);
//        DefaultPieceFreightPo defaultFreight = defaultPieceFreightDao.findDefaultPieceFreightByDest(address);
//        System.out.println(defaultFreight);
//    }
//
//    @Test
//    void addDefaultPieceFreight() {
//    }
//
//    @Test
//    void updateDefaultPieceFreight() {
//    }
//
//    @Test
//    void deleteDefaultPieceFreight() {
//    }
//}