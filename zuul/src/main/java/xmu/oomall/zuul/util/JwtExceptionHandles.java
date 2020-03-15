package xmu.oomall.zuul.util;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/19 0019.
 */
@RestControllerAdvice
public class JwtExceptionHandles {

    @ExceptionHandler(TokenExpiredException.class)
    public Object TokenExpiredException(TokenExpiredException exception) {
        System.out.println();
        System.out.println(exception.getMessage());
        System.out.println();

        Map<String, String> map = new HashMap<>(16);
        map.put(TokenExpiredException.class.getSimpleName(), exception.getMessage());
        return map;
    }

    @ExceptionHandler(JWTVerificationException.class)
    public Object JWTVerificationException(JWTVerificationException exception) {
        System.out.println();
        System.out.println(exception.getMessage());
        System.out.println();

        Map<String, String> map = new HashMap<>(16);
        map.put(JWTVerificationException.class.getSimpleName(), exception.getMessage());
        return map;
    }

}
