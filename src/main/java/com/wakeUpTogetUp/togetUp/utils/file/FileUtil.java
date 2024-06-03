package com.wakeUpTogetUp.togetUp.utils.file;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.DateTimeProvider;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtil {

    private static final String FILE_NAME_FORMAT = "%s/%s/%s";
    private static final String FILE_DATE_TIME_FORMAT = "yyyy/MM/dd";

    public static byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new BaseException("파일 읽기를 실패했습니다.", e, Status.INVALID_IMAGE);
        }
    }

    public static String extractExtension(String fileName) {
        FileValidator.validateFileName(fileName);

        return fileName
                .substring(fileName.lastIndexOf(".") + 1)
                .toLowerCase(Locale.ROOT);
    }

    public static String generatePath(String directoryPath, String fileName) {
        String key = generateKey(fileName);
        String dateTime = DateTimeProvider.getDateTime(FILE_DATE_TIME_FORMAT);

        return String.format(FILE_NAME_FORMAT, directoryPath, dateTime, key);
    }

    public static String generateKey(String fileName) {
        return UUID.randomUUID() + fileName;
    }
}
