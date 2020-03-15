//package xmu.oomall.user.service;
//
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//import xmu.oomall.user.UserApplication;
//import xmu.oomall.user.domain.User;
//import xmu.oomall.user.domain.UserPo;
//import xmu.oomall.user.service.impl.UserServiceImpl;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = UserApplication.class)
//@Transactional
//class UserServiceTest {
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    @Before
//    public void setUpAll() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void addUser() {
//        UserPo userPo = new UserPo();
//        User user = new User(userPo);
//        user.setName("userA");
//        user.setNickname("Adam");
//        user.setPassword("qwerty888");
//        user.setGender(0);
//        user.setBirthday(LocalDate.of(1998, 3, 1));
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
//
//        Integer beSuccess = userService.addUser(user);
//        if (beSuccess == 1) {
//            System.out.println("用户添加成功： user=" + user);
//        } else {
//            System.out.println("用户添加失败");
//        }
//    }
//
//    @Test
//    void getUserById() {
//        UserPo user= userService.findUserById(1);
//        System.out.println(user);
//    }
//
//    @Test
//    void getAllUser() {
//        List<UserPo> userList= userService.getAllUser(0,5);
//        for (UserPo user : userList) {
//            System.out.println(user);
//        }
//    }
//
//    @Test
//    void updateUser() {
//        UserPo userPo = new UserPo();
//        User user = new User(userPo);
//        user.setId(8);
//        user.setName("userA");
//        user.setNickname("Adam");
//        user.setPassword("qwerty999");
//        user.setGender(0);
//        user.setBirthday(LocalDate.of(1998, 3, 1));
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
//
//        Integer beSuccess = userService.updateUser(user);
//        if (beSuccess == 1) {
//            System.out.println("用户更新成功： user="+user);
//        } else {
//            System.out.println("用户更新失败");
//        }
//    }
//
//    @Test
//    void deleteUser() {
//        Integer beSuccess = userService.deleteUser(1);
//        if (beSuccess == 1) {
//            System.out.println("用户删除成功");
//        } else {
//            System.out.println("用户删除失败");
//        }
//    }
//}