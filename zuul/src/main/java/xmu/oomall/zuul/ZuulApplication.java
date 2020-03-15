package xmu.oomall.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import xmu.oomall.zuul.filter.AuthFilter;
import xmu.oomall.zuul.filter.JwtErrorFilter;
import xmu.oomall.zuul.filter.JwtFilter;
import xmu.oomall.zuul.filter.PostFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

    @Bean
    public JwtFilter jwtFilter() { return new JwtFilter(); }

    @Bean
    public AuthFilter authFilter() { return new AuthFilter(); }

    @Bean
    public PostFilter postFilter() { return new PostFilter(); }

    @Bean
    public JwtErrorFilter jwtErrorFilter() { return new JwtErrorFilter(); }

}
