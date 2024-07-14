package com.wakeUpTogetUp.togetUp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableFeignClients
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
@EnableCaching
public class TogetUpApplication {

    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
        nu.pattern.OpenCV.loadLocally();
    }

    public static void main(String[] args) {
        SpringApplication.run(TogetUpApplication.class, args);
    }
}
