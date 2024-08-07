package com.wakeUpTogetUp.togetUp.api.notification;

import com.wakeUpTogetUp.togetUp.api.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserIdOrderByCreatedAtDesc(Integer userId);

    Notification findByUserIdAndId(Integer userId, Integer notificationId);

    void deleteByUserIdAndId(Integer userId, Integer notificationId);
}

