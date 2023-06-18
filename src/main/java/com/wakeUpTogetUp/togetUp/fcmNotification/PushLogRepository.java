package com.wakeUpTogetUp.togetUp.fcmNotification;

import com.wakeUpTogetUp.togetUp.fcmNotification.entity.PushLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PushLogRepository extends JpaRepository<PushLog, Integer> {
}
