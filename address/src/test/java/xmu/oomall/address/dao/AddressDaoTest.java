//package xmu.oomall.address.dao;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import xmu.oomall.address.AddressApplication;
//import xmu.oomall.address.domain.Address;
//import xmu.oomall.address.domain.AddressPo;
//import xmu.oomall.address.domain.Region;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest(classes = AddressApplication.class)
//@Transactional
//public class AddressDaoTest {
//
//    @Autowired
//    private AddressDao addressDao;
//
//    @Test
//    @Rollback
//    void addAddress() throws Exception {
//        AddressPo addressPo = new AddressPo();
//        addressPo.setProvinceId(13);
//        addressPo.setCityId(147);
//        addressPo.setCountyId(95);
//        addressPo.setAddressDetail("海韵学生公寓");
//        addressPo.setBeDefault(true);
//        addressPo.setMobile("23333333");
//        addressPo.setConsignee("ooad");
//        addressPo.setPostalCode("150330");
//        addressPo.setUserId("110");
//        Address address = new Address(addressPo);
//        LocalDateTime localDateTime = LocalDateTime.now();
//        address.setGmtCreate(localDateTime);
//        address.setGmtModified(localDateTime);
//        Integer success = addressDao.addAddress(address);
//        System.out.println(success);
//    }
//
//    @Test
//    @Rollback
//    void findAddressById() throws Exception {
//        System.out.println(addressDao.getAddressById(1));
//    }
//
//    @Test
//    @Rollback
//    void findAllAddress() throws Exception {
//        List<AddressPo> addressList = addressDao.getAllAddress(111, 0, 5);
//        System.out.println(addressList);
//    }
//
//    @Test
//    @Rollback
//    void updateAddress() throws Exception {
//        AddressPo addressPo = new AddressPo();
//        addressPo.setProvinceId(13);
//        addressPo.setCityId(147);
//        addressPo.setCountyId(95);
//        addressPo.setAddressDetail("海韵学生公寓");
//        addressPo.setBeDefault(true);
//        addressPo.setMobile("23333333");
//        addressPo.setConsignee("ooad");
//        addressPo.setPostalCode("150330");
//        addressPo.setUserId("110");
//        Address address = new Address(addressPo);
//        address.setId(1);
//        LocalDateTime localDateTime = LocalDateTime.now();
//        address.setGmtModified(localDateTime);
//        System.out.println(address);
//        Integer success = addressDao.updateAddress(address);
//        System.out.println(success);
//    }
//
//    @Test
//    @Rollback
//    void clearAddress() throws Exception {
//        Integer success = addressDao.deleteAddress(1);
//        System.out.println(success);
//    }
//
//    @Test
//    @Rollback
//    void getRegionById() throws Exception {
//        Region region = addressDao.getRegionById(10);
//        assertEquals(region.getName(), "江苏省");
//    }
//
//    @Test
//    @Rollback
//    void getRegionsByPid() throws Exception {
//        List<Region> regionList = addressDao.getRegionsByPid(10);
//        System.out.println(regionList);
//    }
//}
