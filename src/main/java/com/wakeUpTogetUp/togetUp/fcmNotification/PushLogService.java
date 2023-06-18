package com.wakeUpTogetUp.togetUp.fcmNotification;

import com.wakeUpTogetUp.togetUp.fcmNotification.dto.request.PushNotificationReq;
import com.wakeUpTogetUp.togetUp.fcmNotification.entity.PushLog;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PushLogService {
    private final PushLogRepository pushLogRepository;
    private final UserRepository userRepository;

    // pushLog 생성
    @Transactional
    public void createPushLog(User reciever, PushNotificationReq request){
        pushLogRepository.save(
                PushLog.builder()
                        .title(request.getTitle())
                        .message(request.getMessage())
                        .reciever(reciever)
                        .build()
        );
    }
}
