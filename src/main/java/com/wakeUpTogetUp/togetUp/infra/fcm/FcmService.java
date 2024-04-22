package com.wakeUpTogetUp.togetUp.infra.fcm;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.*;
import com.wakeUpTogetUp.togetUp.api.event.EventPublisher;
import com.wakeUpTogetUp.togetUp.api.notification.vo.NotificationSendEvent;
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


    public void sendMessageToTokens(NotificationSendEvent notificationSendEvent) {

        MulticastMessage message = getPreconfiguredMessageToTokens(notificationSendEvent);
        ApiFuture<BatchResponse> response = sendAndGetResponse(message);
        deleteInvalidToken(response, notificationSendEvent.getFcmTokens().stream()
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

                EventPublisher.raise(new FcmTokenDeleteEvent(failedDeviceTokens));
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


    private MulticastMessage getPreconfiguredMessageToTokens(NotificationSendEvent notificationSendEvent) {
        return getPreconfiguredMulticastMessageBuilder(notificationSendEvent).addAllTokens(notificationSendEvent.getFcmTokens().stream()
                        .map(FcmToken::getValue)
                        .collect(Collectors.toList()))
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(NotificationSendEvent notificationSendEvent) {
        return Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(notificationSendEvent.getTitle())
                        .setBody(notificationSendEvent.getBody())
                        .build());
    }

    private MulticastMessage.Builder getPreconfiguredMulticastMessageBuilder(NotificationSendEvent notificationSendEvent) {
        return MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(notificationSendEvent.getTitle())
                        .setBody(notificationSendEvent.getBody())
                        .build())
                .putAllData(notificationSendEvent.getDataMap());
    }

}
