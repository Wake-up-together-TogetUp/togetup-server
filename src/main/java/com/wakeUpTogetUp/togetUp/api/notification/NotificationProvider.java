package com.wakeUpTogetUp.togetUp.api.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationProvider {
    private final NotificationRepository notificationRepository;
    // pushlogres List 반환
    // TODO : 수정
//    public List<NotificationRes> getUserPushLogList(Integer userId) {
//        List<Notification> notificationList = notificationRepository.findAllByFcmToken_Id(userId);
//
//        return EntityDtoMapper.INSTANCE.toNotificationResList(notificationList);
//    }
}
