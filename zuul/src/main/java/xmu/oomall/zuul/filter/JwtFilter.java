package xmu.oomall.zuul.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import xmu.oomall.zuul.util.Config;
import xmu.oomall.zuul.util.JwtUtil;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zty
 * 0层过滤器，用于JWT数据的处理和维系
 */
public class JwtFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Config config;

    @Override
    public String filterType(){
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * JWT状态码
     */
    private static final int EXPIREDTOKEN = 2;
    private static final int BADTOKEN = 1;
    private static final int VALIDTOKEN = 0;
    private static final int UNKNOWNBAD = -1;

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();

        String jwt = ctx.getRequest().getHeader("authorization");

        Integer role = 0;

        /**
         * 没有Token，颁发游客
         */
        if (jwt == null) {
            logger.info("Empty");
            jwt = JwtUtil.generateToken("null",role,-1);
            ctx.addZuulResponseHeader("authorization", jwt);
        }

        /**
         * 否则验证token
         */
        else {
            Integer status = JwtUtil.verify(jwt);
            if (status == BADTOKEN || status == UNKNOWNBAD) {
                logger.info("Bad");
                ctx.set("error.status_code",401);
                ctx.set("error.message","Bad Token");
                throw new RuntimeException("Bad Token");
            }

            /**
             * 过期，如果redis里有他就重新发JWT并更新redis
             */
            if (status == EXPIREDTOKEN) {
                logger.info("Expired");
                String claim = JwtUtil.getClaim(jwt);
                JSONObject json = JSONUtil.parseObj(claim);
                try {
                    Integer userId = Integer.parseInt(json.get("userId").toString());

                    role = Integer.parseInt(json.get("roleId").toString());

                    ctx.set("userId", userId);

                    String key = "userId" + userId.toString() + "role" + role.toString();
                    if (redisTemplate.opsForValue().get(key) != null) {
                        logger.info("用户已登录，派发新的Token。");
                        String username = json.get("userName").toString();
                        jwt = JwtUtil.generateToken(username, role, userId);
                        redisTemplate.opsForValue().getAndSet(key, jwt);
                    }
                } catch (Exception e) {
                    ctx.set("error.status_code", 401);
                    ctx.set("error.message","Bad Token");
                    throw new RuntimeException("Bad Token");
                }
            } else {
                /**
                 * 正常
                 */
                try {
                    logger.info("NORMAL");

                    Integer userId = JwtUtil.getUserId(jwt);

                    ctx.set("userId", userId);

                    role = JwtUtil.getRole(jwt);
                } catch (Exception e) {
                    ctx.set("error.status_code", 401);
                    ctx.set("error.message","Bad Token");
                    throw new RuntimeException("Bad Token");
                }
            }
        }

        ctx.getResponse().setHeader("authorization", jwt);

        /**
         * 像context中传递必要的信息，可根据需要修改
         */

        ctx.set("role", role);

        /**
         * 解析数据
         */

        String claim = JwtUtil.getClaim(jwt);

        ctx.addZuulRequestHeader("userInfo", claim);

        try {
            JSONObject claimJson = JSONUtil.parseObj(claim);
            String userId = claimJson.get("userId").toString();
            ctx.addZuulRequestHeader("id", userId);
        } catch (Exception ignored) {
        }

        return null;
    }
}
