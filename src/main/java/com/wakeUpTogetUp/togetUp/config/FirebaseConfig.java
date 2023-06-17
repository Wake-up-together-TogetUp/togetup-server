package com.wakeUpTogetUp.togetUp.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    @Value("${my.file.firebase-configuration-file}")
    private String resourceName;

    @Bean
    FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
    @Bean
    FirebaseApp firebaseApp(GoogleCredentials credentials) {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

        return FirebaseApp.initializeApp(options);
    }
    @Bean
    GoogleCredentials googleCredentials() {
        try (InputStream serviceAccount = new ClassPathResource(resourceName).getInputStream()) {
            return GoogleCredentials.fromStream(serviceAccount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(Status.INTERNAL_SERVER_ERROR);
        }
    }
}
