package com.wakeUpTogetUp.togetUp.api.users;


import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmTokenDeleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class UserEventHandler {

    private final UserService userService;

    @EventListener(FcmTokenDeleteEvent.class)
    public void handlePushTokenDeleteEvent(FcmTokenDeleteEvent event) {
        userService.deleteFcmTokens(event.getFcmTokens());
    }

}

