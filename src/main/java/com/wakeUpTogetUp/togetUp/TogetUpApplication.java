package com.wakeUpTogetUp.togetUp;

import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TogetUpApplication {

	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");

//		nu.pattern.OpenCV.loadShared();
		nu.pattern.OpenCV.loadLocally();
//		System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		SpringApplication.run(TogetUpApplication.class, args);
	}
}
