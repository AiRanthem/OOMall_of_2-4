package xmu.oomall.user.Controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.user.UserApplication;
import xmu.oomall.user.controller.UserController;
import xmu.oomall.user.controller.vo.LoginVo;
import xmu.oomall.user.domain.User;
import xmu.oomall.user.domain.UserPo;
import xmu.oomall.user.service.UserService;
import xmu.oomall.user.util.JacksonUtil;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    private User user;

    private LoginVo loginVO;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(UserApplication.class);

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        UserPo userPo = new UserPo();
        User user = new User(userPo);
        user.setName("userA");
        user.setNickname("Adam");
        user.setPassword("qwerty888");
        user.setGender(0);
        user.setBirthday(LocalDate.of(1998, 3, 27));
        user.setMobile("13389463725");
        user.setRebate(25);
        user.setAvatar("resources/userA.png");
        user.setLastLoginTime(LocalDateTime.of(2019,12,7,15,38,5));
        user.setLastLoginIp("48.37.2.64");
        user.setUserLevel(3);
        user.setWxOpenId("ocgT_KJGds6546DF5fd64644sdFSD");
        user.setSessionKey("fadsSDFsd4fg54fa9sdf6ga");
        user.setGmtCreate(LocalDateTime.of(2019,10,6,10,25,36));
        user.setGmtModified(LocalDateTime.of(2019,10,8,20,37,6));
        user.setBeDeleted(false);
        this.user = user;
    }

    @Test
    @Rollback
    void SimpleLoginSuccessTest() throws Exception{
        JSONObject json = JSONUtil.createObj();
        json.put("username", "userA");
        json.put("password", "qwerty888");

        String jsonStr = json.toString();

        String responseString = this.mockMvc.perform(post("/login")
                .contentType("application/json;charset=UTF-8")
                .content(jsonStr))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        logger.info(responseString);
        System.out.println(responseString);

    }

    @Test
    @Rollback
    void ChangePasswordTest() throws Exception {
        JSONObject json = JSONUtil.createObj();
        json.put("password","666666");
        json.put("code","123456");
        json.put("mobile","13666666666");
        String jsonStr=json.toString();

        JSONObject json2 = JSONUtil.createObj();
        json2.put("userId","1");
        String json2Str = json2.toString();

        MockHttpServletResponse response = this.mockMvc.perform(put("/password")
                .contentType("application/json;charset=UTF-8")
                .header("userInfo",json2Str)
                .content(jsonStr))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse();

        System.out.println(response.getContentAsString());

    }

    @Test
    @Rollback
    void validateTest() throws Exception{
        String responseString = this.mockMvc.perform(get("/user/validate")
                .contentType("application/json;charset=UTF-8")
                .param("userId","1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        logger.info(responseString);
        System.out.println(responseString);
    }

    @Test
    @Rollback
    void SimpleJWTTest() throws Exception{
        JSONObject json = JSONUtil.createObj();
        json.put("username", "userA");
        json.put("password", "qwerty888");

        String jsonStr = json.toString();

        String responseString = this.mockMvc.perform(post("/login")
                .contentType("application/json;charset=UTF-8")
                .content(jsonStr))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getHeader("Authorization");

        logger.info(responseString);
        System.out.println(responseString);
    }

    @Test
    @Rollback
    void WrongUserNameLoginTest() throws Exception{
        JSONObject json = JSONUtil.createObj();
        json.put("username", "ABC");
        json.put("password", "qwerty888");

        String jsonStr = json.toString();

        String responseString = this.mockMvc.perform(post("/login")
                .contentType("application/json;charset=UTF-8")
                .content(jsonStr))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        logger.info(responseString);
        System.out.println(responseString);


    }

    //@Test
    //@Rollback
    //void SimpleLoginFailTest() throws Exception{
    //    JSONObject json = JSONUtil.createObj();
    //    json.put("username", "userA");
    //    json.put("password", "1234");
    //
    //    String jsonStr = json.toString();
    //
    //    String responseString = this.mockMvc.perform(post("/login")
    //            .contentType("application/json;charset=UTF-8")
    //            .content(jsonStr))
    //            .andExpect(status().is4xxClientError())
    //            .andExpect(content().contentType("application/json;charset=UTF-8"))
    //            .andReturn().getResponse().getContentAsString();
    //
    //    logger.info(responseString);
    //
    //}

    @Test
    @Rollback
    void SimpleRegisterSuccessTest() throws Exception{
        JSONObject json = JSONUtil.createObj();
        json.put("username", "userB");
        json.put("password", "qwerty888");
        json.put("mobile","12345678900");
        json.put("code","123456");
        json.put("wxCode","12345678900");

        String jsonStr = json.toString();

        String responseString = this.mockMvc.perform(post("/register")
                .contentType("application/json;charset=UTF-8")
                .content(jsonStr))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        logger.info(responseString);

        System.out.println(responseString);

    }

    @Test
    @Rollback
    void getUserInfo() throws Exception {
        String jsonString = "";

        System.out.println(jsonString);

        String responseString = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/user/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8")
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(responseString);
    }

    @Test
    @Rollback
    void getAllUsers() throws Exception {
        String jsonString = "";

        System.out.println(jsonString);

        String responseString = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                        .param("limit","5")
                        .contentType("application/json;charset=UTF-8")
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(responseString);
    }

    @Test
    @Rollback
    void updateUser() throws Exception {
        UserPo userPo = new UserPo();
        User user = new User(userPo);
        user.setId(1);
        user.setName("userA");
        user.setNickname("Adam");
        user.setPassword("qwerty999");
        user.setGender(0);
        user.setBirthday(LocalDate.of(1998, 3, 1));
        user.setMobile("13389463725");
        user.setRebate(25);
        user.setAvatar("resources/userA.png");
        user.setLastLoginTime(LocalDateTime.of(2019,12,7,15,38,5));
        user.setLastLoginIp("48.37.2.64");
        user.setUserLevel(3);
        user.setWxOpenId("ocgT_KJGds6546DF5fd64644sdFSD");
        user.setSessionKey("fadsSDFsd4fg54fa9sdf6ga");
        user.setGmtCreate(LocalDateTime.of(2019,10,6,10,25,36));

        String jsonString = JacksonUtil.toJson(user);

        System.out.println(jsonString);

        String responseString = this.mockMvc
                .perform(MockMvcRequestBuilders.put("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8")
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(responseString);
    }

    @Test
    @Rollback
    void deleteUser() throws Exception {
        String jsonString = "";

        System.out.println(jsonString);

        String responseString = this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/user/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8")
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(responseString);
    }
}
