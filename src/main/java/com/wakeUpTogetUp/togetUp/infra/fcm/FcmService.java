package com.wakeUpTogetUp.togetUp.infra.fcm;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.*;
import com.wakeUpTogetUp.togetUp.api.notification.vo.NotificationSendVo;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmTokenDeleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {
    private final FirebaseMessaging firebaseMessaging;
    private final ApplicationEventPublisher eventPublisher;


    public void sendMessageToTokens(NotificationSendVo notificationSendVo) {

        MulticastMessage message = getPreconfiguredMessageToTokens(notificationSendVo);
        ApiFuture<BatchResponse> response = sendAndGetResponse(message);
        deleteInvalidToken(response, notificationSendVo.getFcmTokens().stream()
                .map(FcmToken::getValue)
                .collect(Collectors.toList()));
    }


    public void deleteInvalidToken(ApiFuture<BatchResponse> response, List<String> deviceTokens) {

        try {
            if (response.get().getFailureCount() > 0) {
                List<String> failedDeviceTokens = new ArrayList<>();
                List<SendResponse> responses = response.get().getResponses();
                IntStream.range(0, responses.size())
                        .filter(idx -> !responses.get(idx).isSuccessful())
                        .forEach(idx -> {

                            String token = deviceTokens.get(idx);
                            MessagingErrorCode errorCode = responses.get(idx).getException().getMessagingErrorCode();
                            String errorMessage = responses.get(idx).getException().getMessage();

                            if (errorCode == MessagingErrorCode.UNREGISTERED)
                                failedDeviceTokens.add(deviceTokens.get(idx));

                            log.warn("[PushException][{}]{} {}",
                                    token,
                                    errorCode,
                                    errorMessage);


                        });

                eventPublisher.publishEvent(new FcmTokenDeleteEvent(failedDeviceTokens));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private ApiFuture<BatchResponse> sendAndGetResponse(MulticastMessage message) {
        ApiFuture<BatchResponse> response = firebaseMessaging.sendMulticastAsync(message);
        return response;
    }


    private MulticastMessage getPreconfiguredMessageToTokens(NotificationSendVo notificationSendVo) {
        return getPreconfiguredMulticastMessageBuilder(notificationSendVo).addAllTokens(notificationSendVo.getFcmTokens().stream()
                        .map(FcmToken::getValue)
                        .collect(Collectors.toList()))
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(NotificationSendVo notificationSendVo) {
        return Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(notificationSendVo.getTitle())
                        .setBody(notificationSendVo.getBody())
                        .build());
    }

    private MulticastMessage.Builder getPreconfiguredMulticastMessageBuilder(NotificationSendVo notificationSendVo) {
        return MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(notificationSendVo.getTitle())
                        .setBody(notificationSendVo.getBody())
                        .build())
                .putAllData(notificationSendVo.getDataMap());
    }

}
