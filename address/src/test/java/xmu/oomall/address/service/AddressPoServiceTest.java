//package xmu.oomall.address.service;
//
//import org.junit.Before;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//import xmu.oomall.address.AddressApplication;
//import xmu.oomall.address.domain.Address;
//import xmu.oomall.address.domain.AddressPo;
//import xmu.oomall.address.domain.Region;
//import xmu.oomall.address.service.impl.AddressServiceImpl;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = AddressApplication.class)
//@Transactional
//class AddressPoServiceTest {
//
//    @Autowired
//    private AddressServiceImpl addressService;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    @Rollback
//    void addAddress() {
//        AddressPo addressPo = new AddressPo();
//        addressPo.setProvinceId(13);
//        addressPo.setCityId(147);
//        addressPo.setCountyId(95);
//        addressPo.setAddressDetail("海韵学生公寓");
//        addressPo.setBeDefault(true);
//        addressPo.setMobile("23333333");
//        addressPo.setConsignee("ooad");
//        addressPo.setPostalCode("150330");
//        addressPo.setUserId("111");
//        Address address = new Address(addressPo);
//        LocalDateTime localDateTime = LocalDateTime.now();
//        address.setGmtCreate(localDateTime);
//        address.setGmtModified(localDateTime);
//        Integer success = addressService.addAddress(address);
//        System.out.println(success);
//    }
//
//    @Test
//    @Rollback
//    void updateAddress() {
//        AddressPo addressPo = new AddressPo();
//        addressPo.setId(1);
//        addressPo.setProvinceId(12);
//        addressPo.setCityId(147);
//        addressPo.setCountyId(95);
//        addressPo.setAddressDetail("厦大学生公寓");
//        addressPo.setBeDefault(true);
//        addressPo.setMobile("23333333");
//        addressPo.setConsignee("ooad");
//        addressPo.setPostalCode("150330");
//        addressPo.setUserId("111");
//        Address address = new Address(addressPo);
//        LocalDateTime localDateTime = LocalDateTime.now();
//        address.setGmtModified(localDateTime);
//        System.out.println(addressPo);
//        Integer success = addressService.updateAddress(address);
//        System.out.println(success);
//    }
//
//    @Test
//    @Rollback
//    void findAllAddress() {
//        List<AddressPo> addressPoList = addressService.findAllAddress(111, 0, 5);
//        System.out.println(addressPoList);
//    }
//
//    @Test
//    @Rollback
//    void findAddressById() {
//        AddressPo addressPo = addressService.findAddressById(1);
//    }
//
//    @Test
//    @Rollback
//    void clearAddress() {
//        Integer success = addressService.clearAddress(1);
//        List<AddressPo> addressPos = addressService.findAllAddress(111, 0, 5);
//        System.out.println(addressPos);
//    }
//
//    @Test
//    @Rollback
//    void findRegionById() {
//        Region region = addressService.findRegionById(10);
//        Assertions.assertEquals(region.getName(), "江苏省");
//    }
//
//    @Test
//    @Rollback
//    void findRegionsByPid() {
//        List<Region> regionList = addressService.findRegionsByPid(10);
//        System.out.println(regionList);
//    }
//}