package com.wakeUpTogetUp.togetUp.files;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.files.dto.response.PostFileRes;
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

    @Transactional
    public PostFileRes uploadFiles(MultipartFile[] fileList, String fileType) throws Exception {
        // avatar, group, mission
        if(fileType.equals("avatar") || fileType.equals("group") || fileType.equals("mission") ) {
            List<String> imagePathList = new ArrayList<>();
            String uploadFilePath = fileType;

            // mission 일 때
            if(fileType.equals("mission")) {
                // 날짜 폴더 붙이기
                uploadFilePath += ("/" + getFolderName());
                // TODO : 그룹 이름 붙이기
            }

            for(MultipartFile file: fileList) {
                // 파일 이름
                String originalName = file.getOriginalFilename();
                String uploadFileName = getUuidFileName(originalName);

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

                String imagePath = amazonS3Client.getUrl(S3Bucket, keyName).toString(); // 접근가능한 URL 가져오기
                imagePathList.add(imagePath);
            }

            return new PostFileRes(imagePathList);
        } else
            throw new BaseException(Status.BAD_REQUEST_PARAM);
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
