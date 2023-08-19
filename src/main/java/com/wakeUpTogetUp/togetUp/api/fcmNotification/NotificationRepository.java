package com.wakeUpTogetUp.togetUp.api.fcmNotification;

import com.wakeUpTogetUp.togetUp.api.fcmNotification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllByReceiverId(Integer receiverId);
}
