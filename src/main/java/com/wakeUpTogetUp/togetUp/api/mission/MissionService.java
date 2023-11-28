package com.wakeUpTogetUp.togetUp.api.mission;

import com.azure.ai.vision.imageanalysis.DetectedObject;
import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Likelihood;
import com.wakeUpTogetUp.togetUp.api.alarm.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.mission.dto.request.MissionLogCreateReq;
import com.wakeUpTogetUp.togetUp.api.mission.model.Emotion;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.mission.service.AzureAiService;
import com.wakeUpTogetUp.togetUp.api.mission.service.GoogleVisionService;
import com.wakeUpTogetUp.togetUp.api.mission.service.ObjectDetectionService;
import com.wakeUpTogetUp.togetUp.api.notification.NotificationService;
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
    private final GoogleVisionService googleVisionService;
    private final AlarmRepository alarmRepository;
    private final MissionLogRepository missionLogRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final NotificationService notificationService;

    // 객체 인식
    public List<DetectedObject> getObjectDetectionResult(String object, MultipartFile missionImage)
            throws Exception {
        List<DetectedObject> detectedObjects = azureAiService.detectObjectByVer40(missionImage);

        if (detectedObjects.size() == 0) {
            throw new BaseException(Status.MISSION_OBJECT_NOT_FOUND);
        }

        // todo: 객체 정리하고 비교할 자료구조 찾기

        List<DetectedObject> highestConfidenceObjects = detectedObjects.stream()
                .filter(objetDetected -> objetDetected.getName().toLowerCase().equals(object))
                .sorted(Comparator.comparing(DetectedObject::getConfidence).reversed())
                .limit(3)
                .collect(Collectors.toList());

        if (highestConfidenceObjects.isEmpty()) {
            throw new BaseException(Status.MISSION_FAILURE);
        }

        return highestConfidenceObjects;
    }

    // 표정 인식
    public List<FaceAnnotation> getFaceRecognitionResult(String object, MultipartFile missionImage) {
        Emotion emotion = Emotion.fromName(object);
        List<FaceAnnotation> faceAnnotations = googleVisionService.getFaceRecognitionResult(missionImage);

        if (faceAnnotations.isEmpty()) {
            throw new BaseException(Status.MISSION_OBJECT_NOT_FOUND);
        }

        List<FaceAnnotation> highestConfidenceFaces = faceAnnotations.stream()
                .filter(face -> getLikelihood(emotion, face).getNumber() >= 3)
                .sorted(Comparator.comparing(FaceAnnotation::getDetectionConfidence).reversed())
                .limit(3)
                .collect(Collectors.toList());

        if (highestConfidenceFaces.isEmpty()) {
            throw new BaseException(Status.MISSION_FAILURE);
        }

        return highestConfidenceFaces;
    }

    private Likelihood getLikelihood(Emotion emotion, FaceAnnotation face) {
        switch (emotion) {
            case JOY:
                return face.getJoyLikelihood();
            case SORROW:
                return face.getSorrowLikelihood();
            case ANGER:
                return face.getAngerLikelihood();
            case SURPRISE:
                return face.getSurpriseLikelihood();
            default:
                throw new BaseException(Status.INVALID_OBJECT_NAME);
        }
    }

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

        if (Objects.nonNull(room))
            notificationService.sendNotificationToUsersInRoom(room, user);

    }
}

