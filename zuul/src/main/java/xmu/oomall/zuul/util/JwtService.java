package xmu.oomall.zuul.util;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


/**
 * Created by Administrator on 2019/3/17 0017.
 */
@Service
public class JwtService {

    /**
     *  加密算法 可以抽象到环境变量中配置
     */
    private String MAC_NAME = "HMacSHA256";

    /**
     *  秘钥生成器
     */
    private KeyGenerator keyGenerator;

    /**
     *  加密算法
     */
    private static Algorithm algorithm;

    /**
     *  校验类
     */
    public static JWTVerifier jwtVerifier;

    /**
     *  校验器 用于生成 JWTVerifier 校验器
     */
    public static Verification verification;

    @PostConstruct
    @Scheduled(cron = "0 5 * * *  ?")
    public void initAndRefresh() throws NoSuchAlgorithmException {

        if (null == keyGenerator) {
            keyGenerator = KeyGenerator.getInstance(MAC_NAME);
        }

        SecretKey secretKey =keyGenerator.generateKey();
        algorithm = Algorithm.HMAC256(secretKey.getEncoded());
        verification = JWT.require(algorithm);
        jwtVerifier = verification.build();
    }

    public static String createToken(String userId, String username) {

        JWTCreator.Builder builder = JWT.create();
        builder.withSubject(userId);
        builder.withIssuer(username);
//        builder.withExpiresAt(new Date(System.currentTimeMillis() + (24 * 3600 * 1000)));
        builder.withExpiresAt(new Date());

        return builder.sign(algorithm);
    }
}
