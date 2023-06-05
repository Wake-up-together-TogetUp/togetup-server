package com.wakeUpTogetUp.togetUp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakeUpTogetUp.togetUp.config.interceptor.AuthenticationInterceptor;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 앞으로 만들 모든 CORS 정보를 적용한다
        registry.addMapping("/**")

                // Header의 Origin에 들어있는 주소가 http://localhost:3000인 경우를 허용한다
                .allowedOrigins("http://localhost:3000")
                // 모든 HTTP Method를 허용한다.
                .allowedMethods("*")
                // HTTP 요청의 Header에 어떤 값이든 들어갈 수 있도록 허용한다.
                .allowedHeaders("*");
                // 자격증명 사용을 허용한다.
                // 해당 옵션 사용시 allowedOrigins를 * (전체)로 설정할 수 없다.
               // .addAllOriginPatterns("*");
                //.allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry reg){
        reg.addInterceptor(new AuthenticationInterceptor(jwtService,objectMapper))
                .order(1)
                .addPathPatterns("/app/users")
                .excludePathPatterns("/app/group")
                .excludePathPatterns("/app/chat");

       //         .addPathPatterns("/**"); //interceptor 작업이 필요한 path를 모두 추가한다.

        //.excludePathPatterns("app/accounts","/app/accounts/auth","app/videos/**");
        // 인가작업에서 제외할 API 경로를 따로 추가할수도 있으나, 일일히 따로 추가하기 어려우므로 어노테이션을 따로 만들어 해결한다.
    }

}