package com.wakeUpTogetUp.togetUp.api.mission.objectDetection;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import com.wakeUpTogetUp.togetUp.api.mission.domain.Letterbox;
import com.wakeUpTogetUp.togetUp.api.mission.domain.ODResult;
import com.wakeUpTogetUp.togetUp.api.mission.domain.ObjectDetectionModel;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.config.ODConfig;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.ImageCompressor;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ObjectDetectionService {

    private final ObjectDetectionModel odm;
    private final ODConfig odConfig;
    private final Letterbox letterbox;
    private final ImageCompressor imageCompressor;

    public ArrayList<String> detectObject(MultipartFile missionImage) {
        long startTime = System.currentTimeMillis();

        try {
            // 기본 정보 출력
            odm.getSession().getInputInfo().keySet().forEach(x -> {
                try {
                    System.out.println("input name = " + x);
                    System.out.println(odm.getSession().getInputInfo().get(x).getInfo().toString());
                } catch (OrtException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(Status.LOAD_MODEL_FAILURE);
        }

        try {
            // image 읽기
//            Mat img = Imgcodecs.imdecode(new MatOfByte(missionImage.getBytes()),
//                    Imgcodecs.IMREAD_COLOR);
            Mat img = Imgcodecs.imdecode(
                    new MatOfByte(imageCompressor.compressImage(missionImage, 0.5f)),
                    Imgcodecs.IMREAD_COLOR);
            Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2RGB);
            Mat image = img.clone();

            // 아래 상자의 굵기, 글자의 크기, 글자의 종류, 글자의 색을 먼저 정의합니다. (비례에 따라 굵기를 설정하는 것이 좋습니다.)
//            int minDwDh = Math.min(img.width(), img.height());
//            int thickness = minDwDh / odConfig.getLineThicknessRatio();
//            double fontSize = minDwDh / odConfig.getFontSizeRatio();
//            int fontFace = Imgproc.FONT_HERSHEY_SIMPLEX;
//            Scalar fontColor = new Scalar(0, 0, 0);

            // image 크기 변경
            image = letterbox.letterbox(image);
            double ratio = letterbox.getRatio();
            double dw = letterbox.getDw();
            double dh = letterbox.getDh();
            int rows = letterbox.getHeight();
            int cols = letterbox.getWidth();
            int channels = image.channels();

            // Mat 객체의 픽셀 값을 Float[] 객체에 할당합니다.
            float[] pixels = new float[channels * rows * cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    double[] pixel = image.get(j, i);
                    for (int k = 0; k < channels; k++) {
                        // 이러한 설정은 image.transpose (2, 0, 1) 작업을 동시에 수행하는 것과 같습니다
                        pixels[rows * cols * k + j * cols + i] = (float) pixel[k] / 255.0f;
                    }
                }
            }

            // OnnxTensor 개체 만들기
            long[] shape = {1L, (long) channels, (long) rows, (long) cols};
            OnnxTensor tensor = OnnxTensor.createTensor(odm.getEnvironment(),
                    FloatBuffer.wrap(pixels), shape);
            HashMap<String, OnnxTensor> stringOnnxTensorHashMap = new HashMap<>();
            stringOnnxTensorHashMap.put(odm.getSession().getInputInfo().keySet().iterator().next(),
                    tensor);

            // 모델 실행
            OrtSession.Result output = odm.getSession().run(stringOnnxTensorHashMap);

            // 결과를 얻기
            // 디버깅
            System.out.println("info = " + output.get(0).getInfo().toString());
            float[][] outputData = (float[][]) output.get(0).getValue();

            ArrayList<String> detectedObejectList = new ArrayList<String>();
            Arrays.stream(outputData).iterator().forEachRemaining(x -> {
                ODResult odResult = new ODResult(x);
                System.out.println("odResult : " + odResult);

//                // 그림틀
//                Point topLeft = new Point((odResult.getX0() - dw) / ratio,
//                        (odResult.getY0() - dh) / ratio);
//                Point bottomRight = new Point((odResult.getX1() - dw) / ratio,
//                        (odResult.getY1() - dh) / ratio);
//                Scalar color = new Scalar(odConfig.getColor(odResult.getClsId()));
//                Imgproc.rectangle(img, topLeft, bottomRight, color, thickness);
//
//                // 틀에 글을 쓰다
//                String boxName = odConfig.getName(odResult.getClsId()) + ": " + odResult.getScore();
//                Point boxNameLoc = new Point((odResult.getX0() - dw) / ratio,
//                        (odResult.getY0() - dh) / ratio - 3);
//
//                Imgproc.putText(img, boxName, boxNameLoc, fontFace, fontSize, fontColor, thickness);

                // 감지된 클래스 리스트 생성
                detectedObejectList.add(odConfig.getName(odResult.getClsId()));
            });

//            // 그림 저장
//            Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2BGR);
//            Imgcodecs.imwrite(savePicPath, img);

            // 걸린 시간 계산
            long endTime = System.currentTimeMillis();

            long timeElapsed = endTime - startTime;
            System.out.println("Execution time in milliseconds: " + timeElapsed);

            return detectedObejectList;

        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(Status.MISSION_OBJECT_NOT_FOUND);
        }
    }
}
