package com.wakeUpTogetUp.togetUp.api.mission.service;

import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.FaceRecognitionRes;
import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.ObjectDetectionRes;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.ImageProcessing.ImageProcessor;
import com.wakeUpTogetUp.togetUp.utils.ImageProcessing.vo.ImageProcessedResult;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import lombok.RequiredArgsConstructor;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class NaverAiService {

    @Value("${naver.application.client-id}")
    private String clientId;

    @Value("${naver.application.client-secret}")
    private String clientSecret;

    private final ImageProcessor imageProcessor;
    private final ObjectMapper objectMapper;

    public ObjectDetectionRes detectObject(MultipartFile file) throws Exception {
        long startTime = System.currentTimeMillis();

        String paramName = "image"; // 파라미터명은 image로 지정
        String apiURL = "https://naveropenapi.apigw.ntruss.com/vision-obj/v1/detect"; // 객체 인식
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setDoOutput(true);
        con.setDoInput(true);

        // multipart request
        String boundary = "---" + System.currentTimeMillis() + "---";
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
        con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
        OutputStream outputStream = con.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
        String LINE_FEED = "\r\n";

        // file 추가
        String fileName = file.getOriginalFilename();
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append(
                "Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName
                        + "\"").append(LINE_FEED);
        writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();

        System.out.println("file.getSize() = " + file.getSize());
        ImageProcessedResult imageProcessedResult = resizeUntil(file, 0.2f, 300000);
        outputStream.write(imageProcessedResult.getResult());
        outputStream.flush();

        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();

        BufferedReader br = null;
        int responseCode = con.getResponseCode();

        if (responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 오류 발생
            System.out.println("ERROR : responseCode = " + responseCode);
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        // 출력
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        System.out.println(response.toString());

        // 걸린 시간 계산
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("통신 걸린 시간 : " + timeElapsed);

        // TODO : 리팩토링 하기
        if (responseCode == 200) {
            ObjectDetectionRes odr = objectMapper.readValue(response.toString(),
                    ObjectDetectionRes.class);
            odr.setOriginalImageProcessedResult(imageProcessedResult);

            return odr;
        } else {
            throw new BaseException(Status.INTERNAL_SERVER_ERROR);
        }
    }

    public FaceRecognitionRes recognizeFace(MultipartFile file) throws Exception {
        String paramName = "image"; // 파라미터명은 image로 지정
        String apiURL = "https://naveropenapi.apigw.ntruss.com/vision/v1/face"; // 얼굴 감지
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setDoOutput(true);
        con.setDoInput(true);

        // multipart request
        String boundary = "---" + System.currentTimeMillis() + "---";
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
        con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
        OutputStream outputStream = con.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
        String LINE_FEED = "\r\n";

        // file 추가
        String fileName = file.getOriginalFilename();
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append(
                "Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName
                        + "\"").append(LINE_FEED);
        writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();

        System.out.println("file.getSize() " + file.getSize());
        ImageProcessedResult imageProcessedResult = resizeUntil(file, 0.3f, 500000);
        outputStream.write(imageProcessedResult.getResult());
        outputStream.flush();

        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();

        BufferedReader br = null;
        int responseCode = con.getResponseCode();

        if (responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 오류 발생
            System.out.println("ERROR : responseCode = " + responseCode);
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();
        System.out.println(response.toString());

        if (responseCode == 200) {
            FaceRecognitionRes frr = objectMapper.readValue(response.toString(),
                    FaceRecognitionRes.class);
            frr.setOriginalImageProcessedResult(imageProcessedResult);
            return frr;
        } else {
            throw new BaseException(Status.INTERNAL_SERVER_ERROR);
        }
    }

    private ImageProcessedResult resizeUntil(MultipartFile file, float quality, int max)
            throws Exception {
        long startTime = System.currentTimeMillis();
        byte[] result = file.getBytes();

        if (result.length > max) {
            // 압축
            result = imageProcessor.compress(result, quality);
            System.out.println("result.length after compress = " + result.length);

            // 리사이즈
            while (result.length > max) {
                double ratio = 240000.0 / result.length;
                result = imageProcessor.resize(result, ratio);
                System.out.println("result.length after resize = " + result.length);
            }
        }

        // 이미지 orientation에 따라 회전
        TiffImageMetadata metadata = imageProcessor.getImageMetadata(file);
        ImageProcessedResult ipResult = imageProcessor.orientImage(result, metadata);

        // 걸린 시간 계산
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("이미지 처리 걸린 시간 : " + timeElapsed);

        return ipResult;
    }
}