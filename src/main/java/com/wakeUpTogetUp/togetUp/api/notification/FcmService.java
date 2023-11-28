package com.wakeUpTogetUp.togetUp.api.notification;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.*;
import com.wakeUpTogetUp.togetUp.api.notification.vo.NotificationSendVo;
import com.wakeUpTogetUp.togetUp.api.notification.dto.response.PushNotificationRes;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final FirebaseMessaging firebaseMessaging;

//    @Transactional
//    public PushNotificationRes sendMessageToToken(NotificationSendVo notificationSendVo)
//            throws InterruptedException, ExecutionException {
//        // message 만들기
//        Message message = getPreconfiguredMessageToTokens(notificationSendVo);
//        String response = sendAndGetResponse(message);
//
//        return PushNotificationRes.builder()
//                .message("Sent message to token.")
//                .category(notificationSendVo.getCategory())
//                .response(response)
//                .build();
//    }

    @Transactional
    public void sendMessageToTokens(NotificationSendVo notificationSendVo) {
        // message 만들기
        MulticastMessage message = getPreconfiguredMessageToTokens(notificationSendVo);
        ApiFuture<BatchResponse> response = sendAndGetResponse(message);


    }

//    @Transactional
//    public PushNotificationRes sendMessageToTopic (NotificationSendVo notificationSendVo)
//            throws ExecutionException, InterruptedException {
//        // message 만들기
//        Message message = getPreconfiguredMessageToTopic(notificationSendVo);
//        String response = sendAndGetResponse(message);
//
//        return PushNotificationRes.builder()
//                .message("Sent message to tokens.")
//                .category(notificationSendVo.getCategory())
//                .topic(notificationSendVo.getTopic())
//                .response(response)
//                .build();
//    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private ApiFuture<BatchResponse> sendAndGetResponse(MulticastMessage message) {
        ApiFuture<BatchResponse> response = FirebaseMessaging.getInstance().sendMulticastAsync(message);
        return response;
    }

//    // topic
//    private Message getPreconfiguredMessageToTopic(NotificationSendVo notificationSendVo) {
//        return getPreconfiguredMessageBuilder(notificationSendVo).setTopic(notificationSendVo.getTopic())
//                .build();
//    }

//    // token
//    private Message getPreconfiguredMessageToToken(NotificationSendVo notificationSendVo) {
//        return getPreconfiguredMessageBuilder(notificationSendVo).setToken(notificationSendVo.getTokenList())
//                .build();
//    }

    // token 여러 개
    private MulticastMessage getPreconfiguredMessageToTokens(NotificationSendVo notificationSendVo) {
        return getPreconfiguredMulticastMessageBuilder(notificationSendVo).addAllTokens(notificationSendVo.getFcmTokens().stream()
                        .map(FcmToken::getValue)
                        .collect(Collectors.toList()))
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(NotificationSendVo notificationSendVo) {
        // ApnsConfig apnsConfig = getApnsConfig(notificationSendVo.getCategory());
        return Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(notificationSendVo.getTitle())
                        .setBody(notificationSendVo.getBody())
                        .build());
    }

    private MulticastMessage.Builder getPreconfiguredMulticastMessageBuilder(NotificationSendVo notificationSendVo) {
        // ApnsConfig apnsConfig = getApnsConfig(notificationSendVo.getCategory());
        return MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(notificationSendVo.getTitle())
                        .setBody(notificationSendVo.getBody())
                        .build())
                .putAllData(notificationSendVo.getDataMap());
    }

//    private ApnsConfig getApnsConfig(String category) {
//        return ApnsConfig.builder()
//                .setAps(Aps.builder()
//                        .setCategory(category)
//                        .setBadge(1)
//                        .setSound("default")
//                        .build())
//                .build();
//    }

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
    public void deleteTopic(String topicName) {

    }
}
