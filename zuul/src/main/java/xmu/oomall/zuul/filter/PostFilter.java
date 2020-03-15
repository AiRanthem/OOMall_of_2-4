package xmu.oomall.zuul.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StreamUtils;
import xmu.oomall.zuul.util.Config;
import xmu.oomall.zuul.util.JwtUtil;
import xmu.oomall.zuul.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class PostFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(PostFilter.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Config config;

    @Override
    public String filterType(){
        return "post";
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        Object code = ctx.get("error.status_code");

        /**
         * 网关拦截器错误
         */
        if (code != null) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(Integer.parseInt(code.toString()));
            ctx.setResponseBody(ctx.get("error.message").toString());
            return null;
        }

        /**
         * 获得响应体。所有responsebody都在字符串body中。
         */
        String body = null;
        try {
            InputStream stream = ctx.getResponseDataStream();
            body = StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
            ctx.setResponseBody(body);
        } catch (IOException e) {
            return null;
        }

        JSONObject json = JSONUtil.parseObj(body);
        HttpServletResponse response = ctx.getResponse();

        try {
            Object data1 = json.get("data");
            String data = data1.toString();
            JSONObject dataObj = JSONUtil.parseObj(data);
            /**
             * 如果返回了token，说明产生了身份变化。要
             *
             */
            String token = dataObj.get("token").toString();

            String logout = "logout";
            if (logout.equals(token)) {
                /**
                 * 登出
                 * 1. 设置响应头的authorization为空
                 * 2. 删除用户对应redis
                 * 3. 规范化返回body
                 */
                response.setHeader("authorization","");

                String userId = ctx.get("userId").toString();

                String roleId = ctx.get("role").toString();

                String key = "userId" + userId + "role" + roleId.toString();
                redisTemplate.delete(key);
                logger.info("删除用户" + key + "从redis。");

                ctx.setResponseBody(ResponseUtil.ok().toString());

                ctx.setSendZuulResponse(false);
            }
            else {
                /**
                 * 登入
                 * 1. 设置响应头的authorization为新的token
                 * 2. 添加用户到redis
                 */
                response.setHeader("authorization", token);

                String claim = JwtUtil.getClaim(token);

                JSONObject claimJson = JSONUtil.parseObj(claim);

                String userId = claimJson.get("userId").toString();

                String roleId = claimJson.get("roleId").toString();

                String key = "userId" + userId + "role" + roleId.toString();

                redisTemplate.opsForValue().set(key, token, config.getRedisExpireTime(), TimeUnit.MINUTES);
                logger.info("存入用户" + key + "到redis。");
            }
            ctx.setSendZuulResponse(false);
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
