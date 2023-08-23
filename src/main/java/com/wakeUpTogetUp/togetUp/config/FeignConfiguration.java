package com.wakeUpTogetUp.togetUp.config;

import com.wakeUpTogetUp.togetUp.TogetUpApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableFeignClients(basePackageClasses = TogetUpApplication.class)
public class FeignConfiguration {
        @Bean
        public RequestInterceptor requestInterceptor() {
            return template -> template.header("Content-Type", "application/x-www-form-urlencoded");
        }


        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }
    }