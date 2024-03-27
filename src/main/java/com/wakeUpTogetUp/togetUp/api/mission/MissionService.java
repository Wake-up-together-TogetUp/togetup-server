package com.wakeUpTogetUp.togetUp.api.mission;

import com.wakeUpTogetUp.togetUp.api.alarm.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.mission.domain.VisionAnalysisResult;
import com.wakeUpTogetUp.togetUp.api.mission.dto.request.MissionCompleteReq;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionCompleteRes;
import com.wakeUpTogetUp.togetUp.api.mission.domain.CustomAnalysisEntity;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionType;
import com.wakeUpTogetUp.togetUp.api.mission.repository.MissionLogRepository;
import com.wakeUpTogetUp.togetUp.api.notification.NotificationService;
import com.wakeUpTogetUp.togetUp.api.users.UserAvatarService;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.api.users.UserService;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.vo.UserProgressResult;
import com.wakeUpTogetUp.togetUp.api.users.vo.UserStat;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.wakeUpTogetUp.togetUp.api.users.UserServiceHelper.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MissionService {

    private final int MAX_MATCHES_LIMIT = 3;

    private final VisionServiceFactory visionServiceFactory;
    private final AlarmRepository alarmRepository;
    private final MissionLogRepository missionLogRepository;
    private final UserService userService;
    private final UserAvatarService userAvatarService;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public List<CustomAnalysisEntity> getMissionResult(MissionType type, String object, MultipartFile missionImage) {
        VisionAnalysisResult result =
                visionServiceFactory
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

        UserProgressResult progressResult = userService.userProgress(user);
        createMissionLog(user, req);
        sendNotificationIfRoomExists(alarm, user);

        boolean isNewAvatarAvailable = false;
        if (progressResult.isUserLevelUp()) {
            isNewAvatarAvailable = userAvatarService.unlockAvatarIfAvailableExist(user);
        }

        return MissionCompleteRes.builder()
                .userStat(UserStat.from(user))
                .isUserLevelUp(progressResult.isUserLevelUp())
                .isUserLevelUp(isNewAvatarAvailable)
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

    private void createMissionLog(User user, MissionCompleteReq req) {
        Alarm alarm = alarmRepository.findById(req.getAlarmId())
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));

        MissionLog missionLog = MissionLog.builder()
                .alarmName(alarm.getName())
                .missionPicLink(req.getMissionPicLink())
                .user(user)
                .room(alarm.getRoom())
                .build();

        missionLogRepository.save(missionLog);
    }
}