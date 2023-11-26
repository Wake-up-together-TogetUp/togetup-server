package com.wakeUpTogetUp.togetUp.utils.ImageProcessing;

import static org.apache.commons.imaging.Imaging.getMetadata;

import com.azure.ai.vision.common.BoundingBox;
import com.azure.ai.vision.imageanalysis.DetectedObject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageProcessor {

    @Value("${my.path.image-path}")
    private String imagePath;

    public byte[] compress(byte[] imageToByte, float quality) throws Exception {
        System.out.println("[압축]");

        BufferedImage originalImage = readImage(imageToByte);

        ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream();
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

        // 압축
        if (writers.hasNext()) {
            ImageWriter writer = writers.next();
            ImageWriteParam param = writer.getDefaultWriteParam();

            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality); // 0.0 to 1.0
            }

            writer.setOutput(ImageIO.createImageOutputStream(compressedImageStream));
            writer.write(null, new IIOImage(originalImage, null, null), param);
            writer.dispose();
        } else {
            throw new IllegalStateException("No writers found");
        }
        compressedImageStream.close();

        // 압축된 이미지 스트림을 BufferedImage로 변환
        ByteArrayInputStream bis = new ByteArrayInputStream(compressedImageStream.toByteArray());

        // 이미지 저장
//        BufferedImage compressedImage = ImageIO.read(bis);
//        File outputFile = new File(imagePath + "/result_compressed.jpg");
//        ImageIO.write(compressedImage, "jpg", outputFile);

        return compressedImageStream.toByteArray();
    }

    public byte[] resize(byte[] imageToByte, double ratio) throws IOException {
        System.out.println("[리사이즈]");

        BufferedImage originalImage = readImage(imageToByte);

        int scaledWidth = (int) (originalImage.getWidth() * ratio);
        int scaledHeight = (int) (originalImage.getHeight() * ratio);

        // 이미지 품질 설정
        // Image.SCALE_DEFAULT : 기본 이미지 스케일링 알고리즘 사용
        // Image.SCALE_FAST : 이미지 부드러움보다 속도 우선
        // Image.SCALE_REPLICATE : ReplicateScaleFilter 클래스로 구체화 된 이미지 크기 조절 알고리즘
        // Image.SCALE_SMOOTH : 속도보다 이미지 부드러움을 우선
        // Image.SCALE_AREA_AVERAGING : 평균 알고리즘 사용
        Image resizeImage = originalImage.getScaledInstance(scaledWidth, scaledHeight,
                Image.SCALE_FAST);
        BufferedImage newImage = new BufferedImage(scaledWidth, scaledHeight,
                BufferedImage.TYPE_INT_RGB);

        Graphics gp = newImage.getGraphics();
        gp.drawImage(resizeImage, 0, 0, null);
        gp.dispose();

        // byte 배열로 변환
        ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream();
        ImageIO.write(newImage, "jpg", compressedImageStream);
        compressedImageStream.close();

        // 이미지 저장
//        File outputFile = new File(imagePath + "/result_resized.jpg");
//        ImageIO.write(newImage, "jpg", outputFile);

        return compressedImageStream.toByteArray();
    }

    public byte[] rotate(byte[] imageToByte, int degrees) throws IOException {
        BufferedImage originalImage = readImage(imageToByte);

        // 90도 회전 변환 설정
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(degrees),
                (double) originalImage.getWidth() / 2,
                (double) originalImage.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        // 회전 적용
        BufferedImage rotatedImage = op.filter(originalImage, null);

        ByteArrayOutputStream rotatedImageStream = new ByteArrayOutputStream();
        ImageIO.write(rotatedImage, "jpg", rotatedImageStream);
        rotatedImageStream.close();

        return rotatedImageStream.toByteArray();
    }

    public byte[] orientImage(byte[] imageToByte, TiffImageMetadata metadata)
            throws IOException, ImageReadException {
        System.out.println("[이미지 정보를 가져와 회전]");

        TiffField orientationField = metadata != null
                ? metadata.findField(TiffTagConstants.TIFF_TAG_ORIENTATION)
                : null;
        int orientation = orientationField != null ? orientationField.getIntValue() : 1;
        System.out.println("orientation = " + orientation);

        BufferedImage originalImage = readImage(imageToByte);
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

        AffineTransformOp op = new AffineTransformOp(affineTransform,
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedImage = op.filter(originalImage, null);

        // AffineTransformOp에 의해 반환된 BufferedImage의 타입이 원본과 다를 수 있어 때로 약간의 색상 변화나 문제가 발생할 수 있다.
        // 새로운 BufferedImage를 생성하고 그 위에 결과 이미지를 그린다.
        BufferedImage newImage = new BufferedImage(
                rotatedImage.getWidth(),
                rotatedImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(rotatedImage, 0, 0, null);
        g.dispose();
        rotatedImage = newImage;

        // 이미지 저장
//        File outputFile = new File(imagePath + "/result_oriented.jpg");
//        ImageIO.write(rotatedImage, "jpg", outputFile);

        ByteArrayOutputStream rotatedImageStream = new ByteArrayOutputStream();
        ImageIO.write(rotatedImage, "jpg", rotatedImageStream);
        rotatedImageStream.close();

        return rotatedImageStream.toByteArray();
    }

    // 객체 인식 결과 그림 그리기
    public byte[] drawODResultOnImage(MultipartFile file, List<DetectedObject> detectedObjects, String object)
            throws IOException {
        BufferedImage originalImage = readImage(file.getBytes());

        Graphics2D g2d = originalImage.createGraphics();

        for (DetectedObject detectedObject : detectedObjects) {
            BoundingBox box = detectedObject.getBoundingBox();

            // 두께 결정
            int minDwDh = Math.min(originalImage.getWidth(), originalImage.getHeight());
            int thickness = minDwDh / 150;

            Random rand = new Random();
            g2d.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            g2d.setStroke(new BasicStroke(thickness));
            g2d.draw(new Rectangle2D.Float(box.getX(), box.getY(), box.getW(), box.getH()));

            // 사각형 왼쪽 위 글씨 쓰기
            int fontSize = minDwDh / 25;
            g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
            g2d.drawString(
                    object + " : " + String.format("%.3f", detectedObject.getConfidence()),
                    box.getX(), box.getY() - (float) (originalImage.getHeight() / 100));
        }
        g2d.dispose();

        // 이미지 저장
//        File outputFile = new File(imagePath + "/result.jpg");
//        ImageIO.write(originalImage, "jpg", outputFile);

        var outputImageStream = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", outputImageStream);
        outputImageStream.close();

        return outputImageStream.toByteArray();
    }

    public TiffImageMetadata getImageMetadata(MultipartFile file)
            throws IOException, ImageReadException {
        return readExifMetadata(file.getBytes());
    }

    private TiffImageMetadata readExifMetadata(byte[] jpegData)
            throws IOException, ImageReadException {
        ImageMetadata imageMetadata = getMetadata(jpegData);
        if (imageMetadata == null) {
            return null;
        }
        JpegImageMetadata jpegMetadata = (JpegImageMetadata) imageMetadata;
        return jpegMetadata.getExif();
    }

    private BufferedImage readImage(byte[] data) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(data));
    }
}