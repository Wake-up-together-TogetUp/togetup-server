package com.wakeUpTogetUp.togetUp.utils.image;

import com.wakeUpTogetUp.togetUp.api.mission.domain.CustomAnalysisEntity;
import com.wakeUpTogetUp.togetUp.api.mission.model.BoundingBox;
import com.wakeUpTogetUp.togetUp.api.mission.model.Coord;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.infra.aws.s3.model.ImageContentType;
import com.wakeUpTogetUp.togetUp.utils.file.FileUtil;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageDrawer extends ImageUtil {

    private final Random rand = new Random();
    private BufferedImage image;
    private Color color;
    private int thickness;
    private int fontSize;

    private void setup(MultipartFile file) {
        setOrientedImage(file);

        this.color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        int minWH = Math.min(image.getWidth(), image.getHeight());
        this.thickness = minWH / 150;
        this.fontSize = minWH / 20;
    }

    private void setOrientedImage(MultipartFile file) {
        this.image = readImage(FileUtil.getBytes(file));
        TiffImageMetadata metadata = getImageMetadata(file);
        image = ImageProcessor.orientImage(image, metadata);
    }

    public byte[] drawResultOnImage(MultipartFile file, List<CustomAnalysisEntity> entities, String object) {
        setup(file);

        drawEntityRectangles(entities);
        drawEntityLabel(entities, object);

        String format = ImageContentType
                .getByContentType(file.getContentType())
                .getExtension();



        /**
         *
         */

        System.out.println("X = " + entities.get(0).getBox().getX());
        System.out.println("Y = " + entities.get(0).getBox().getY());

        File outputFile2 = new File("src/main/resources/images/image_drawn.jpg");
        try {
            ImageIO.write(image, "jpg", outputFile2);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving the image file: " + e.getMessage());
        }

        /**
         *
         */


        try (ByteArrayOutputStream outputImageStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, format, outputImageStream);
            return outputImageStream.toByteArray();
        } catch (IOException e) {
            throw new BaseException(IMAGE_WRITE_ERROR_MESSAGE, e, Status.INVALID_IMAGE);
        }
    }

    private void drawEntityRectangles(List<CustomAnalysisEntity> entities) {
        Graphics2D g2d = image.createGraphics();

        for (CustomAnalysisEntity entity : entities) {
            BoundingBox box = entity.getBox();

            drawRectangle(g2d, box);
        }
        g2d.dispose();
    }

    private void drawEntityLabel(List<CustomAnalysisEntity> entities, String object) {
        Graphics2D g2d = image.createGraphics();

        for (CustomAnalysisEntity entity : entities) {
            BoundingBox box = entity.getBox();
            Coord coord = new Coord(box.getX(), box.getY() - (image.getHeight() / 100));
            String entityLabel = String.format("%s : %.3f", object, entity.getConfidence());

            drawString(g2d, coord, entityLabel);
        }
        g2d.dispose();
    }

    private void drawRectangle(Graphics2D g2d, BoundingBox box) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.draw(new Rectangle2D.Float(box.getX(), box.getY(), box.getW(), box.getH()));
    }

    private void drawString(Graphics2D g2d, Coord coord, String content) {
        g2d.setColor(color);
        g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
        g2d.drawString(content, coord.getX(), coord.getY());
    }
}
