package com.wakeUpTogetUp.togetUp.utils.image;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.infra.aws.s3.model.ImageContentType;
import com.wakeUpTogetUp.togetUp.utils.file.FileUtil;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageProcessor extends ImageUtil {

    public static byte[] compressUntil(MultipartFile file, int sizeOfMB) {
        ImageContentType contentType = ImageContentType.getByContentType(file.getContentType());
        byte[] data = FileUtil.getBytes(file);
        int sizeLimit = 1024 * 1024 * sizeOfMB;

        if (data.length < sizeLimit) {
            return data;
        }

        // TODO: 디버그용 삭제
        System.out.println("[압축]");
        System.out.println("original size: " + data.length);
        // TODO: 무한루프 빠지지 않게 처리하기
        // TODO: 최적의 quality 찾기
        while (data.length >= sizeLimit) {
            data = compress(data, 0.5f, contentType);
            System.out.println(data.length);
        }

        TiffImageMetadata metadata = getImageMetadata(file);
        return orientByteImage(data, metadata, contentType);
    }

    private static byte[] compress(byte[] imageByte, float quality, ImageContentType contentType) {
        BufferedImage originalImage = readImage(imageByte);

        try (ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream()) {
            Iterator<ImageWriter> writers =
                    ImageIO.getImageWritersByFormatName(contentType.getExtension());

            if (writers.hasNext()) {
                ImageWriter writer = writers.next();
                ImageWriteParam param = writer.getDefaultWriteParam();
                setCompressImageWriterParam(param, quality);

                writer.setOutput(ImageIO.createImageOutputStream(compressedImageStream));
                writer.write(null, new IIOImage(originalImage, null, null), param);
                writer.dispose();
            } else {
                throw new IllegalStateException("No writers found");
            }

            return compressedImageStream.toByteArray();
        } catch (IOException e) {
            throw new BaseException(IMAGE_WRITE_ERROR_MESSAGE, e, Status.INVALID_IMAGE);
        }
    }

    private static void setCompressImageWriterParam(ImageWriteParam param, float quality) {
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
        }
    }

    public static byte[] resize(byte[] imageByte, double ratio) throws IOException {
        BufferedImage originalImage = readImage(imageByte);

        int scaledWidth = (int) (originalImage.getWidth() * ratio);
        int scaledHeight = (int) (originalImage.getHeight() * ratio);

        Image resizeImage = originalImage
                .getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_FAST);
        BufferedImage newImage =
                new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);

        Graphics gp = newImage.getGraphics();
        gp.drawImage(resizeImage, 0, 0, null);
        gp.dispose();

        ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream();
        ImageIO.write(newImage, "jpg", compressedImageStream);
        compressedImageStream.close();

        return compressedImageStream.toByteArray();
    }

    public static byte[] rotate(byte[] imageByte, int degrees) throws IOException {
        BufferedImage originalImage = readImage(imageByte);

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(degrees),
                (double) originalImage.getWidth() / 2,
                (double) originalImage.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        BufferedImage rotatedImage = op.filter(originalImage, null);

        ByteArrayOutputStream rotatedImageStream = new ByteArrayOutputStream();
        ImageIO.write(rotatedImage, "jpg", rotatedImageStream);
        rotatedImageStream.close();

        return rotatedImageStream.toByteArray();
    }

    protected static byte[] orientByteImage(byte[] imageByte, TiffImageMetadata metadata, ImageContentType contentType) {
        int orientation = getOrientation(metadata);
        BufferedImage originalImage = readImage(imageByte);
        BufferedImage rotatedImage = transformImage(originalImage, orientation);

        BufferedImage newImage = new BufferedImage(
                rotatedImage.getWidth(),
                rotatedImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = newImage.createGraphics();
        g.drawImage(rotatedImage, 0, 0, null);
        g.dispose();

        try (ByteArrayOutputStream rotatedImageStream = new ByteArrayOutputStream();) {
            ImageIO.write(newImage, contentType.getExtension(), rotatedImageStream);
            return rotatedImageStream.toByteArray();
        } catch (IOException e) {
            throw new BaseException(IMAGE_WRITE_ERROR_MESSAGE, e, Status.INVALID_IMAGE);
        }
    }

    protected static BufferedImage orientImage(BufferedImage image, TiffImageMetadata metadata) {
        int orientation = getOrientation(metadata);
        image = transformImage(image, orientation);

        BufferedImage newImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);

        return newImage;
    }

    private static int getOrientation(TiffImageMetadata metadata) {
        try {
            TiffField tiffField = metadata != null
                    ? metadata.findField(TiffTagConstants.TIFF_TAG_ORIENTATION)
                    : null;

            return tiffField != null ? tiffField.getIntValue() : 1;
        } catch (ImageReadException e) {
            throw new BaseException(e, Status.INVALID_IMAGE);
        }
    }

    private static BufferedImage transformImage(BufferedImage originalImage, int orientation) {
        AffineTransform affineTransform = new AffineTransform();

        switch (orientation) {
            case 1:
                break;
            case 2: // Flip horizontally
                affineTransform.scale(-1.0, 1.0);
                affineTransform.translate(-originalImage.getWidth(), 0);
                break;
            case 3: // Rotate 180 degrees
                affineTransform.translate(originalImage.getWidth(), originalImage.getHeight());
                affineTransform.rotate(Math.PI);
                break;
            case 4: // Flip vertically
                affineTransform.scale(1.0, -1.0);
                affineTransform.translate(0, -originalImage.getHeight());
                break;
            case 5: // Flip vertically and rotate 90 degrees
                affineTransform.scale(1.0, -1.0);
                affineTransform.translate(0, -originalImage.getHeight());
                affineTransform.translate(originalImage.getHeight(), 0);
                affineTransform.rotate(Math.PI / 2);
                break;
            case 6: // Rotate 90 degrees
                affineTransform.translate(originalImage.getHeight(), 0);
                affineTransform.rotate(Math.PI / 2);
                break;
            case 7: // Flip horizontally and rotate 90 degrees
                affineTransform.scale(-1.0, 1.0);
                affineTransform.translate(-originalImage.getHeight(), 0);
                affineTransform.translate(originalImage.getHeight(), 0);
                affineTransform.rotate(Math.PI / 2);
                break;
            case 8: // Rotate 270 degrees
                affineTransform.translate(0, originalImage.getWidth());
                affineTransform.rotate(3 * Math.PI / 2);
                break;
        }

        AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);

        return op.filter(originalImage, null);
    }
}