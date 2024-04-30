package com.wakeUpTogetUp.togetUp.config;

import com.wakeUpTogetUp.togetUp.api.event.EventPublisher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class EventsConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public InitializingBean eventsInitializer(ApplicationEventPublisher eventPublisher) {
        return () -> EventPublisher.setPublisher(eventPublisher);
    }
}
