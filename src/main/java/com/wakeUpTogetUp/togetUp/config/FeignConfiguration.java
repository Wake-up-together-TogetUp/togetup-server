package com.wakeUpTogetUp.togetUp.config;

import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

//public class FeignConfiguration {
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

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