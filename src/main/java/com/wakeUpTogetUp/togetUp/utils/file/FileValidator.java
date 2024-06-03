package com.wakeUpTogetUp.togetUp.utils.file;

import com.wakeUpTogetUp.togetUp.infra.aws.s3.model.ImageContentType;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileValidator {

    public static boolean isValidImageFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        String extension = FileUtil.extractExtension(fileName);

        return isValidFileName(fileName) && isValidFileContentType(contentType, extension);
    }

    public static void validateImageFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        String extension = FileUtil.extractExtension(fileName);

        validateFileName(fileName);
        validateImageContentType(contentType, extension);
    }

    public static void validateFileName(String fileName) {
        if (!isValidFileName(fileName)) {
            throw new BaseException(Status.INVALID_FILE_NAME_EXCEPTION);
        }
    }

    public static void validateImageContentType(String contentType, String extension) {
        if (!isValidFileContentType(contentType, extension)) {
            throw new BaseException(Status.INVALID_FILE_CONTENT_TYPE_EXCEPTION);
        }
    }

    private static boolean isValidFileName(String fileName) {
        return fileName != null && fileName.contains(".");
    }

    private static boolean isValidFileContentType(String contentType, String extension) {
        return contentType != null && ImageContentType.exists(contentType, extension);
    }
}
