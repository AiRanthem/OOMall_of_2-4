//package xmu.oomall.zuul;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(classes = ZuulApplication.class)
//@AutoConfigureMockMvc
//class ZuulApplicationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ZuulApplication.class);
//
//    @BeforeEach
//    void before() {
//
//    }
//
//    @Test void ConnectTest() throws Exception{
//
//        String responseString = this.mockMvc.perform(get("/hello"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/plain;charset=UTF-8"))
//                .andReturn().getResponse().getHeader("Authorization");
//
//        logger.info(responseString);
//    }
//
//    @Test void JWTRefreshTest() throws Exception{
//
//        String responseString = this.mockMvc.perform(get("/hello"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/plain;charset=UTF-8"))
//                .andReturn().getResponse().getHeader("Authorization");
//
//        logger.info(responseString);
//
//        Thread.sleep(3000);
//
//        responseString = this.mockMvc.perform(get("/hello").header("Authorization", responseString))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/plain;charset=UTF-8"))
//                .andReturn().getResponse().getHeader("Authorization");
//
//        logger.info(responseString);
//
//        Thread.sleep(3000);
//
//        responseString = this.mockMvc.perform(get("/hello").header("Authorization", responseString))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/plain;charset=UTF-8"))
//                .andReturn().getResponse().getHeader("Authorization");
//
//        logger.info(responseString);
//    }
//
//    @Test void UserInfoTest() throws Exception {
//        String responseString = this.mockMvc.perform(get("/hello")
//                .contentType("application/x-www-form-urlencoded"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/plain;charset=UTF-8"))
//                .andReturn().getResponse().getHeader("userInfo");
//
//        logger.info(responseString);
//    }
//
//    @Test void JWTExpiredTest() throws Exception {
//        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiMTIzIiwiaXNzIjoiaXNzdWVyIiwiZXhwIjoxNTc2MDY0Mjg2LCJpYXQiOjE1NzYwNjQyODF9.U3Usn1U1hgNWP2TOZ6tj_S2_QI1ZANiCL3zR74z2DRM";
//
//        MockHttpServletResponse response = this.mockMvc.perform(get("/hello").header("Authorization", jwt))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/plain;charset=UTF-8"))
//                .andReturn().getResponse();
//        String token = response.getHeader("Authorization");
//
//        String info = response.getHeader("userInfo");
//
//        logger.info(jwt);
//        logger.info(token);
//        logger.info(info);
//    }
//
//    @Test void OKTest() throws Exception{
//        MockHttpServletResponse response = this.mockMvc.perform(get("/hello/ok"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse();
//
//        String contentString = response.getContentAsString();
//
//        logger.info(contentString);
//    }
//
//    @Test void OKDataTest() throws Exception {
//        MockHttpServletResponse response = this.mockMvc.perform(get("/hello/okData"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse();
//
//        String contentString = response.getContentAsString();
//
//        logger.info(contentString);
//    }
//
//    @Test void ERRTest() throws Exception {
//        MockHttpServletResponse response = this.mockMvc.perform(get("/hello/err"))
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse();
//
//        String contentString = response.getContentAsString();
//
//        logger.info(contentString);
//    }
//
//    @Test void BadJWTTest() throws Exception {
//        String jwt = "eyJhbGciOiJIUzYwNjQyODF9.U3Usn1U1hgNWP2TOZ6tj_S2_QI1ZANiCL3zR74z2DRM";
//
//        MockHttpServletResponse response = this.mockMvc.perform(get("/hello").header("Authorization", jwt))
//                .andExpect(status().is4xxClientError())
//                .andReturn().getResponse();
//        String token = response.getHeader("Authorization");
//
//        String info = response.getHeader("userInfo");
//
//        logger.info(jwt);
//        logger.info(token);
//        logger.info(info);
//    }
//}
