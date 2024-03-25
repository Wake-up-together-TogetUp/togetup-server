package com.wakeUpTogetUp.togetUp.infra.aws.s3.model;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class CustomFile implements AutoCloseable {
    private final InputStream inputStream;
    private final long length;
    private final String contentType;
    private final String originalFilename;

    private CustomFile(MultipartFile file, byte[] data) {
        this.inputStream = new ByteArrayInputStream(data);
        this.length = data.length;
        this.contentType = file.getContentType();
        this.originalFilename = file.getOriginalFilename();
    }

    private CustomFile(MultipartFile file) throws IOException {
        this.inputStream = file.getInputStream();
        this.length = file.getSize();
        this.contentType = file.getContentType();
        this.originalFilename = file.getOriginalFilename();
    }

    public static CustomFile fromProcessedFile(MultipartFile file, byte[] data) {
        return new CustomFile(file, data);
    }

    public static CustomFile fromRawFile(MultipartFile file) {
        try {
            return new CustomFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(Status.IMAGE_IO_EXCEPTION);
        }
    }

    @Override
    public void close() throws Exception {
        inputStream.close();
    }
}
