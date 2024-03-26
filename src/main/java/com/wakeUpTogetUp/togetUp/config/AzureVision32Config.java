package com.wakeUpTogetUp.togetUp.config;

import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionClient;
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureVision32Config {

    @Value("${azure.computer-vision.endpoint}")
    private String endpoint;

    @Value("${azure.computer-vision.key}")
    private String key;

    @Bean
    public ComputerVisionClient computerVisionClient() {
        return ComputerVisionManager.authenticate(key).withEndpoint(endpoint);
    }
}
