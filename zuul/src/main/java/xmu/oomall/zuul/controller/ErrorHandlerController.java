package xmu.oomall.zuul.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.netflix.zuul.context.RequestContext;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ErrorHandlerController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error",produces = "application/json;charset=UTF-8")
    public Object err() {
        RequestContext ctx = RequestContext.getCurrentContext();

        JSONObject obj = JSONUtil.createObj();

        try {

            String msg = ctx.get("error.message").toString();

            int code = Integer.parseInt(ctx.get("error.status_code").toString());

            ctx.setResponseStatusCode(code);


            obj.put("errno", code);
            obj.put("errmsg", msg);
        } catch (Exception e) {
            obj.put("errno", 500);
            obj.put("errmsg","can not router to target");
        }
        return obj.toString();
    }
}
