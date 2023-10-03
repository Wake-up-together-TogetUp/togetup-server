package com.wakeUpTogetUp.togetUp.utils.ImageProcessing;

import static org.apache.commons.imaging.Imaging.*;

import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.FaceRecognitionRes;
import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.FaceRecognitionRes.Face;
import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.FaceRecognitionRes.Face.EmotionValue;
import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.FaceRecognitionRes.Info.Size;
import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.ObjectDetectionRes;
import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.ObjectDetectionRes.Prediction;
import com.wakeUpTogetUp.togetUp.utils.ImageProcessing.vo.ImageDrawResult;
import com.wakeUpTogetUp.togetUp.utils.ImageProcessing.vo.ImageProcessedResult;
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
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
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
        BufferedImage compressedImage = ImageIO.read(bis);

        // 이미지 저장
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

    public ImageProcessedResult orientImage(byte[] imageToByte, TiffImageMetadata metadata)
            throws IOException, ImageReadException {
        System.out.println("[이미지 정보 가져와서 회전]");

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
        // 새로운 BufferedImage를 생성하고 그 위에 결과 이미지를 그리는 것을 시도해볼 수 있다.
        BufferedImage newImage = new BufferedImage(rotatedImage.getWidth(),
                rotatedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
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

        return new ImageProcessedResult(rotatedImageStream.toByteArray(),
                new Size(rotatedImage.getWidth(), rotatedImage.getHeight()));
    }

    // 객체 인식 결과 그림 그리기
    public ImageDrawResult drawODResultOnImage(MultipartFile file,
            ObjectDetectionRes objectDetectionRes,
            String object) throws IOException, ImageReadException {
        // TODO : 시계, 사람 상반신의 경우 2번 인식되므로 완전 겹치면 안 그리는 로직을 추가해야 될것같음

        System.out.println("[결과 그리기]");
        long startTime = System.currentTimeMillis();

        // 결과 가져오기
        Prediction pred = objectDetectionRes.getPredictions().get(0);

        // MultipartFile -> BufferedImage
        TiffImageMetadata metadata = getImageMetadata(file);
        BufferedImage originalImage = readImage(orientImage(file.getBytes(), metadata).getResult());

        // 그래픽 객체 생성
        Graphics2D g2d = originalImage.createGraphics();

        for (int i = 0; i < pred.getNum_detections(); i++) {
            if (pred.getDetection_names().get(i).equals(object)) {
                // 좌표 값
                float y1 = pred.getDetection_boxes().get(i).get(0).floatValue()
                        * originalImage.getHeight();
                float x1 = pred.getDetection_boxes().get(i).get(1).floatValue()
                        * originalImage.getWidth();
                float y2 = pred.getDetection_boxes().get(i).get(2).floatValue()
                        * originalImage.getHeight();
                float x2 = pred.getDetection_boxes().get(i).get(3).floatValue()
                        * originalImage.getWidth();

                // 사각형 그리기
                int minDwDh = Math.min(originalImage.getWidth(), originalImage.getHeight());
                int thickness = minDwDh / 150;

                Random rand = new Random();
                g2d.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
//                g2d.setColor(Color.decode("#4B89DC"));
                g2d.setStroke(new BasicStroke(thickness));
                g2d.draw(new Rectangle2D.Float(x1, y1, x2 - x1, y2 - y1));

                // 사각형 왼쪽 위에 글씨 쓰기
                int fontSize = (int) minDwDh / 25;
                g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
                g2d.drawString(
                        object + " : " + String.format("%.3f", pred.getDetection_scores().get(i)),
                        x1, y1 - (float) (originalImage.getHeight() / 100));
            }
        }

        // 그래픽 리소스 해제
        g2d.dispose();

        // 이미지 저장
//        File outputFile = new File(imagePath + "/result.jpg");
//        ImageIO.write(originalImage, "jpg", outputFile);

        // BufferedImage를 ByteArrayOutputStream으로 변환
        var drawedImageStream = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", drawedImageStream);
        drawedImageStream.close();

        // 걸린 시간 계산
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("결과 그리는데 걸린 시간 : " + timeElapsed);

        return new ImageDrawResult(
                new ByteArrayInputStream(drawedImageStream.toByteArray()),
                drawedImageStream.size());
    }

    // 표정 인식 결과 그림 그리기
    public ImageDrawResult drawODResultOnImage(MultipartFile file,
            FaceRecognitionRes faceRecognitionRes,
            String object) throws IOException, ImageReadException {
        System.out.println("[결과 그리기]");
        long startTime = System.currentTimeMillis();

        // MultipartFile -> BufferedImage
        TiffImageMetadata metadata = getImageMetadata(file);
        BufferedImage originalImage = readImage(orientImage(file.getBytes(), metadata).getResult());

        // 그래픽 객체 생성
        Graphics2D g2d = originalImage.createGraphics();

        float sizeRatio = (float) (1.0 * originalImage.getWidth() /
                faceRecognitionRes.getOriginalImageProcessedResult().getSize().getWidth());

        for (Face face : faceRecognitionRes.getFaces()) {
            if (face.getEmotion().getValue() == EmotionValue.valueOf(object)) {
                // 좌표 값
                float x = face.getRoi().getX() * sizeRatio;
                float y = face.getRoi().getY() * sizeRatio;
                float width = face.getRoi().getWidth() * sizeRatio;
                float height = face.getRoi().getHeight() * sizeRatio;

                System.out.println("x = " + x);
                System.out.println("y = " + y);
                System.out.println("width = " + width);
                System.out.println("height = " + height);

                // 비율 정보
                int minDwDh = Math.min(originalImage.getWidth(), originalImage.getHeight());
                int thickness = minDwDh / 200;

                // 사각형 그리기
                Random rand = new Random();
                g2d.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
//                g2d.setColor(Color.decode("#4B89DC"));
                g2d.setStroke(new BasicStroke(thickness));
                g2d.draw(new Rectangle2D.Float(x, y, width, height));

                // 사각형 왼쪽 위에 글씨 쓰기
                int fontSize = (int) minDwDh / 30;
                g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
                g2d.drawString(
                        object + " : " + String.format("%.3f", face.getEmotion().getConfidence()),
                        x, y - (float) (originalImage.getHeight() / 100));
            }
        }
        // 그래픽 리소스 해제
        g2d.dispose();

        // 이미지 저장
        File outputFile = new File(imagePath + "/result.jpg");
        ImageIO.write(originalImage, "jpg", outputFile);

        // BufferedImage를 ByteArrayOutputStream으로 변환
        ByteArrayOutputStream drawedImageStream = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", drawedImageStream);
        drawedImageStream.close();

        // 걸린 시간 계산
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("결과 그리는데 걸린 시간 : " + timeElapsed);

        return new ImageDrawResult(
                new ByteArrayInputStream(drawedImageStream.toByteArray()),
                drawedImageStream.size());
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