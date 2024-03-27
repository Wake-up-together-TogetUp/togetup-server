package com.wakeUpTogetUp.togetUp.infra.aws.s3.model;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.util.Arrays;

public enum ImageContentType {
    JPG("image/jpeg", "jpg"),
    JPEG("image/jpeg", "jpeg");
//    PNG("image/png", "png"),
//    WEBP("image/webp", "webp"),
//    HEIF("application/octet-stream", "heic");

    private final String contentType;
    private final String extension;

    ImageContentType(String contentType, String extension) {
        this.contentType = contentType;
        this.extension = extension;
    }

    private String getContentType() {
        return contentType;
    }

    public String getExtension() {
        return extension;
    }

    public static ImageContentType getByContentType(String contentType) {
        return Arrays.stream(ImageContentType.values())
                .filter(type -> type.contentType.equals(contentType))
                .findAny()
                .orElseThrow(() -> new BaseException(Status.INVALID_FILE_CONTENT_TYPE_EXCEPTION));
    }

    public static boolean exists(String contentType, String extension) {
        return Arrays.stream(ImageContentType.values())
                .anyMatch(type -> type.fieldsMatches(contentType, extension));
    }

    private boolean fieldsMatches(String contentType, String extension) {
        return getExtension().equalsIgnoreCase(extension) && getContentType().equalsIgnoreCase(contentType);
    }
}
