package com.wakeUpTogetUp.togetUp.api.fcmNotification;

import com.wakeUpTogetUp.togetUp.api.fcmNotification.dto.response.PushLogRes;
import com.wakeUpTogetUp.togetUp.api.fcmNotification.entity.PushLog;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationProvider {
    private final PushLogRepository pushLogRepository;
    // pushlogres List 반환
    public List<PushLogRes> getUserPushLogList(Integer userId) {
        List<PushLog> pushLogList = pushLogRepository.findAllByReceiverId(userId);

        return EntityDtoMapper.INSTANCE.toPushLogResList(pushLogList);
    }
}
