package com.wakeUpTogetUp.togetUp.api.fcmNotification;

import com.google.firebase.messaging.*;
import com.wakeUpTogetUp.togetUp.api.fcmNotification.dto.request.PushNotificationReq;
import com.wakeUpTogetUp.togetUp.api.fcmNotification.dto.response.PushNotificationRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final FirebaseMessaging firebaseMessaging;

    @Transactional
    public PushNotificationRes sendMessageToToken(PushNotificationReq request)
            throws InterruptedException, ExecutionException {
        // message 만들기
        Message message = getPreconfiguredMessageToToken(request);
        String response = sendAndGetResponse(message);

        return PushNotificationRes.builder()
                .message("Sent message to token.")
                .category(request.getCategory())
                .response(response)
                .build();
    }
    @Transactional
    public PushNotificationRes sendMessageToTokens(PushNotificationReq request)
            throws ExecutionException, InterruptedException {
        // message 만들기
        MulticastMessage message = getPreconfiguredMessageToTokens(request);
        Integer failureCnt = sendAndGetResponse(message);

        return PushNotificationRes.builder()
                .message("Sent message to tokens")
                .category(request.getCategory())
                .response(failureCnt.toString())
                .build();
    }

    @Transactional
    public PushNotificationRes sendMessageToTopic (PushNotificationReq request)
            throws ExecutionException, InterruptedException {
        // message 만들기
        Message message = getPreconfiguredMessageToTopic(request);
        String response = sendAndGetResponse(message);

        return PushNotificationRes.builder()
                .message("Sent message to tokens.")
                .category(request.getCategory())
                .topic(request.getTopic())
                .response(response)
                .build();
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private int sendAndGetResponse(MulticastMessage message) throws ExecutionException, InterruptedException {
        return FirebaseMessaging.getInstance().sendMulticastAsync(message).get().getFailureCount();
    }

    // topic
    private Message getPreconfiguredMessageToTopic(PushNotificationReq request) {
        return getPreconfiguredMessageBuilder(request).setTopic(request.getTopic())
                .build();
    }

    // token
    private Message getPreconfiguredMessageToToken(PushNotificationReq request) {
        return getPreconfiguredMessageBuilder(request).setToken(request.getTokenList().get(0))
                .build();
    }

    // token 여러 개
    private MulticastMessage getPreconfiguredMessageToTokens(PushNotificationReq request) {
        return getPreconfiguredMulticastMessageBuilder(request).addAllTokens(request.getTokenList())
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationReq request) {
        ApnsConfig apnsConfig = getApnsConfig(request.getCategory());
        return Message.builder()
                .setApnsConfig(apnsConfig)
                .setNotification(Notification.builder()
                        .setTitle(request.getTitle())
                        .setBody(request.getMessage())
                        .build());
    }

    private MulticastMessage.Builder getPreconfiguredMulticastMessageBuilder(PushNotificationReq request) {
        ApnsConfig apnsConfig = getApnsConfig(request.getCategory());
        return MulticastMessage.builder()
                .setApnsConfig(apnsConfig)
                .setNotification(Notification.builder()
                        .setTitle(request.getTitle())
                        .setBody(request.getMessage())
                        .build());
    }

    private ApnsConfig getApnsConfig(String category) {
        return ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory(category)
                        .setBadge(1)
                        .setSound("default")
                        .build())
                .build();
    }

    // 구독 요청
    public void subscribe(List<String> memberTokenList, String topicName) {
        firebaseMessaging.subscribeToTopicAsync(
                memberTokenList,
                topicName
        );
    }

    // 구독 취소
    public void unSubscribe(List<String> memberTokenList, String topicName) {
        firebaseMessaging.unsubscribeFromTopicAsync(
                memberTokenList,
                topicName
        );
    }

    // TODO : 토픽 삭제
    public void deleteTopic(String topicName){

    }
}
