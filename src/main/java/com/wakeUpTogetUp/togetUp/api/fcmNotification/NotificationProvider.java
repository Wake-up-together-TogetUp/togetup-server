package com.wakeUpTogetUp.togetUp.api.fcmNotification;

import com.wakeUpTogetUp.togetUp.api.fcmNotification.dto.response.NotificationRes;
import com.wakeUpTogetUp.togetUp.api.fcmNotification.entity.Notification;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationProvider {
    private final NotificationRepository notificationRepository;
    // pushlogres List 반환
    public List<NotificationRes> getUserPushLogList(Integer userId) {
        List<Notification> notificationList = notificationRepository.findAllByReceiverId(userId);

        return EntityDtoMapper.INSTANCE.toNotificationResList(notificationList);
    }
}
