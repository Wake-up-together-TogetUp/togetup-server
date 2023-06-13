package com.wakeUpTogetUp.togetUp.config;

import com.wakeUpTogetUp.togetUp.config.interceptor.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    //메시지 브로커에 관련된 설정을 한다
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");//구독 요청 prefix
        config.setApplicationDestinationPrefixes("/pub");//발행 prefix
    }
//SockJs Fallback을 이용해 노출할 STOMP endpoint를 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*")//setAllowedOrigins("*")
                .withSockJS(); // sock.js를 통하여 낮은 버전의 브라우저에서도 websocket이 동작할수 있게 합니다.
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}