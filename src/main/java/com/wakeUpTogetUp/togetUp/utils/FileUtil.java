package com.wakeUpTogetUp.togetUp.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtil {

    private static final String FILE_NAME_FORMAT = "%s/%s/%s";
    private static final String FILE_DATE_TIME_FORMAT = "yyyy/MM/dd";

    public static String extractExtension(String fileName) {
        FileValidator.validateFileName(fileName);

        return fileName
                .substring(fileName.lastIndexOf(".") + 1)
                .toLowerCase(Locale.ROOT);
    }

    public static String generatePath(String directoryPath, String fileName) {
        String key = generateKey(fileName);
        String dateTime = ZonedDateTime.now()
                .format(
                        DateTimeFormatter
                                .ofPattern(FILE_DATE_TIME_FORMAT)
                                .withLocale(Locale.ROOT));

        return String.format(FILE_NAME_FORMAT, directoryPath, dateTime, key);
    }

    public static String generateKey(String fileName) {
        return UUID.randomUUID() + fileName;
    }
}
