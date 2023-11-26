package com.wakeUpTogetUp.togetUp.api.mission;

import com.azure.ai.vision.imageanalysis.DetectedObject;
import com.wakeUpTogetUp.togetUp.api.alarm.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.mission.dto.request.MissionLogCreateReq;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.mission.service.AzureAiService;
import com.wakeUpTogetUp.togetUp.api.mission.service.ObjectDetectionService;
import com.wakeUpTogetUp.togetUp.api.room.RoomRepository;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.api.users.UserService;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.vo.UserProgressionResult;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final ObjectDetectionService objectDetectionService;
    private final AzureAiService azureAiService;
    private final AlarmRepository alarmRepository;
    private final MissionLogRepository missionLogRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    // 객체 인식
    public List<DetectedObject> getObjectDetectionResult(String object, MultipartFile missionImage)
            throws Exception {
        List<DetectedObject> detectedObjects = azureAiService.detectObject(missionImage);

        // 인식된 객체가 없을때
        if (detectedObjects.size() == 0) {
            throw new BaseException(Status.MISSION_OBJECT_NOT_FOUND);
        }

        List<DetectedObject> highestConfidenceObjects = detectedObjects.stream()
                .filter(objetDetected -> objetDetected.getName().equals(object))
                .sorted(Comparator.comparing(DetectedObject::getConfidence).reversed())
                .limit(3)
                .collect(Collectors.toList());

        if (highestConfidenceObjects.isEmpty()) {
            throw new BaseException(Status.MISSION_FAILURE);
        }

        return highestConfidenceObjects;
    }

    // 표정 인식
//    public FaceRecognitionRes recognizeEmotion(String object, MultipartFile missionImage)
//            throws Exception {
//        FaceRecognitionRes faceRecognitionRes = naverAiService.recognizeFace(missionImage);
//
//        // 인식된 객체가 없을때
//        if (faceRecognitionRes.getInfo().getFaceCount() == 0) {
//            throw new BaseException(Status.MISSION_OBJECT_NOT_FOUND);
//        }
//
//        // 미션 성공 여부 판별
//        for (Face face : faceRecognitionRes.getFaces()) {
//            System.out.println("face.getEmotion().getValue() = " + face.getEmotion().getValue());
//
//            if (face.getEmotion().getValue().equals(EmotionValue.valueOf(object))) {
//                return faceRecognitionRes;
//            }
//        }
//        throw new BaseException(Status.MISSION_FAILURE);
//    }

    // 모델로 객체 인식하기
    public void recognizeObjectByModel(String object, MultipartFile missionImage) {
        for (String objectDetected : objectDetectionService.detectObject(missionImage)) {
            System.out.println("objectDetected = " + objectDetected);

            if (objectDetected.equals(object)) {
                return;
            }
        }

        throw new BaseException(Status.MISSION_FAILURE);
    }

    @Transactional
    public UserProgressionResult afterMissionComplete(int userId, MissionLogCreateReq req) {
        createMissionLog(userId, req);
        return userService.userProgress(userId);
    }

    // TODO : 미션 수행 기록 추가 + REQUEST 수정하기
    public void createMissionLog(int userId, MissionLogCreateReq req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(Status.USER_NOT_FOUND));

        Alarm alarm = alarmRepository.findById(req.getAlarmId())
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));

        Room room = Objects.isNull(req.getRoomId())
                ? null
                : roomRepository.findById(req.getRoomId())
                        .orElseThrow(() -> new BaseException(Status.ROOM_NOT_FOUND));

        MissionLog missionLog = MissionLog.builder()
                .alarmName(alarm.getName())
                .missionPicLink(req.getMissionPicLink())
                .user(user)
                .room(room)
                .build();

        missionLogRepository.save(missionLog);
    }
}

