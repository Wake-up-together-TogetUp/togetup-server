package com.wakeUpTogetUp.togetUp.api.mission;

import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Likelihood;
import com.wakeUpTogetUp.togetUp.api.alarm.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.mission.dto.request.MissionCompleteReq;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionCompleteRes;
import com.wakeUpTogetUp.togetUp.api.mission.model.CustomDetectedObject;
import com.wakeUpTogetUp.togetUp.api.mission.model.CustomDetectedTag;
import com.wakeUpTogetUp.togetUp.api.mission.model.Emotion;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.mission.service.AzureVision32Service;
import com.wakeUpTogetUp.togetUp.api.mission.service.AzureVision40Service;
import com.wakeUpTogetUp.togetUp.api.mission.service.GoogleVisionService;
import com.wakeUpTogetUp.togetUp.api.mission.service.ObjectDetectionService;
import com.wakeUpTogetUp.togetUp.api.notification.NotificationService;
import com.wakeUpTogetUp.togetUp.api.users.UserAvatarService;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.api.users.UserService;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.vo.UserProgressResult;
import com.wakeUpTogetUp.togetUp.api.users.vo.UserStat;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    private final ObjectDetectionService objectDetectionService;
    private final AzureVision32Service azureVision32Service;
    private final AzureVision40Service azureVision40Service;
    private final GoogleVisionService googleVisionService;
    private final AlarmRepository alarmRepository;
    private final MissionLogRepository missionLogRepository;
    private final UserService userService;
    private final UserAvatarService userAvatarService;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public List<CustomDetectedObject> getObjectDetectedResult(String object, MultipartFile missionImage)
            throws Exception {
//        List<CustomDetectedObject> detectedObjects = azureVision32Service.detectObjects(missionImage);
        List<CustomDetectedObject> detectedObjects = azureVision40Service.detectObjects(missionImage);

        printDetectedObjects(detectedObjects);

        List<CustomDetectedObject> highestConfidenceObjects = detectedObjects.stream()
                .filter(objetDetected -> objetDetected.getObjectParent().toLowerCase().contains(object))
                .sorted(Comparator.comparing(CustomDetectedObject::getConfidence).reversed())
                .limit(3)
                .collect(Collectors.toList());

        if (highestConfidenceObjects.isEmpty()) {
            throw new BaseException(Status.MISSION_FAILURE);
        }

        return highestConfidenceObjects;
    }

    public List<CustomDetectedTag> getTagDetectionResult(String object, MultipartFile missionImage)
            throws Exception {
        List<CustomDetectedTag> detectedTags = azureVision40Service.detectTags(missionImage);

        printDetectedTags(detectedTags);

        List<CustomDetectedTag> highestConfidenceObjects = detectedTags.stream()
                .filter(objetDetected -> objetDetected.getName().toLowerCase().contains(object))
                .sorted(Comparator.comparing(CustomDetectedTag::getConfidence).reversed())
                .limit(3)
                .collect(Collectors.toList());

        if (highestConfidenceObjects.isEmpty()) {
            throw new BaseException(Status.MISSION_FAILURE);
        }

        return highestConfidenceObjects;
    }

    private void printDetectedObjects(List<CustomDetectedObject> detectedObjects) {
        System.out.println("detectedObjects.size() = " + detectedObjects.size());
        detectedObjects.forEach(object -> System.out.println(object.getObject()));
        detectedObjects.forEach(object -> System.out.println(object.getParent()));
    }

    private void printDetectedTags(List<CustomDetectedTag> tags) {
        System.out.println("tags.size() = " + tags.size());
        tags.forEach(tag -> System.out.println(tag.getName()));
    }

    // 표정 인식
    public List<FaceAnnotation> getFaceRecognitionResult(String object, MultipartFile missionImage) {
        Emotion emotion = Emotion.fromName(object);
        List<FaceAnnotation> faceAnnotations = googleVisionService.getFaceRecognitionResult(missionImage);

        if (faceAnnotations.isEmpty()) {
            throw new BaseException(Status.NO_DETECTED_OBJECT);
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
            log.info("objectDetected = " + objectDetected);

            if (objectDetected.equals(object)) {
                return;
            }
        }

        throw new BaseException(Status.MISSION_FAILURE);
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
            // 미션 성공 후 처리 로직과 알림 보내기 로직을 독립적으로 분리
            // 알림을 보내는데 실패해도 모든 과정이 롤백되지 않음
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

