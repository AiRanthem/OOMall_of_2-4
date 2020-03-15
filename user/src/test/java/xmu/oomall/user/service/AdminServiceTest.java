//package xmu.oomall.user.service;
//
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//import xmu.oomall.user.UserApplication;
//import xmu.oomall.user.domain.Admin;
//import xmu.oomall.user.domain.AdminPo;
//import xmu.oomall.user.service.impl.AdminServiceImpl;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = UserApplication.class)
//@Transactional
//class AdminServiceTest {
//
//    @Autowired
//    private AdminServiceImpl adminService;
//
//    @Before
//    public void setUpAll() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    @Rollback
//    void addAdmin() {
//        AdminPo adminPo = new AdminPo();
//        Admin admin = new Admin(adminPo);
//        admin.setUsername("admin");
//        admin.setPassword("qwerty888");
//        admin.setRoleId(123546);
//        admin.setGmtCreate(LocalDateTime.of(2019,10,6,10,25,36));
//        admin.setGmtModified(LocalDateTime.of(2019,10,8,20,37,6));
//        admin.setBeDeleted(false);
//
//        Integer beSuccess = adminService.addAdmin(admin);
//        if (beSuccess == 1) {
//            System.out.println("管理员添加成功： admin=" + admin);
//        } else {
//            System.out.println("管理员添加失败");
//        }
//        assertEquals(beSuccess, 1);
//    }
//
//    @Test
//    @Rollback
//    void getAdminById() {
//        AdminPo admin= adminService.getAdminById(1);
//        System.out.println(admin);
//    }
//
//    @Test
//    @Rollback
//    void getAllAdmin() {
//        List<AdminPo> adminList= new ArrayList<>();
//        try {
//            adminList= adminService.getAllAdminByName(1,5, "liming");
//        } catch (Exception e) {
//            if (adminList == null) {
//                System.out.println("没有找到admin");
//            } else {
//                for (AdminPo admin : adminList) {
//                    System.out.println(admin);
//                }
//            }
//        }
//    }
//
//    @Test
//    @Rollback
//    void updateAdmin() {
//        AdminPo adminPo = new AdminPo();
//        Admin admin = new Admin(adminPo);
//        admin.setId(1);
//        admin.setUsername("admin");
//        admin.setPassword("qwerty999");
//        admin.setRoleId(123546);
//        admin.setGmtCreate(LocalDateTime.of(2019,10,6,10,25,36));
//        admin.setGmtModified(LocalDateTime.of(2019,10,8,20,37,6));
//        admin.setBeDeleted(false);
//
//        Integer beSuccess = adminService.updateAdmin(admin);
//        if (beSuccess == 1) {
//            System.out.println("管理员更新成功： admin="+admin);
//        } else {
//            System.out.println("管理员更新失败");
//        }
//        assertEquals(beSuccess, 1);
//    }
//
//    @Test
//    @Rollback
//    void deleteAdmin() {
//        Integer beSuccess = adminService.deleteAdmin(1);
//        if (beSuccess == 1) {
//            System.out.println("管理员删除成功");
//        } else {
//            System.out.println("管理员删除失败");
//        }
//        assertEquals(beSuccess, 1);
//    }
//}