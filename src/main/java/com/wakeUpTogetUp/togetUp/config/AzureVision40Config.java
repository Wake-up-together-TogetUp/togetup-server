package com.wakeUpTogetUp.togetUp.config;

import com.azure.ai.vision.common.VisionServiceOptions;
import com.azure.ai.vision.imageanalysis.ImageAnalysisFeature;
import com.azure.ai.vision.imageanalysis.ImageAnalysisOptions;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EnumSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureVision40Config {

    @Value("${azure.computer-vision.endpoint}")
    private String endpoint;

    @Value("${azure.computer-vision.key}")
    private String key;

    @Bean
    public VisionServiceOptions visionServiceOptions() throws MalformedURLException {
        return new VisionServiceOptions(new URL(endpoint), key);
    }

    @Bean
    public ImageAnalysisOptions imageAnalysisOptions() {
        ImageAnalysisOptions analysisOptions = new ImageAnalysisOptions();
        analysisOptions.setFeatures(
                EnumSet.of(
                        ImageAnalysisFeature.OBJECTS,
                        ImageAnalysisFeature.TAGS));

        return analysisOptions;
    }
}
