package xmu.oomall.freight.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.freight.controller.vo.ExpressRetVO;
import xmu.oomall.freight.controller.vo.ExpressVO;
import xmu.oomall.freight.domain.*;
import xmu.oomall.freight.eureka.LogService;
import xmu.oomall.freight.service.FreightService;
import xmu.oomall.freight.util.LogUtil;
import xmu.oomall.freight.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zty
 * 唯一 controller 类
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class FreightController {

    private static final Logger logger = LoggerFactory.getLogger(RestController.class);

    @Autowired
    FreightService freightService;

    @Autowired
    private LogUtil logUtil;

    @GetMapping(value = "/freightPrice",produces = "application/json;charset=UTF-8")
    public Object getExpressFee(@RequestBody Order order) {

        logger.debug("submit参数：" + order);

        List<OrderItem> orderItemList = order.getOrderItemList();

        List<Goods> goodsList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();

        for (OrderItem orderItem : orderItemList) {
            Product product = orderItem.getProduct();
            Goods goods = product.getGoods();
            Integer number = orderItem.getNumber();

            numList.add(number);
            goodsList.add(goods);
        }

        Object retObj;

        /*
          商品和数量列表不匹配
         */
        if (goodsList.size() != numList.size()) {
            return ResponseUtil.badArgumentValue();
        }

        /*
          有数量错误
         */
        for (Integer num : numList) {
            if (num <= 0) {
                return ResponseUtil.badArgumentValue();
            }
        }

        AddressPo destination = order.getAddressObj();

        BigDecimal fee;
        try {
            fee = freightService.cacuFee(goodsList, numList, destination);
        } catch (Exception e) {
            System.out.println("运费计算失败！");
            return ResponseUtil.fail();
        }

        return ResponseUtil.ok(fee);

    }

    /**
     * 获得所有的计重运费模板
     *
     * @param page 第几页
     * @param limit 需要几条
     * @return defaultFreightList
     */
    @GetMapping("/defaultFreights")
    public Object findAllDefaultFreight(@RequestParam Integer page, @RequestParam Integer limit, HttpServletRequest request) {

        if (page == null || limit == null) {
            return ResponseUtil.badArgument();
        } else if (page <= 0 || limit <= 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            try {
                logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                List<DefaultFreightPo> defaultFreightPoList = freightService.findAllDefaultFreight((page - 1) * limit, limit);
                if (defaultFreightPoList == null) {
                    return ResponseUtil.invalidDefaultFreight();
                } else {
                    return ResponseUtil.ok(defaultFreightPoList);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 获得所有的计件运费模板
     *
     * @param page 第几页
     * @param limit 需要几条
     * @return defaultPieceFreightList
     */
    @GetMapping("/defaultPieceFreights")
    public Object findAllDefaultPieceFreight(@RequestParam Integer page, @RequestParam Integer limit,HttpServletRequest request) {

        if (page == null || limit == null) {
            return ResponseUtil.badArgument();
        } else if (page <= 0 || limit <= 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            try {
                logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                List<DefaultPieceFreightPo> defaultPieceFreightPoList = freightService.findAllDefaultPieceFreight((page - 1) * limit, limit);
                if (defaultPieceFreightPoList == null) {
                    return ResponseUtil.invalidDefaultPieceFreight();
                } else {
                    return ResponseUtil.ok(defaultPieceFreightPoList);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 获得所有的特殊运费模板
     *
     * @param page 第几页
     * @param limit 需要几条
     * @return specialFreightList
     */
    @GetMapping("/specialFreights")
    public Object findAllSpecialFreight(@RequestParam Integer page, @RequestParam Integer limit,HttpServletRequest request) {

        if (page == null || limit == null) {
            return ResponseUtil.badArgument();
        } else if (page <= 0 || limit <= 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            try {
                logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                List<SpecialFreightPo> specialFreightPoList = freightService.findAllSpecialFreight((page - 1) * limit, limit);
                if (specialFreightPoList == null) {
                    return ResponseUtil.invalidSpecialFreight();
                } else {
                    return ResponseUtil.ok(specialFreightPoList);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 获得选定的计重运费模板
     *
     * @param id 计重运费模板id
     * @return defaultFreight
     */
    @GetMapping("/defaultFreights/{id}")
    public Object findDefaultFreightById(@PathVariable Integer id,HttpServletRequest request) {

        if (id == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }


        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            logger.debug("要查询的计重运费模板id：" + id);

            try {
                logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                DefaultFreightPo defaultFreightPo = freightService.findDefaultFreightById(id);
                if (defaultFreightPo == null) {
                    return ResponseUtil.invalidDefaultFreight();
                } else {
                    return ResponseUtil.ok(defaultFreightPo);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 获得选定的计件运费模板
     *
     * @param id 计件运费模板id
     * @return defaultPieceFreight
     */
    @GetMapping("/defaultPieceFreights/{id}")
    public Object findDefaultPieceFreightById(@PathVariable Integer id,HttpServletRequest request) {

        if (id == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));


            logger.debug("要查询的计件运费模板id：" + id);

            try {
                logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                DefaultPieceFreightPo defaultPieceFreightPo = freightService.findDefaultPieceFreightById(id);
                if (defaultPieceFreightPo == null) {
                    return ResponseUtil.invalidDefaultPieceFreight();
                } else {
                    return ResponseUtil.ok(defaultPieceFreightPo);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 获得选定的特殊运费模板
     *
     * @param id 特殊运费模板id
     * @return specialFreight
     */
    @GetMapping("/specialFreights/{id}")
    public Object findSpecialFreightById(@PathVariable Integer id,HttpServletRequest request) {

        if (id == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));


            logger.debug("要查询的特殊运费模板id：" + id);

            try {
                logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                SpecialFreightPo specialFreightPo = freightService.findSpecialFreightById(id);
                if (specialFreightPo == null) {
                    return ResponseUtil.invalidSpecialFreight();
                } else {
                    return ResponseUtil.ok(specialFreightPo);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),0,"查询",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 新增计重运费模板
     *
     * @param defaultFreightPo 运费模板对象
     * @return success
     */
    @PostMapping("/defaultFreights")
    public Object addDefaultFreight(@RequestBody DefaultFreightPo defaultFreightPo,HttpServletRequest request) {

        if (defaultFreightPo == null) {
            return ResponseUtil.badArgument();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            logger.debug("defaultFreight对象：" + defaultFreightPo);

            DefaultFreight defaultFreight = new DefaultFreight(defaultFreightPo);
            LocalDateTime localDateTime = LocalDateTime.now();
            defaultFreight.setGmtCreate(localDateTime);
            defaultFreight.setGmtModified(localDateTime);

            try {
                Integer success = freightService.addDefaultFreight(defaultFreight);
                if (success == 0) {
                    logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.updateDefaultFreightFailed();
                } else {
                    logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.ok(defaultFreightPo);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 新增计件运费模板
     *
     * @param defaultPieceFreightPo 运费模板对象
     * @return success
     */
    @PostMapping("/defaultPieceFreights")
    public Object addDefaultPieceFreight(@RequestBody DefaultPieceFreightPo defaultPieceFreightPo,HttpServletRequest request) {

        if (defaultPieceFreightPo == null) {
            return ResponseUtil.badArgument();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            logger.debug("defaultPieceFreight对象：" + defaultPieceFreightPo);

            DefaultPieceFreight defaultPieceFreight = new DefaultPieceFreight(defaultPieceFreightPo);
            LocalDateTime localDateTime = LocalDateTime.now();
            defaultPieceFreight.setGmtCreate(localDateTime);
            defaultPieceFreight.setGmtModified(localDateTime);


            try {Integer success = freightService.addDefaultPieceFreight(defaultPieceFreight);
                if (success == 0) {
                    logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.updateDefaultPieceFreightFailed();
                } else {
                    logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.ok(defaultPieceFreightPo);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 新增特殊运费模板
     *
     * @param specialFreightPo 运费模板对象
     * @return success
     */
    @PostMapping("/specialFreights")
    public Object addSpecialFreight(@RequestBody SpecialFreightPo specialFreightPo,HttpServletRequest request) {

        if (specialFreightPo == null) {
            return ResponseUtil.badArgument();
        }
        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            logger.debug("specialFreight对象：" + specialFreightPo);

            SpecialFreight specialFreight = new SpecialFreight(specialFreightPo);
            LocalDateTime localDateTime = LocalDateTime.now();
            specialFreight.setGmtCreate(localDateTime);
            specialFreight.setGmtModified(localDateTime);


            try {Integer success = freightService.addSpecialFreight(specialFreight);
                if (success == 0) {
                    logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.insertSpecialFreightFailed();
                } else {
                    logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.ok(specialFreightPo);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),1,"插入",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }
    }

    /**
     * 修改选定的计重运费模板
     *
     * @param id             选定的运费模板id
     * @param defaultFreightPo 运费模板对象
     * @return success
     */
    @PutMapping("/defaultFreights/{id}")
    public Object updateDefaultFreight(@PathVariable Integer id, @RequestBody DefaultFreightPo defaultFreightPo,HttpServletRequest request) {

        if (id == null || defaultFreightPo == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            logger.debug("选定的defaultFreight对象id：" + id);

            DefaultFreight defaultFreight = new DefaultFreight(defaultFreightPo);
            LocalDateTime localDateTime = LocalDateTime.now();
            defaultFreight.setGmtModified(localDateTime);

            try {
                Integer success = freightService.updateDefaultFreight(defaultFreight);
                if (success == 0) {
                    logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.updateDefaultFreightFailed();
                } else {
                    logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.ok(defaultFreightPo);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){
            return ResponseUtil.serious();
        }

    }

    /**
     * 修改选定的计件运费模板
     *
     * @param id                  选定的运费模板id
     * @param defaultPieceFreightPo 运费模板对象
     * @return success
     */
    @PutMapping("/defaultPieceFreights/{id}")
    public Object updateDefaultPieceFreight(@PathVariable Integer id, @RequestBody DefaultPieceFreightPo defaultPieceFreightPo,HttpServletRequest request) {

        if (id == null || defaultPieceFreightPo == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            logger.debug("选定的defaultPieceFreight对象id：" + id);

            DefaultPieceFreight defaultPieceFreight = new DefaultPieceFreight(defaultPieceFreightPo);
            LocalDateTime localDateTime = LocalDateTime.now();
            defaultPieceFreight.setGmtModified(localDateTime);

            try {
                Integer success = freightService.updateDefaultPieceFreight(defaultPieceFreight);
                if (success == 0) {
                    logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.updateDefaultPieceFreightFailed();
                } else {
                    logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.ok(defaultPieceFreight);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());

                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){

            return ResponseUtil.serious();
        }


    }

    /**
     * 修改选定的特殊运费模板
     *
     * @param id             选定的运费模板id
     * @param specialFreightPo 运费模板对象
     * @return success
     */
    @PutMapping("/specialFreights/{id}")
    public Object updateSpecialFreight(@PathVariable Integer id, @RequestBody SpecialFreightPo specialFreightPo,HttpServletRequest request) {

        if (id == null || specialFreightPo == null) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            logger.debug("选定的specialFreight对象id：" + specialFreightPo);

            SpecialFreight specialFreight = new SpecialFreight(specialFreightPo);
            LocalDateTime localDateTime = LocalDateTime.now();
            specialFreight.setGmtModified(localDateTime);

            try {
                Integer success = freightService.updateSpecialFreight(specialFreight);
                if (success == 0) {
                    logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.updateSpecialFreightFailed();
                } else {
                    logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.ok(specialFreight);
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),2,"修改",LocalDateTime.now(),LocalDateTime.now());

                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){

            return ResponseUtil.serious();
        }


    }

    /**
     * 删除选定的计重运费模板
     *
     * @param id 选定的计重运费模板id
     * @return success
     */
    @DeleteMapping("/defaultFreights/{id}")
    public Object deleteDefaultFreight(@PathVariable Integer id,HttpServletRequest request) {

        if (id == null ) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));


            logger.debug("要删除的计重运费模板id：" + id);

            try {
                Integer success = freightService.deleteDefaultFreight(id);
                if (success == 0) {
                    logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),4,"删除",LocalDateTime.now(),LocalDateTime.now());
                    return ResponseUtil.deleteDefaultFreightFailed();
                } else {
                    logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),4,"删除",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.ok();
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),4,"删除",LocalDateTime.now(),LocalDateTime.now());
                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){

            return ResponseUtil.serious();
        }

    }

    /**
     * 删除选定的计件运费模板
     *
     * @param id 选定的计件运费模板id
     * @return success
     */
    @DeleteMapping("/defaultPieceFreights/{id}")
    public Object deleteDefaultPieceFreight(@PathVariable Integer id,HttpServletRequest request) {

        if (id == null ) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            logger.debug("要删除的计件运费模板id：" + id);

            try {
                Integer success = freightService.deleteDefaultPieceFreight(id);
                if (success == 0) {
                    logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),4,"删除",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.deleteDefaultPieceFreightFailed();
                } else {
                    logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),4,"删除",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.ok();
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),4,"删除",LocalDateTime.now(),LocalDateTime.now());

                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){

            return ResponseUtil.serious();
        }


    }

    /**
     * 删除选定的特殊运费模板
     *
     * @param id 选定的特殊运费模板id
     * @return success
     */
    @DeleteMapping("/specialFreights/{id}")
    public Object deleteSpecialFreight(@PathVariable Integer id,HttpServletRequest request) {

        if (id == null ) {
            return ResponseUtil.badArgument();
        } else if (id < 0) {
            return ResponseUtil.badArgumentValue();
        }

        try {
            JSONObject jsonObject = JSONUtil.parseObj(request.getHeader("userInfo"));
            Integer adminId = Convert.convert(Integer.class, jsonObject.get("userId"));

            logger.debug("要删除的特殊运费模板id：" + id);

            try {
                Integer success = freightService.deleteSpecialFreight(id);
                if (success == 0) {
                    logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),4,"删除",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.deleteSpecialFreightFailed();
                } else {
                    logUtil.addLogInfoSuccess(request,adminId, InetAddress.getLocalHost().toString(),4,"删除",LocalDateTime.now(),LocalDateTime.now());

                    return ResponseUtil.ok();
                }
            } catch (Exception e) {
                logUtil.addLogInfoFail(request,adminId, InetAddress.getLocalHost().toString(),4,"删除",LocalDateTime.now(),LocalDateTime.now());

                return ResponseUtil.operaterFailed();
            }
        }catch (Exception e){

            return ResponseUtil.serious();
        }


    }
}
