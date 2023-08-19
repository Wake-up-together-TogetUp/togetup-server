package com.wakeUpTogetUp.togetUp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TogetUpApplication {

	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
		nu.pattern.OpenCV.loadLocally();
//		nu.pattern.OpenCV.loadShared();
//		System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		SpringApplication.run(TogetUpApplication.class, args);
	}
}
