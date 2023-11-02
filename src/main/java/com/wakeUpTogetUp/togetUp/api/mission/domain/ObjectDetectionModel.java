package com.wakeUpTogetUp.togetUp.api.mission.domain;

import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;

import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ObjectDetectionModel {
    private OrtSession session;
    private OrtEnvironment environment;
    private OrtSession.SessionOptions sessionOptions;

    @Value("${my.path.model-path}")
    private String modelPath;

}