package xmu.oomall.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import xmu.oomall.zuul.dao.ZuulDao;
import xmu.oomall.zuul.util.CommonUtil;

import java.util.List;
import java.util.Map;

/**
 * @author zty
 * 1层权限过滤器，用于鉴权
 */
public class AuthFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private ZuulDao dao;

    @Override
    public String filterType(){
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            String role = ctx.get("role").toString();
            String sa = "1";
            return !sa.equals(role);
        } catch (Exception e) {
           return true;
        }
    }

    /**
     * @return null
     * @throws ZuulException
     * @author zty
     */
    private String preProcedureUrl(String url) {
        int i = 3;
        int index;
        try {
            while (true) {
                index = CommonUtil.ordinalIndexOf(url, "/", i);
                if (index == -1) {
                    break;
                }

                char c = url.charAt(index + 1);
                if (c >= '0' && c <= '9') {
                    Integer nextIdx = CommonUtil.ordinalIndexOf(url, "/", i + 1);
                    if (nextIdx == -1) {
                        nextIdx = url.length();
                    }
                    String part1 = url.substring(0, index);

                    String part2 = "/{id}";

                    String part3 = "";
                    try {
                        part3 = url.substring(nextIdx, url.length());
                    } catch (Exception ignore) {
                    }
                    return part1 + part2 + part3;
                }
                i++;
            }
        } catch (Exception e) {
            return url;
        }
        return url;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        Integer role = (Integer)ctx.get("role");
        String url = ctx.getRequest().getRequestURI();

        url = preProcedureUrl(url);

//        Integer index = CommonUtil.ordinalIndexOf(url, "/", 2);
//        if (index > -1) {
//            url = url.substring(CommonUtil.ordinalIndexOf(url, "/", 2), url.length());
//        } else {
//            url = "/";
//        }

        ctx.set("url",url);

        String method = ctx.getRequest().getMethod();

        Map<String, List<Integer>> authTable = dao.getAuthTable(method);

            List<Integer> roles = authTable.get(url);

            if (roles == null || roles.isEmpty()) {
                ctx.set("error.status_code", 404);
                ctx.set("error.message","Not Found");
                throw new RuntimeException("not found");
            }

            if (!roles.contains(role)) {
                ctx.set("error.status_code", 403);
                ctx.set("error.message","Unauthorized");
                throw new RuntimeException("没有权限");
            }

        return null;
    }
}
