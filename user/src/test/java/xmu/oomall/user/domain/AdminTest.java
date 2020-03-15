//package xmu.oomall.user.domain;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import xmu.oomall.user.UserApplication;
//
//import java.time.LocalDateTime;
//
//@SpringBootTest(classes = UserApplication.class)
//class AdminTest {
//
//    private Admin admin;
//
//    @BeforeEach
//    void setUp() {
//        AdminPo adminPo = new AdminPo();
//        Admin admin = new Admin(adminPo);
//        admin.setUsername("admin");
//        admin.setPassword("qwerty888");
//        admin.setGmtCreate(LocalDateTime.of(2019,10,6,10,25,36));
//        admin.setGmtModified(LocalDateTime.of(2019,10,8,20,37,6));
//        admin.setBeDeleted(false);
//        this.admin = admin;
//    }
//
//    @Test
//    void getDateTime() {
//        System.out.println(admin.getGmtModified());
//    }
//}