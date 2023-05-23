package com.wakeUpTogetUp.togetUp.missions;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.missions.dto.response.PostObjectRecognitionRes;
import com.wakeUpTogetUp.togetUp.objectDetection.ObjectDetection;
import org.springframework.stereotype.Service;

@Service
public class MissionService {

    private ObjectDetection objectDetection = new ObjectDetection();

    // TODO : 구현
    public PostObjectRecognitionRes recognizeObject(String object) {
        try{
            objectDetection.detectObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(Status.INTERNAL_SERVER_ERROR);
        }

        return new PostObjectRecognitionRes();
    }
}
