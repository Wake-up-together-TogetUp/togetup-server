package com.wakeUpTogetUp.togetUp.api.mission;

import com.wakeUpTogetUp.togetUp.api.mission.objectDetection.NaverObjectDetectionService;
import com.wakeUpTogetUp.togetUp.api.mission.objectDetection.ObjectDetectionService;
import com.wakeUpTogetUp.togetUp.api.room.RoomRepository;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.api.mission.dto.request.PostMissionLogReq;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final ObjectDetectionService objectDetectionService;
    private final NaverObjectDetectionService naverObjectDetectionService;
    private final MissionRepository missionRepository;
    private final MissionLogRepository missionLogRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    // 객체 인식
    public boolean recognizeObject(String object, MultipartFile missionImage) {
        // 일치 여부 확인
        boolean isConsistent = false;
        for(String objectDetected : naverObjectDetectionService.detectObject(missionImage)
                .getPredictions().get(0)
                .getDetection_names()){
            System.out.println("objectDetected = " + objectDetected);

            if (objectDetected.equals(object)) {
                isConsistent = true;
                break;
            }
        }

        // 일치하지 않으면
        if(!isConsistent)
            throw new BaseException(Status.MISSION_FAILURE);
        else
            return isConsistent;
    }

    public boolean recognizeObjectByModel(String object, MultipartFile missionImage) {
        // 일치 여부 확인
        boolean isConsistent = false;
        for(String objectDetected : objectDetectionService.detectObject(missionImage)){

            System.out.println("objectDetected = " + objectDetected);

            if (objectDetected.equals(object)) {
                isConsistent = true;
                break;
            }
        }

        // 일치하지 않으면
        if(!isConsistent)
            throw new BaseException(Status.MISSION_FAILURE);
        else
            return isConsistent;
    }

    // 미션 수행 기록하기
    @Transactional
    public Integer createMissionLog(int userId, PostMissionLogReq req){
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new BaseException(Status.USER_NOT_FOUND));

        Mission mission = missionRepository.findById(req.getMissionId())
                .orElseThrow(
                        () -> new BaseException(Status.INVALID_MISSION_ID));

        Room room = Objects.isNull(req.getRoomId())
                ? null
                : roomRepository.findById(req.getRoomId())
                        .orElseThrow(
                                () -> new BaseException(Status.ALARM_NOT_FOUND));

        MissionLog missionLog = MissionLog.builder()
                .alarmName(req.getAlarmName())
                .missionPicLink(req.getMissionPicLink())
                .user(user)
                .room(room)
                .mission(mission)
                .build();

        MissionLog missionLogCreated = missionLogRepository.save(missionLog);

        return missionLogCreated.getId();
    }
}

