package xmu.oomall.address.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import xmu.oomall.address.AddressApplication;
import xmu.oomall.address.domain.AddressPo;
import xmu.oomall.address.domain.Region;
import xmu.oomall.address.service.AddressService;
import xmu.oomall.address.util.JacksonUtil;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AddressApplication.class)
@AutoConfigureMockMvc
@Transactional
class AddressPoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

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
                .perform(MockMvcRequestBuilders.post("/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("userInfo","{\"roleId\":\"4\",\"userName\":\"userA\",\"userId\":\"7\"}")
                        .contentType("application/json;charset=UTF-8")
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(responseString);
    }

    @Test
    @Rollback
    void updateAddress() throws Exception{
        AddressPo addressPo = new AddressPo();
        addressPo.setId(1);
        addressPo.setProvinceId(12);
        addressPo.setCityId(147);
        addressPo.setCountyId(95);
        addressPo.setAddressDetail("厦大学生公寓");
        addressPo.setBeDefault(true);
        addressPo.setMobile("23333333");
        addressPo.setConsignee("ooad");
        addressPo.setPostalCode("150330");
        addressPo.setUserId("111");

        String jsonString = JacksonUtil.toJson(addressPo);

        String responseString = this.mockMvc
                .perform(MockMvcRequestBuilders.put("/addresses/111")
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
    void findAllAddress() throws Exception {

        AddressPo addressPo = new AddressPo();

        String jsonString = JacksonUtil.toJson(addressPo);

        String responseString = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/addresses")
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
    void findAddressById() throws Exception {

        AddressPo addressPo = new AddressPo();

        String jsonString = JacksonUtil.toJson(addressPo);

        String responseString = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/addresses/1")
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
    void clearAddress() throws Exception {

        List<Integer> addressList = new LinkedList<>();

        String jsonString = JacksonUtil.toJson(addressList);

        String responseString = this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/addresses/1")
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
    void findRegionById() throws Exception {
        Region region = new Region();

        String jsonString = JacksonUtil.toJson(region);

        String responseString = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/region/10")
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
    void findRegionByPid() throws Exception {
        Region region = new Region();

        String jsonString = JacksonUtil.toJson(region);

        String responseString = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/regions/10")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType("application/json;charset=UTF-8")
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(responseString);
    }

}