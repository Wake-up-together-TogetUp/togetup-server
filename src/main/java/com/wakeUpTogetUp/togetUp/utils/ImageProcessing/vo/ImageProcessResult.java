package com.wakeUpTogetUp.togetUp.utils.ImageProcessing.vo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import lombok.Getter;

@Getter
public class ImageProcessResult implements AutoCloseable {
    private final byte[] imageBytes;
    private final InputStream inputStream;

    public ImageProcessResult(byte[] imageBytes) {
        this.imageBytes = imageBytes;
        this.inputStream = new ByteArrayInputStream(imageBytes);
    }

    public int getLength() {
        return imageBytes.length;
    }

    @Override
    public void close() throws Exception {
        inputStream.close();
    }
}
