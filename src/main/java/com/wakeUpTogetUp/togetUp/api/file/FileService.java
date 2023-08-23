package com.wakeUpTogetUp.togetUp.api.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileService {
    private String S3Bucket = "togetup"; // Bucket 이름
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AmazonS3Client amazonS3Client;

    /**
     * 파일 업로드
     * @param file
     * @param type
     * @return
     * @throws Exception
     */
    @Transactional
    public String uploadFile(MultipartFile file, String type) throws Exception{
        if(type.equals("mission")) {
            String uploadFilePath = ("mission/" + getFolderName());
            String filePath = uploadToBucket(file, uploadFilePath);

            return filePath;
        }
        else if(type.equals("group")) {
            //String uploadFilePath = ("group/" + getFolderName());
            //그룹프로필 사진 한 장
            String filePath = uploadToBucket(file, type);

            return filePath;
        }
        else
            throw new BaseException(Status.BAD_REQUEST_PARAM);
    }

    /**
     * 파일 리스트 업로드
     * @param fileList
     * @param type
     * @return
     * @throws Exception
     */
    @Transactional
    public List<String> uploadFiles(MultipartFile[] fileList, String type) throws Exception {
        // avatar, group, mission
        if(type.equals("avatar") || type.equals("group")) {
            List<String> filePathList = new ArrayList<>();
            String uploadFilePath = type;

            for(MultipartFile file: fileList)
                filePathList.add(uploadToBucket(file, uploadFilePath));

            return filePathList;
        } else
            throw new BaseException(Status.BAD_REQUEST_PARAM);
    }

    /**
     * 파일 업로드
     * @param file
     * @param uploadFilePath
     * @return
     * @throws Exception
     */
    public String uploadToBucket(MultipartFile file, String uploadFilePath) throws Exception{
        // 파일 이름
        String uploadFileName = getUuidFileName(file.getOriginalFilename());

        // 파일 크기
        long size = file.getSize();

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(file.getContentType());
        objectMetaData.setContentLength(size);

        String keyName = uploadFilePath + "/" + uploadFileName;

        // S3에 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(S3Bucket, keyName, file.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        String filePath = amazonS3Client.getUrl(S3Bucket, keyName).toString(); // 접근가능한 URL 가져오기

        return filePath;
    }
    
    /**
     * S3에 업로드된 파일 삭제
     */
    @Transactional
    public void deleteFile(String fileName) {
        // 파일 이름 자르기
        fileName = fileName.substring(48, fileName.length());

        boolean isObjectExist = amazonS3Client.doesObjectExist(S3Bucket, fileName);

        if (isObjectExist)
            amazonS3Client.deleteObject(S3Bucket, fileName);
        else
            throw new BaseException(Status.FILE_NOT_FOUND);
    }

    /**
     * UUID 파일명 반환
     */
    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
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
