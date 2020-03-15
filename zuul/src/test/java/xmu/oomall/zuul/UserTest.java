package xmu.oomall.zuul;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.zuul.domain.AddressPo;
import xmu.oomall.zuul.util.JacksonUtil;
import xmu.oomall.zuul.vo.UserRegisterVo;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ZuulApplication.class)
@AutoConfigureMockMvc
@Transactional
public class UserTest {
    @Autowired
    private MockMvc mockMvc;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ZuulApplication.class);

    @Test
    void SimpleLoginTest() throws Exception {
        JSONObject json = JSONUtil.createObj();
        json.put("username", "userA");
        json.put("password", "qwerty888");

        String jsonStr = json.toString();

        MockHttpServletResponse response = this.mockMvc.perform(post("/userInfoService/login")
                .contentType("application/json;charset=UTF-8")
                .content(jsonStr))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse();

        String jwt = response.getHeader("authorization");

        String content = response.getContentAsString();

        System.out.println(jwt);

        System.out.println(content);

    }

    @Test
    void SimpleLogoutTest() throws Exception {
        JSONObject json = JSONUtil.createObj();
        json.put("username", "userA");
        json.put("password", "qwerty888");

        String jsonStr = json.toString();

        MockHttpServletResponse response = this.mockMvc.perform(post("/userInfoService/login")
                .contentType("application/json;charset=UTF-8")
                .content(jsonStr))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse();

        String jwt = response.getHeader("Authorization");

        String content = response.getContentAsString();

        System.out.println(jwt);

        System.out.println(content);

        Thread.sleep(4000);

        response = this.mockMvc.perform(post("/userInfoService/logout")
                .contentType("application/json;charset=UTF-8")
                .header("Authorization", jwt))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse();

        jwt = response.getHeader("Authorization");

        content = response.getContentAsString();

        assert jwt != null;
        System.out.println("header中token为空：" + (jwt.isEmpty()));

        System.out.println(content);

    }

    @Test
    @Rollback
    void SimpleRegisterTest() throws Exception {
        UserRegisterVo vo = new UserRegisterVo();
        vo.setCode("123456");
        vo.setUsername("zhongtt");
        vo.setPassword("999999");
        vo.setTelephone("1366666666");
        JSONObject json = JSONUtil.parseObj(vo);

        String jsonStr = json.toString();

        MockHttpServletResponse response = this.mockMvc.perform(post("/userInfoService/register")
                .contentType("application/json;charset=UTF-8")
                .content(jsonStr))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse();

        String authorization = response.getHeader("authorization");

        System.out.println(authorization);

        System.out.println(response.getContentAsString());
    }

    @Test
    @Rollback
    void FailRegisterTest() throws Exception {
        JSONObject json = JSONUtil.createObj();
        json.put("username", "userA");
        json.put("password", "123456");
        json.put("telephone", "13389463725");
        json.put("code", "123456");

        String jsonStr = json.toString();

        MockHttpServletResponse response = this.mockMvc.perform(post("/userInfoService/register")
                .contentType("application/json;charset=UTF-8")
                .content(jsonStr))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse();

        String authorization = response.getHeader("Authorization");

        System.out.println(authorization);

        System.out.println(response.getContentAsString());
    }

    @Test
    @Rollback
    void UpdatePasswordTest() throws Exception {
        JSONObject json = JSONUtil.createObj();

        json.put("password", "666666");
        json.put("code", "123456");
        json.put("telephone", "13389463725");
        String jsonStr = json.toString();

        MockHttpServletResponse response = this.mockMvc.perform(put("/userInfoService/password")
                .contentType("application/json;charset=UTF-8")
                .content(jsonStr))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse();

        System.out.println(response.getHeader("authorization"));
        System.out.println(response.getContentAsString());
    }

    @Test
    @Rollback
    void UpdatePhoneTest() throws Exception {
        JSONObject json = JSONUtil.createObj();
        json.put("username", "userA");
        json.put("password", "qwerty888");

        String jsonStr = json.toString();

        MockHttpServletResponse response = this.mockMvc.perform(post("/userInfoService/login")
                .contentType("application/json;charset=UTF-8")
                .content(jsonStr))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse();

        String jwt = response.getHeader("Authorization");

        json.clear();
        json.put("password", "qwerty888");
        json.put("code", "123456");
        json.put("telephone", "13666666666");
        json.put("newTelephone","123");
        jsonStr = json.toString();

        response = this.mockMvc.perform(put("/userInfoService/phone")
                .contentType("application/json;charset=UTF-8")
                .header("Authorization", jwt)
                .content(jsonStr))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse();

        System.out.println(response.getHeader("Authorization"));
        System.out.println(response.getContentAsString());
    }

    @Test
    void getExpressNumberTest() throws Exception {
        String r = mockMvc.perform(get("/logisticsService/logistics").contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(r);
    }

//    @Test
//    void getAddressTest() throws Exception{
//        String r = mockMvc.perform(get("/addressService/addresses/1").contentType("application/json;charset=UTF-8"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse().getContentAsString();
//        System.out.println(r);
//    }

}
