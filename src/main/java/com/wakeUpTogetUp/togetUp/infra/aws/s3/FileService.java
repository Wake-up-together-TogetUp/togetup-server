package com.wakeUpTogetUp.togetUp.infra.aws.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.wakeUpTogetUp.togetUp.infra.aws.s3.model.CustomFile;
import com.wakeUpTogetUp.togetUp.utils.FileUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public List<String> uploadFiles(MultipartFile[] files, String uploadFilePath) {
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            fileUrls.add(uploadFile(CustomFile.fromRawFile(file), uploadFilePath));
        }

        return fileUrls;
    }

    @Transactional
    public String uploadFile(CustomFile file, String uploadFilePath) {
        String path = FileUtil.generatePath(uploadFilePath, file.getOriginalFilename());

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(file.getContentType());
        objectMetaData.setContentLength(file.getLength());

        amazonS3Client.putObject(
                new PutObjectRequest(bucket, path, file.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, path).toString();
    }
}
