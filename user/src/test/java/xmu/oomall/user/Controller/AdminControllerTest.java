//package xmu.oomall.user.Controller;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import xmu.oomall.user.UserApplication;
//import xmu.oomall.user.controller.AdminController;
//import xmu.oomall.user.domain.Admin;
//import xmu.oomall.user.domain.AdminPo;
//import xmu.oomall.user.service.AdminService;
//import xmu.oomall.user.util.JacksonUtil;
//
//import java.time.LocalDateTime;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(classes = UserApplication.class)
//@AutoConfigureMockMvc
//@Transactional
//public class AdminControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private AdminService adminService;
//
//    @InjectMocks
//    private AdminController adminController;
//
//    @Test
//    @Rollback
//    void createAdmin() throws Exception {
//        AdminPo adminPo = new AdminPo();
//        Admin admin = new Admin(adminPo);
//        admin.setUsername("admin");
//        admin.setPassword("qwerty888");
//        admin.setRoleId(15669);
//
//        String jsonString = JacksonUtil.toJson(admin);
//
//        System.out.println(jsonString);
//
//        String responseString = this.mockMvc
//                .perform(MockMvcRequestBuilders.post("/admins")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType("application/json;charset=UTF-8")
//                        .content(jsonString))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse().getContentAsString();
//
//        System.out.println(responseString);
//    }
//
//    @Test
//    @Rollback
//    void getAdminInfo() throws Exception {
//        String jsonString = "";
//
//        System.out.println(jsonString);
//
//        String responseString = this.mockMvc
//                .perform(MockMvcRequestBuilders.get("/admins/1")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType("application/json;charset=UTF-8")
//                        .content(jsonString))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse().getContentAsString();
//
//        System.out.println(responseString);
//    }
//
//    @Test
//    @Rollback
//    void adminList() throws Exception {
//        String jsonString = "";
//
//        System.out.println(jsonString);
//
//        String responseString = this.mockMvc
//                .perform(MockMvcRequestBuilders.get("/admins")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .param("adminName","liming")
//                        .param("page","1")
//                        .param("limit","5")
//                        .contentType("application/json;charset=UTF-8")
//                        .content(jsonString))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse().getContentAsString();
//
//        System.out.println(responseString);
//    }
//
//    @Test
//    @Rollback
//    void updateAdmin() throws Exception {
//        AdminPo adminPo = new AdminPo();
//        Admin admin = new Admin(adminPo);
//        admin.setId(1);
//        admin.setUsername("admin");
//        admin.setPassword("qwerty999");
//        admin.setRoleId(12256);
//        admin.setGmtCreate(LocalDateTime.of(2019,10,6,10,25,36));
//
//        String jsonString = JacksonUtil.toJson(admin);
//
//        System.out.println(jsonString);
//
//        String responseString = this.mockMvc
//                .perform(MockMvcRequestBuilders.put("/admins/1")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType("application/json;charset=UTF-8")
//                        .content(jsonString))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse().getContentAsString();
//
//        System.out.println(responseString);
//    }
//
//    @Test
//    @Rollback
//    void deleteAdmin() throws Exception {
//        String jsonString = "";
//
//        System.out.println(jsonString);
//
//        String responseString = this.mockMvc
//                .perform(MockMvcRequestBuilders.delete("/admins/1")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType("application/json;charset=UTF-8")
//                        .content(jsonString))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse().getContentAsString();
//
//        System.out.println(responseString);
//    }
//}
