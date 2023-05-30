package com.wakeUpTogetUp.togetUp.missions;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.missions.dto.response.PostObjectRecognitionRes;
import com.wakeUpTogetUp.togetUp.objectDetection.ObjectDetection;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MissionService {

    private ObjectDetection objectDetection = new ObjectDetection();

    public boolean recognizeObject(String object, MultipartFile missionImage) {
        // 일치 여부 확인
        boolean isConsistent = false;
        for(String objectDetected : objectDetection.detectObject(missionImage)){
            if(objectDetected.equals(object))
                isConsistent = true;
        }

        // 일치하지 않으면
        if(!isConsistent)
            throw new BaseException(Status.MISSION_UNSUCCESS);
        else
            return isConsistent;
    }
}

