package com.wakeUpTogetUp.togetUp.api.mission.objectDetection;

import com.wakeUpTogetUp.togetUp.api.mission.objectDetection.dto.response.ObjectDetectionRes;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.ImageCompressor;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class NaverObjectDetectionService {
    @Value("${naver.application.client-id}")
    private String clientId;

    @Value("${naver.application.client-secret}")
    private String clientSecret;

    private final ImageCompressor imageCompressor;
    private final ObjectMapper mapper;

    public ObjectDetectionRes detectObject(MultipartFile image) {

        StringBuffer reqStr = new StringBuffer();

        try {
            String paramName = "image"; // 파라미터명은 image로 지정
//            String imgFile = "이미지 파일 경로 ";
//            File uploadFile = new File(imgFile);

            String apiURL = "https://naveropenapi.apigw.ntruss.com/vision-obj/v1/detect"; // 객체 인식
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
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
            String fileName = image.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
            writer.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

//            FileInputStream inputStream = new FileInputStream(uploadFile);
//            byte[] buffer = new byte[4096];
//            int bytesRead = -1;
//
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }

            outputStream.write(imageCompressor.compressImage(image, 0.5f));
            outputStream.flush();
//            inputStream.close();

            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            BufferedReader br = null;
            int responseCode = con.getResponseCode();

            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 오류 발생
                System.out.println("error!!!!!!! responseCode= " + responseCode);
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }

            String inputLine;
            if(br != null) {
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());

                return mapper.readValue(response.toString(), ObjectDetectionRes.class);
            } else {
                throw new BaseException(Status.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            throw new BaseException(Status.INTERNAL_SERVER_ERROR);
        }
    }
}