package com.wakeUpTogetUp.togetUp.api.mission;

import com.wakeUpTogetUp.togetUp.api.mission.service.NaverAiService;
import com.wakeUpTogetUp.togetUp.api.mission.service.ObjectDetectionService;
import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.FaceRecognitionRes;
import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.FaceRecognitionRes.Face;
import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.FaceRecognitionRes.Face.EmotionValue;
import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.ObjectDetectionRes;
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
    private final NaverAiService naverAiService;
    private final MissionRepository missionRepository;
    private final MissionLogRepository missionLogRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    // 객체 인식
    public ObjectDetectionRes recognizeObject(String object, MultipartFile missionImage)
            throws Exception {
        ObjectDetectionRes objectDetectionRes = naverAiService.detectObject(missionImage);

        // 인식된 객체가 없을때
        if(objectDetectionRes.getPredictions().get(0).getNum_detections() == 0)
            throw new BaseException(Status.MISSION_OBJECT_NOT_FOUND);

        // 미션 성공 여부 판별
        for(String objectDetected : objectDetectionRes.getPredictions().get(0).getDetection_names()){
            System.out.println("objectDetected = " + objectDetected);

            if (objectDetected.equals(object))
                return objectDetectionRes;
        }
        throw new BaseException(Status.MISSION_FAILURE);
    }

    // 표정 인식
    public FaceRecognitionRes recognizeEmotion(String object, MultipartFile missionImage)
            throws Exception {
        FaceRecognitionRes faceRecognitionRes = naverAiService.recognizeFace(missionImage);

        // 인식된 객체가 없을때
        if(faceRecognitionRes.getInfo().getFaceCount() == 0)
            throw new BaseException(Status.MISSION_OBJECT_NOT_FOUND);

        // 미션 성공 여부 판별
        for(Face face : faceRecognitionRes.getFaces()) {
            System.out.println("face.getEmotion().getValue() = " + face.getEmotion().getValue());

            if(face.getEmotion().getValue().equals(EmotionValue.valueOf(object)))
                return faceRecognitionRes;
        }
        throw new BaseException(Status.MISSION_FAILURE);
    }

    // 모델로 객체 인식하기
    public void recognizeObjectByModel(String object, MultipartFile missionImage) {
        for(String objectDetected : objectDetectionService.detectObject(missionImage)){
            System.out.println("objectDetected = " + objectDetected);

            if (objectDetected.equals(object))
                return;
        }

        throw new BaseException(Status.MISSION_FAILURE);
    }

    // 미션 수행 기록하기
    // TODO : 미션 수행 기록 추가 + REQUEST 수정하기
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

