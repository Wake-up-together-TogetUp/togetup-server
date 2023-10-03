package com.wakeUpTogetUp.togetUp.utils.ImageProcessing.vo;

import java.io.InputStream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageDrawResult {
    private InputStream inputStream;
    private long size;
}
