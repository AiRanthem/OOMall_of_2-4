//package xmu.oomall.freight.dao;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import xmu.oomall.freight.FreightApplication;
//import xmu.oomall.freight.domain.AddressPo;
//import xmu.oomall.freight.domain.DefaultFreight;
//import xmu.oomall.freight.domain.DefaultFreightPo;
//
//import java.util.List;
//
//@SpringBootTest(classes = FreightApplication.class)
//@Transactional
//class DefaultFreightDaoTest {
//
//    @Autowired
//    private DefaultFreightDao defaultFreightDao;
//
//    @Test
//    @Rollback
//    void findAllDefaultFreight() {
//        List<DefaultFreightPo> defaultFreightList = defaultFreightDao.findAllDefaultFreight(1,5);
//        System.out.println(defaultFreightList);
//    }
//
//    @Test
//    void findDefaultFreightById() {
//        DefaultFreightPo defaultFreight = defaultFreightDao.findDefaultFreightById(1);
//        System.out.println(defaultFreight);
//    }
//
//    @Test
//    void findDefaultFreightByDest() {
//        AddressPo address = new AddressPo();
//        address.setCityId(147);
//        address.setProvinceId(13);
//        DefaultFreightPo defaultFreight = defaultFreightDao.findDefaultFreightByDest(address);
//        System.out.println(defaultFreight);
//    }
//
//    @Test
//    void addDefaultFreight() {
//
//    }
//
//    @Test
//    void updateDefaultFreight() {
//
//    }
//
//    @Test
//    void deleteDefaultFreight() {
//
//    }
//}