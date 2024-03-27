package com.wakeUpTogetUp.togetUp.utils.image;

import static org.apache.commons.imaging.Imaging.getMetadata;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.FileUtil;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageUtil {

    protected static final String IMAGE_IO_ERROR_MESSAGE = "이미지 입출력을 실패했습니다";
    protected static final String IMAGE_WRITE_ERROR_MESSAGE = "이미지 쓰기에 실패했습니다.";
    private static final String EXTRACT_METADATA_ERROR_MESSAGE = "파일 메타데이터 추출에 실패했습니다.";

    protected static BufferedImage readImage(byte[] data) {
        try {
            return ImageIO.read(new ByteArrayInputStream(data));
        } catch (IOException e) {
            throw new BaseException(IMAGE_IO_ERROR_MESSAGE, e, Status.INVALID_IMAGE);
        }
    }

    protected static TiffImageMetadata getImageMetadata(MultipartFile file) {
        return readExifMetadata(FileUtil.getBytes(file));
    }

    // TODO: 다른 파일 형식 메타 데이터도 가져오게
    private static TiffImageMetadata readExifMetadata(byte[] data) {
        try{
            ImageMetadata imageMetadata = getMetadata(data);
            if (imageMetadata == null) {
                return null;
            }
            JpegImageMetadata jpegMetadata = (JpegImageMetadata) imageMetadata;
            return jpegMetadata.getExif();
        } catch (Exception e) {
            throw new BaseException(EXTRACT_METADATA_ERROR_MESSAGE, e, Status.INVALID_IMAGE);
        }
    }
}