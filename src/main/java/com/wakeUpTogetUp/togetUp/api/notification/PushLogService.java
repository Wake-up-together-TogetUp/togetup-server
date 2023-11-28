//package com.wakeUpTogetUp.togetUp.api.notification;
//
//import com.wakeUpTogetUp.togetUp.api.notification.dto.request.PushNotificationReq;
//import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
//import com.wakeUpTogetUp.togetUp.api.users.model.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class PushLogService {
//    private final NotificationRepository notificationRepository;
//    private final UserRepository userRepository;
//
//    // pushLog 생성
////    // TODO : 수정하기
////    @Transactional
////    public void createPushLog(User receiver, PushNotificationReq request){
////        notificationRepository.save(
////                Notification.builder()
////                        .title(request.getTitle())
////                        .content(request.getMessage())
////                        .group(receiver)
////                        .fcmToken(fcm)
////                        .build()
////        );
////    }
//}
