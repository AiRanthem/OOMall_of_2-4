package xmu.oomall.address.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zty
 */
public class JwtUtil {
    /**
    密钥
     **/
    public static final String SECRET_KEY = "123456";
    /**
    过期时间
     **/
    public static final long TOKEN_EXPIRE_TIME = 2 * 60 * 1000;
    /**
     * refreshToken过期时间
     */
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 10 * 60 * 1000;
    /**
     * 签发人
     */
    private static final String ISSUER = "zhongtianyun";

    /**
     * 生成签名
     * 示例：放入role
     *
     */
    public static String generateToken(String username, Integer roleId, Integer userId){
        Date now = new Date();

        /**
         * 算法
         */
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        Map<String, Object> head = new HashMap<>(2);
        head.put("typ","JWT");
        head.put("alg", "HMAC256");

        /**
         * 在这里使用withClaim添加载荷中的信息
         */
        String token = JWT.create()
                .withHeader(head)
                .withIssuer(ISSUER)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + TOKEN_EXPIRE_TIME))
                .withClaim("role", roleId)
                .withClaim("username",username)
                .withClaim("userId", userId)
                .sign(algorithm);
        return token;
    }

    /**
     * 验证token
     * 0 : 成功
     * 1 : 内容错误（被篡改）
     * 2 : 过期
     * -1 : 未知错误
     */
    public static Integer verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            verifier.verify(token);
        } catch (Exception ex){
            if (ex instanceof SignatureVerificationException) {
                return 1;
            } else if (ex instanceof TokenExpiredException) {
                return 2;
            }
            return -1;
        }
        return 0;
    }

    /**
     * 从token获取属性
     * 弃用
     */
    public static String getAttr(String token, String attr){
        try{
            Claim claim = JWT.decode(token).getClaim(attr);
            return claim.asInt().toString();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获得角色
     * @param token jwt
     * @return role
     */
    public static Integer getRole(String token){
        try{
            Claim claim = JWT.decode(token).getClaim("role");
            return claim.asInt();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取载荷
     */
    public static String getClaim(String token) {
        try{
            DecodedJWT decode = JWT.decode(token);

            HashMap<String, String> claims = new HashMap<>(10);

            claims.put("role", decode.getClaim("role").asInt().toString());
            claims.put("userName", decode.getClaim("username").asString());
            claims.put("userId",decode.getClaim("userId").asInt().toString());

            JSONObject jsonObject = JSONUtil.parseObj(claims);
            return jsonObject.toString();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}