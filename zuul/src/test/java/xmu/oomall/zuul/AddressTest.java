package xmu.oomall.zuul;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.BeforeEach;
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
import xmu.oomall.zuul.domain.*;
import xmu.oomall.zuul.util.JacksonUtil;
import xmu.oomall.zuul.vo.LoginVo;
import xmu.oomall.zuul.vo.UserRegisterVo;

import javax.ws.rs.core.MediaType;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ZuulApplication.class)
@Transactional
@AutoConfigureMockMvc
public class AddressTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Rollback
    void addAddress() throws Exception {
        AddressPo addressPo = new AddressPo();
        addressPo.setProvinceId(13);
        addressPo.setCityId(147);
        addressPo.setCountyId(95);
        addressPo.setAddressDetail("海韵学生公寓");
        addressPo.setBeDefault(true);
        addressPo.setMobile("23333333");
        addressPo.setConsignee("ooad");
        addressPo.setPostalCode("150330");
        addressPo.setUserId("111");

        String jsonString = JacksonUtil.toJson(addressPo);

        System.out.println(jsonString);

        String responseString = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/addressService/addresses")
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
    void findAddressById() throws Exception {

        AddressPo addressPo = new AddressPo();

        String jsonString = JacksonUtil.toJson(addressPo);

        String responseString = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/addressService/addresses/1")
                        .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8")
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(responseString);
    }

    @Test
    void getGoodsTest() throws Exception{
        String responseString = this.mockMvc.perform(get("/goodsInfoService/goods/374")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        System.out.println(responseString);
    }

    private String jsonStr = "";
    private String token = "";

    @BeforeEach
    void adminLogin() throws Exception {
        LoginVo vo = new LoginVo();
        vo.setPassword("qwerty888");
        vo.setUsername("admin2");
        jsonStr = JSONUtil.parseObj(vo).toString();
        MockHttpServletResponse response = this.mockMvc.perform(post("/userInfoService/admin/login")
                .contentType("application/json;charset=UTF-8")
                .content(this.jsonStr))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        System.out.println("管理员登陆成功："+response.getContentAsString());

        token = response.getHeader("authorization");

        System.out.println(token);
    }

    @Test
    void t1() throws Exception {
        String url = "/goodsInfoService/brands/0";

        String responseString = this.mockMvc.perform(get(url)
                .contentType("application/json;charset=UTF-8")
                .header("authorization",token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(responseString);
    }
}
