package com.wakeUpTogetUp.togetUp.fcmNotification;

import com.wakeUpTogetUp.togetUp.fcmNotification.entity.PushLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PushLogRepository extends JpaRepository<PushLog, Integer> {
    List<PushLog> findAllByReceiverId(Integer receiverId);
}
