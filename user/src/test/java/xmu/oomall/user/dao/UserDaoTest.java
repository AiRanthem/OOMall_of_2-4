//package xmu.oomall.user.dao;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import xmu.oomall.user.UserApplication;
//import xmu.oomall.user.domain.User;
//import xmu.oomall.user.domain.UserPo;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest(classes = UserApplication.class)
//@Transactional
//class UserDaoTest {
//    @Autowired
//    private UserDao userDao;
//
//    @Test
//    @Rollback
//    void addUser() {
//        UserPo userPo = new UserPo();
//        User user = new User(userPo);
//        user.setName("userB");
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
//
//        Integer beSuccess = userDao.addUser(user);
//        if (beSuccess == 1) {
//            System.out.println("用户添加成功： user=" + user);
//        } else {
//            System.out.println("用户添加失败");
//        }
//    }
//
//    @Test
//    @Rollback
//    void getUserById() {
//        UserPo user= userDao.getUserById(7);
//        System.out.println(user);
//    }
//
//    @Test
//    @Rollback
//    void getPasswdByName() {
//        UserPo user = userDao.getUserByName("userA");
//        System.out.println(user);
//    }
//
//    @Test
//    @Rollback
//    void getAllUser() {
//        List<UserPo> userList= userDao.getAllUser(0,5);
//        for (UserPo user : userList) {
//            System.out.println(user);
//        }
//    }
//
//    @Test
//    @Rollback
//    void updateUser() {
//        UserPo userPo = new UserPo();
//        User user = new User(userPo);
//        user.setId(7);
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
//        Integer beSuccess = userDao.updateUser(user);
//        if (beSuccess == 1) {
//            System.out.println("用户更新成功");
//        } else {
//            System.out.println("用户更新失败");
//        }
//    }
//
//    @Test
//    @Rollback
//    void deleteUser() {
//        Integer beSuccess = userDao.deleteUser(1);
//        if (beSuccess == 1) {
//            System.out.println("用户删除成功");
//        } else {
//            System.out.println("用户删除失败");
//        }
//    }
//}