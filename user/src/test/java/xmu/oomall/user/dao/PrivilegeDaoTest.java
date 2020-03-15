//package xmu.oomall.user.dao;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import xmu.oomall.user.UserApplication;
//import xmu.oomall.user.domain.Privilege;
//import xmu.oomall.user.domain.Role;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest(classes = UserApplication.class)
//@Transactional
//class PrivilegeDaoTest {
//
//    @Autowired
//    private PrivilegeDao privilegeDao;
//
//    @Test
//    @Rollback
//    void findAllRoles() {
//        System.out.println(privilegeDao.findAllRoles());
//    }
//
//    @Test
//    @Rollback
//    void addRole() {
//        Role role = new Role();
//        role.setName("邱老板");
//        privilegeDao.addRole(role);
//        System.out.println(privilegeDao.findAllRoles());
//    }
//
//    @Test
//    @Rollback
//    void updateRole() {
//    }
//
//    @Test
//    @Rollback
//    void deleteRole() {
//    }
//
//    @Test
//    @Rollback
//    void findAllPrivilegesByRoleId() {
//        System.out.println(privilegeDao.findAllPrivilegesByRoleId(0));
//    }
//
//    @Test
//    @Rollback
//    void updatePrivileges() {
//        List<Privilege> privilegeList = new ArrayList<>();
//        Privilege privilege = new Privilege();
//        privilege.setRoleId(0);
//        privilege.setUrl("/hello");
//        privilegeList.add(privilege);
//        privilege.setUrl("/logout");
//        privilegeList.add(privilege);
//        privilegeList = privilegeDao.updatePrivileges(0, privilegeList);
//        for (Privilege privilege1 : privilegeList) {
//            System.out.println(privilege1);
//        }
//
//    }
//}