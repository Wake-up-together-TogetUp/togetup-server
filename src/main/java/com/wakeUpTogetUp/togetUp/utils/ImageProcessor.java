package com.wakeUpTogetUp.togetUp.utils;

import static org.apache.commons.imaging.Imaging.getMetadata;

import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Vertex;
import com.wakeUpTogetUp.togetUp.api.mission.model.BoundingBox;
import com.wakeUpTogetUp.togetUp.api.mission.domain.CustomAnalysisEntity;
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
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageProcessor {

    public byte[] compress(byte[] imageToByte, float quality) throws IOException {
        BufferedImage originalImage = readImage(imageToByte);

        ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream();
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

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

        return compressedImageStream.toByteArray();
    }

    public byte[] resize(byte[] imageToByte, double ratio) throws IOException {
        BufferedImage originalImage = readImage(imageToByte);

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

    public byte[] rotate(byte[] imageToByte, int degrees) throws IOException {
        BufferedImage originalImage = readImage(imageToByte);

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

    public byte[] orientImage(byte[] imageToByte, TiffImageMetadata metadata)
            throws IOException, ImageReadException {
        TiffField orientationField = metadata != null
                ? metadata.findField(TiffTagConstants.TIFF_TAG_ORIENTATION)
                : null;
        int orientation = orientationField != null ? orientationField.getIntValue() : 1;

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

        AffineTransformOp op =
                new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedImage = op.filter(originalImage, null);

        // AffineTransformOp에 의해 반환된 BufferedImage의 타입이 원본과 다를 수 있어 때로 약간의 색상 변화나 문제가 발생할 수 있다.
        // 새로운 BufferedImage를 생성하고 그 위에 결과 이미지를 그린다.
        BufferedImage newImage =
                new BufferedImage(rotatedImage.getWidth(), rotatedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = newImage.createGraphics();
        g.drawImage(rotatedImage, 0, 0, null);
        g.dispose();

        ByteArrayOutputStream rotatedImageStream = new ByteArrayOutputStream();
        ImageIO.write(newImage, "jpg", rotatedImageStream);
        rotatedImageStream.close();

        return rotatedImageStream.toByteArray();
    }


    // TODO : 인식 결과 그림 그리기 하나로 통일하기
    public byte[] drawODResultOnImage(MultipartFile file, List<CustomAnalysisEntity> entities)
            throws IOException {
        BufferedImage originalImage = readImage(file.getBytes());
        Graphics2D g2d = originalImage.createGraphics();

        for (CustomAnalysisEntity entity : entities) {
            BoundingBox box = entity.getBox();

            int minDwDh = Math.min(originalImage.getWidth(), originalImage.getHeight());
            
            int thickness = minDwDh / 150;
            Random rand = new Random();
            g2d.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            g2d.setStroke(new BasicStroke(thickness));
            g2d.draw(new Rectangle2D.Float(box.getX(), box.getY(), box.getW(), box.getH()));

            int fontSize = minDwDh / 25;
            g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
            g2d.drawString(
                    entity.getTargetName() + " : " + String.format("%.3f", entity.getConfidence()),
                    box.getX(), box.getY() - (float) (originalImage.getHeight() / 100));
        }
        g2d.dispose();

        var outputImageStream = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", outputImageStream);
        outputImageStream.close();

        return outputImageStream.toByteArray();
    }

    public byte[] drawFRResultOnImage(MultipartFile file, List<FaceAnnotation> faceAnnotations, String object)
            throws IOException {
        BufferedImage originalImage = readImage(file.getBytes());

        Graphics2D g2d = originalImage.createGraphics();

        for (FaceAnnotation face : faceAnnotations) {
            List<Vertex> coord = face.getBoundingPoly().getVerticesList();

            int x1 = coord.get(0).getX();
            int y1 = coord.get(0).getY();
            int x2 = coord.get(2).getX();
            int y2 = coord.get(2).getY();

            int width = x2 - x1;
            int height = y2 - y1;

            int minDwDh = Math.min(originalImage.getWidth(), originalImage.getHeight());
            int thickness = minDwDh / 150;

            Random rand = new Random();
            g2d.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            g2d.setStroke(new BasicStroke(thickness));
            g2d.draw(new Rectangle2D.Float(x1, y1, width, height));

            int fontSize = minDwDh / 25;
            g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
            g2d.drawString(
                    object,
                    x1, y1 - (float) (originalImage.getHeight() / 100));
        }
        g2d.dispose();

        var outputImageStream = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", outputImageStream);
        outputImageStream.close();

        return outputImageStream.toByteArray();
    }

    private BufferedImage readImage(byte[] data) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(data));
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
}