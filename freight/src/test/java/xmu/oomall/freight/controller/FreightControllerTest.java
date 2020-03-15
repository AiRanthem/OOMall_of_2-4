package xmu.oomall.freight.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import xmu.oomall.freight.FreightApplication;
import xmu.oomall.freight.controller.vo.ExpressVO;
import xmu.oomall.freight.domain.*;
import xmu.oomall.freight.util.JacksonUtil;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FreightApplication.class)
@AutoConfigureMockMvc
public class FreightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ExpressVO vo = new ExpressVO();
    private AddressPo address = new AddressPo();

    private ArrayList<Integer> numList = new ArrayList<>();
    private ArrayList<Goods> goodsList = new ArrayList<>();
    private Object data;

    @BeforeEach
    void before() {
        address.setCityId(147);

        numList.clear();
        goodsList.clear();

        Goods goods;

        /**
         *  20
         */
        goods=new Goods();
        goods.setWeight(BigDecimal.valueOf(0.3));
        goods.setBeSpecial(true);
        goods.setSpecialFreightId(1);
        goodsList.add(goods);
        numList.add(1);

        /**
         * 30*2=60
         */
        goods=new Goods();
        goods.setWeight(BigDecimal.valueOf(8));
        goods.setBeSpecial(true);
        goods.setSpecialFreightId(1);
        goodsList.add(goods);
        numList.add(2);

        /**
         * 45*3=135
         * 1 1
         * 2 101
         */
        goods=new Goods();
        goods.setWeight(BigDecimal.valueOf(15));
        goods.setBeSpecial(true);
        goods.setSpecialFreightId(1);
        goodsList.add(goods);
        numList.add(3);

        /**
         * 55*4=220
         * 1 2
         * 2 101
         */
        goods=new Goods();
        goods.setWeight(BigDecimal.valueOf(55));
        goods.setBeSpecial(true);
        goods.setSpecialFreightId(1);
        goodsList.add(goods);
        numList.add(4);

        /**
         * 77
         * 1 1
         * 2 1
         */
        goods=new Goods();
        goods.setWeight(BigDecimal.valueOf(305));
        goods.setBeSpecial(false);
        goods.setSpecialFreightId(8);
        goodsList.add(goods);
        numList.add(1);

        /**
         * total = 348.5
         * 1 : 6
         * 2 : 205
         */
    }

    @Test
    @Rollback
    void defaultWinTest() throws Exception {

        Address a = new Address();
        a.setCityId(147);

        User user = new User();
        user.setId(1);

        Order order = new Order(user, a);

        ArrayList<OrderItem> orderItems = new ArrayList<>();

        for (int i = 0; i < goodsList.size(); ++i) {
            Goods goods = goodsList.get(i);
            OrderItem orderItem = new OrderItem();
            Product product = new Product();

            product.setGoods(goods);

            orderItem.setProduct(product);
            orderItem.setNumber(numList.get(i));

            orderItems.add(orderItem);
        }

        order.setOrderItemList(orderItems);

        String jsonString = JacksonUtil.toJson(order);

//        String jsonString = "{\n" +
//                "        \"id\": null,\n" +
//                "        \"userId\": 10086,\n" +
//                "        \"orderSn\": \"P201912192142499603\",\n" +
//                "        \"statusCode\": 0,\n" +
//                "        \"consignee\": null,\n" +
//                "        \"mobile\": null,\n" +
//                "        \"message\": null,\n" +
//                "        \"goodsPrice\": 1700,\n" +
//                "        \"couponPrice\": null,\n" +
//                "        \"rebatePrice\": null,\n" +
//                "        \"freightPrice\": null,\n" +
//                "        \"integralPrice\": null,\n" +
//                "        \"shipSn\": null,\n" +
//                "        \"shipChannel\": null,\n" +
//                "        \"shipTime\": null,\n" +
//                "        \"confirmTime\": null,\n" +
//                "        \"endTime\": null,\n" +
//                "        \"payTime\": null,\n" +
//                "        \"parentId\": null,\n" +
//                "        \"address\": \"  \",\n" +
//                "        \"gmtCreate\": null,\n" +
//                "        \"gmtModified\": null,\n" +
//                "        \"beDeleted\": null,\n" +
//                "        \"addressObj\": {\n" +
//                "            \"id\": null,\n" +
//                "            \"userId\": null,\n" +
//                "            \"cityId\": 147,\n" +
//                "            \"provinceId\": null,\n" +
//                "            \"countyId\": null,\n" +
//                "            \"addressDetail\": null,\n" +
//                "            \"mobile\": null,\n" +
//                "            \"postalCode\": null,\n" +
//                "            \"consignee\": null,\n" +
//                "            \"beDefault\": false,\n" +
//                "            \"gmtCreate\": null,\n" +
//                "            \"gmtModified\": null,\n" +
//                "            \"beDeleted\": null,\n" +
//                "            \"province\": null,\n" +
//                "            \"city\": null,\n" +
//                "            \"county\": null\n" +
//                "        },\n" +
//                "        \"user\": {\n" +
//                "            \"id\": 10086,\n" +
//                "            \"name\": \"24475120868\",\n" +
//                "            \"nickname\": null,\n" +
//                "            \"password\": \"123456\",\n" +
//                "            \"gender\": 0,\n" +
//                "            \"birthday\": null,\n" +
//                "            \"mobile\": \"13959288888\",\n" +
//                "            \"rebate\": 0,\n" +
//                "            \"avatar\": null,\n" +
//                "            \"lastLoginTime\": \"2019-12-18T00:14:13\",\n" +
//                "            \"lastLoginIp\": \"183.246.224.16\",\n" +
//                "            \"userLevel\": 0,\n" +
//                "            \"wxOpenId\": null,\n" +
//                "            \"sessionKey\": null,\n" +
//                "            \"roleId\": 4,\n" +
//                "            \"gmtCreate\": \"2019-12-18T00:14:13\",\n" +
//                "            \"gmtModified\": null,\n" +
//                "            \"beDeleted\": null\n" +
//                "        },\n" +
//                "        \"orderItemList\": [\n" +
//                "            {\n" +
//                "                \"id\": null,\n" +
//                "                \"orderId\": null,\n" +
//                "                \"itemType\": 0,\n" +
//                "                \"statusCode\": null,\n" +
//                "                \"number\": 2,\n" +
//                "                \"price\": 850,\n" +
//                "                \"dealPrice\": 850,\n" +
//                "                \"productId\": 1024,\n" +
//                "                \"goodsId\": null,\n" +
//                "                \"nameWithSpecifications\": null,\n" +
//                "                \"picUrl\": null,\n" +
//                "                \"gmtCreate\": null,\n" +
//                "                \"gmtModified\": null,\n" +
//                "                \"beDeleted\": null,\n" +
//                "                \"product\": {\n" +
//                "                    \"id\": 1024,\n" +
//                "                    \"goodsId\": 274,\n" +
//                "                    \"picUrl\": \"http://47.52.88.176/file/images/201612/file_5861cd259e57a.jpg\",\n" +
//                "                    \"specifications\": \"default\",\n" +
//                "                    \"price\": 850,\n" +
//                "                    \"safetyStock\": 44,\n" +
//                "                    \"gmtCreate\": \"2019-12-18T00:14:12\",\n" +
//                "                    \"gmtModified\": \"2019-12-19T20:02:54\",\n" +
//                "                    \"beDeleted\": false,\n" +
//                "                    \"goods\": {\n" +
//                "                        \"id\": 274,\n" +
//                "                        \"name\": \"金和汇景•宝瓷林高温颜色釉六色茶具\",\n" +
//                "                        \"goodsSn\": \"bcl-b0001\",\n" +
//                "                        \"shortName\": \"+\",\n" +
//                "                        \"description\": null,\n" +
//                "                        \"brief\": null,\n" +
//                "                        \"picUrl\": \"http://47.52.88.176/file/images/201612/file_5861cd259e57a.jpg\",\n" +
//                "                        \"detail\": null,\n" +
//                "                        \"statusCode\": 0,\n" +
//                "                        \"shareUrl\": null,\n" +
//                "                        \"gallery\": null,\n" +
//                "                        \"goodsCategoryId\": 128,\n" +
//                "                        \"brandId\": 103,\n" +
//                "                        \"weight\": 4,\n" +
//                "                        \"volume\": null,\n" +
//                "                        \"specialFreightId\": null,\n" +
//                "                        \"beSpecial\": null,\n" +
//                "                        \"price\": 850,\n" +
//                "                        \"beDeleted\": false,\n" +
//                "                        \"gmtCreate\": \"2019-12-18T00:14:12\"\n" +
//                "                    }\n" +
//                "                }\n" +
//                "            }\n" +
//                "        ],\n" +
//                "        \"couponId\": null,\n" +
//                "        \"paymentList\": null\n" +
//                "    }";

        String responseString = this.mockMvc.perform(get("/freightPrice")
                .contentType("application/json;charset=UTF-8")
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();


        System.out.println(responseString);
    }

    @Test
    @Rollback
    void specialWinTest() throws Exception{
//        vo.setAddress(address);
//
//        Goods goods=new Goods();
//        goods.setWeight(BigDecimal.valueOf(55));
//        goods.setBeSpecial(true);
//        goods.setSpecialFreightId(3);
//        goodsList.add(goods);
//        numList.add(2);
//
//        vo.setGoodsList(goodsList);
//        vo.setNumList(numList);
//
//        /**
//         * should be 1300
//         */
//
//        String jsonString = JacksonUtil.toJson(vo);
//
//        String responseString = this.mockMvc.perform(get("/expressFee").contentType("application/json;charset=UTF-8").content(jsonString))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse().getContentAsString();
//
//
//        JSONObject responseObj = JSONUtil.parseObj(responseString);
//
//        String data = responseObj.get("data").toString();
    }

    @Test
    void findAllDefaultFreight() {
    }

    @Test
    void findAllDefaultPieceFreight() {
    }

    @Test
    void findAllSpecialFreight() {
    }

    @Test
    void findDefaultFreightById() {
    }

    @Test
    void findDefaultPieceFreightById() {
    }

    @Test
    void findSpecialFreightById() {
    }

    @Test
    void addDefaultFreight() {
    }

    @Test
    void addDefaultPieceFreight() {
    }

    @Test
    void addSpecialFreight() {
    }

    @Test
    void updateDefaultFreight() {
    }

    @Test
    void updateDefaultPieceFreight() {
    }

    @Test
    void updateSpecialFreight() {
    }

    @Test
    void deleteDefaultFreight() {
    }

    @Test
    void deleteDefaultPieceFreight() {
    }

    @Test
    void deleteSpecialFreight() {
    }
}