//package xmu.oomall.user.domain;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import xmu.oomall.user.UserApplication;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@SpringBootTest(classes = UserApplication.class)
//class UserTest {
//
//    private User userA;
//
//    @BeforeEach
//    void setUp() {
//        UserPo userPo = new UserPo();
//        User user = new User(userPo);
//        user.setName("userA");
//        user.setNickname("Adam");
//        user.setPassword("qwerty888");
//        user.setGender(0);
//        user.setBirthday(LocalDate.of(1998, 3, 27));
//        user.setMobile("13389463725");
//        user.setRebate(25);
//        user.setAvatar("resources/userA.png");
//        user.setLastLoginTime(LocalDateTime.of(2019,12,7,15,38,5));
//        user.setLastLoginIp("48.37.2.64");
//        user.setUserLevel(3);
//        user.setWxOpenId("ocgT_KJGds6546DF5fd64644sdFSD");
//        user.setSessionKey("fadsSDFsd4fg54fa9sdf6ga");
//        user.setGmtCreate(LocalDateTime.of(2019,10,6,10,25,36));
//        user.setGmtModified(LocalDateTime.of(2019,10,8,20,37,6));
//        user.setBeDeleted(false);
//        this.userA = user;
//    }
//
//    @Test
//    void getDateTime() {
//        System.out.println(userA.getLastLoginTime());
////        assertEquals(userA.getId(), 1);
//    }
//}