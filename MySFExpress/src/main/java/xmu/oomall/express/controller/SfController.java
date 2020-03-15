package xmu.oomall.express.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import xmu.oomall.express.controller.vo.GetExNumVO;
import xmu.oomall.express.domain.ExpressOrder;
import xmu.oomall.express.service.SfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.oomall.express.util.ResponseUtil;

/**
 * 模拟顺丰系统
 * @author 仲天云
 * @date 2019/12/4 create
 * @version 0.1
 * @apiNote GET:/ExpressNumber
 *
 * 目前标准组给出的功能有：
 * 1. 发过来订单，给他生成一个单号 12/4 v1.0 by zty
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class SfController {

    private static final Logger logger = LoggerFactory.getLogger(SfController.class);

    @Autowired SfService sfService;

    @GetMapping("/logistics")
    public Object getExpressNumber(){

        ExpressOrder order = new ExpressOrder();

        String exNum = sfService.express();

        return ResponseUtil.ok(exNum);
    }

    @GetMapping("/logistics/todo")
    public Object getExpressStatus(@RequestParam String no) {
        JSONObject ret = JSONUtil.createObj();

        ExpressOrder order = new ExpressOrder();

        ret.put("status", "未签收");

        return ret;
    }

}
