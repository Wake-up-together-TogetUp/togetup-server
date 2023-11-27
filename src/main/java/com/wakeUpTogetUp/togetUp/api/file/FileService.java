package com.wakeUpTogetUp.togetUp.api.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.ImageProcessing.vo.ImageProcessResult;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String S3Bucket;
    private final AmazonS3Client amazonS3Client;

    @Transactional
    public String uploadFile(MultipartFile file, String type) throws Exception {
        if (type.equals("group")) {
            return uploadToBucket(file, type);
        } else {
            throw new BaseException(Status.BAD_REQUEST_PARAM);
        }
    }

    @Transactional
    public String uploadMissionImage(MultipartFile file, ImageProcessResult imageProcessResult) throws Exception {
        String uploadFilePath = ("mission/" + getFolderName() + "/" + getUuidFileName(file.getName()));
        return uploadMissionImageToBucket(file, imageProcessResult, uploadFilePath);
    }

    public String uploadMissionImageToBucket(MultipartFile file, ImageProcessResult imageProcessResult, String key) {
        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(file.getContentType());
        objectMetaData.setContentLength(imageProcessResult.getLength());

        amazonS3Client.putObject(
                new PutObjectRequest(S3Bucket, key, imageProcessResult.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(S3Bucket, key).toString();
    }

    public String uploadToBucket(MultipartFile file, String uploadFilePath) throws Exception {
        String uploadFileName = getUuidFileName(file.getName());

        long size = file.getSize();

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(file.getContentType());
        objectMetaData.setContentLength(size);

        String keyName = uploadFilePath + "/" + uploadFileName;

        amazonS3Client.putObject(
                new PutObjectRequest(S3Bucket, keyName, file.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return amazonS3Client.getUrl(S3Bucket, keyName).toString();
    }

    @Transactional
    public List<String> uploadFiles(MultipartFile[] fileList, String type) throws Exception {
        // avatar, group, mission
        if (type.equals("avatar") || type.equals("group")) {
            List<String> filePathList = new ArrayList<>();
            String uploadFilePath = type;

            for (MultipartFile file : fileList) {
                filePathList.add(uploadToBucket(file, uploadFilePath));
            }

            return filePathList;
        } else {
            throw new BaseException(Status.BAD_REQUEST_PARAM);
        }
    }

    /**
     * S3에 업로드된 파일 삭제
     */
    @Transactional
    public void deleteFile(String fileName) {
        // 파일 이름 자르기
        fileName = fileName.substring(48, fileName.length());

        boolean isObjectExist = amazonS3Client.doesObjectExist(S3Bucket, fileName);

        if (isObjectExist) {
            amazonS3Client.deleteObject(S3Bucket, fileName);
        } else {
            throw new BaseException(Status.FILE_NOT_FOUND);
        }
    }

    /**
     * UUID 파일명 반환
     */
    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID() + "." + ext;
    }

    /**
     * 년/월/일 폴더명 반환
     */
    private String getFolderName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", "/");
    }
}
