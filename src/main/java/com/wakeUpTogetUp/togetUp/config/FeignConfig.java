package com.wakeUpTogetUp.togetUp.config;

import org.springframework.context.annotation.Bean;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableFeignClients(basePackageClasses = TogetUpApplication.class)
public class FeignConfig {
        @Bean
        public RequestInterceptor requestInterceptor() {
            return template -> template.header("Content-Type", "application/x-www-form-urlencoded");
        }


        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }
    }