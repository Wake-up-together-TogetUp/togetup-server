package com.wakeUpTogetUp.togetUp.api.mission.service;

import static com.wakeUpTogetUp.togetUp.api.users.UserServiceHelper.findExistingUser;

import com.wakeUpTogetUp.togetUp.api.alarm.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.avatar.application.UserAvatarService;
import com.wakeUpTogetUp.togetUp.api.avatar.application.model.UnlockAvatarResult;
import com.wakeUpTogetUp.togetUp.api.mission.domain.CustomAnalysisEntity;
import com.wakeUpTogetUp.togetUp.api.mission.domain.VisionAnalysisResult;
import com.wakeUpTogetUp.togetUp.api.mission.dto.request.MissionCompleteReq;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionCompleteRes;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionType;
import com.wakeUpTogetUp.togetUp.api.mission.repository.MissionLogRepository;
import com.wakeUpTogetUp.togetUp.api.notification.NotificationService;
import com.wakeUpTogetUp.togetUp.api.users.UserProgressService;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.api.users.domain.User;
import com.wakeUpTogetUp.togetUp.api.users.domain.model.UserProgressResult;
import com.wakeUpTogetUp.togetUp.api.users.domain.vo.UserStat;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class MissionService {

    private static final int MAX_MATCHES_LIMIT = 3;

    private final UserProgressService userProgressService;

    private final VisionServiceFactory visionServiceFactory;
    private final UserAvatarService userAvatarService;
    private final NotificationService notificationService;

    private final AlarmRepository alarmRepository;
    private final MissionLogRepository missionLogRepository;
    private final UserRepository userRepository;

    public List<CustomAnalysisEntity> getMissionResult(MissionType type, String object, MultipartFile missionImage) {
        VisionAnalysisResult result = visionServiceFactory
                .getVisionService(type)
                .getResult(missionImage, object);

        result.print();

        if (result.isFail()) {
            throw new BaseException(Status.MISSION_FAILURE);
        }

        return result.getMatches(MAX_MATCHES_LIMIT);
    }

    @Transactional
    public MissionCompleteRes afterMissionComplete(int userId, MissionCompleteReq req) {
        User user = findExistingUser(userRepository, userId);

        Alarm alarm = alarmRepository.findById(req.getAlarmId())
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));

        UserProgressResult progressResult = userProgressService.progress(user);

        boolean isAvatarUnlocked = false;
        if (progressResult.isUserLevelUp()) {
            UnlockAvatarResult unlockAvatarResult = userAvatarService.attemptToUnlockAvatar(user);
            isAvatarUnlocked = unlockAvatarResult.isUnlocked();
        }

        createMissionLog(user, req);
        sendNotificationIfRoomExists(alarm, user);
        sendNotificationIfAvatarUnlocked(isAvatarUnlocked, user.getId());


        return MissionCompleteRes.builder()
                .userStat(UserStat.from(user))
                .isUserLevelUp(progressResult.isUserLevelUp())
                .isAvatarUnlockAvailable(isAvatarUnlocked)
                .build();
    }

    private void sendNotificationIfRoomExists(Alarm alarm, User user) {
        if (alarm.isRoomAlarm()) {
            try {
                notificationService.sendNotificationToUsersInRoom(alarm.getId(), user.getId());
            } catch (BaseException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void sendNotificationIfAvatarUnlocked(boolean isAvatarUnlocked, Integer userId) {
        if (isAvatarUnlocked) {
            notificationService.sendNotificationToAvatarUnlockedUser(userId);
        }
    }

    private void createMissionLog(User user, MissionCompleteReq req) {
        Alarm alarm = alarmRepository.findById(req.getAlarmId())
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));

        MissionLog missionLog = MissionLog.builder()
                .alarmName(alarm.getName())
                .missionPicLink(req.getMissionPicLink())
                .alarm(alarm)
                .user(user)
                .room(alarm.getRoom())
                .build();

        missionLogRepository.save(missionLog);
    }
}