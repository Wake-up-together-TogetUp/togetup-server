package com.wakeUpTogetUp.togetUp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakeUpTogetUp.togetUp.api.auth.AuthUserResolver;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final AuthUserResolver authUserResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authUserResolver);
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 앞으로 만들 모든 CORS 정보를 적용한다
        registry.addMapping("/**")
                // Header의 Origin에 들어있는 주소가 http://localhost:3000인 경우를 허용한다
                .allowedOrigins("*")
                // 모든 HTTP Method를 허용한다.
                .allowedMethods("*")
                // HTTP 요청의 Header에 어떤 값이든 들어갈 수 있도록 허용한다.
                .allowedHeaders("*");
                // 자격증명 사용을 허용한다.
                // 해당 옵션 사용시 allowedOrigins를 * (전체)로 설정할 수 없다.
               // .addAllOriginPatterns("*");
                //.allowCredentials(true);
    }


}