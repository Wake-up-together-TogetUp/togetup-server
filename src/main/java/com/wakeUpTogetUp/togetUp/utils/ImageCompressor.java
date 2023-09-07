package com.wakeUpTogetUp.togetUp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.IIOImage;
import java.awt.image.BufferedImage;

@Component
public class ImageCompressor {
    public byte[] compressImage(MultipartFile file, float quality) throws Exception {
        // Convert MultipartFile to BufferedImage
        BufferedImage originalImage;

        // Use try-with-resources to ensure the InputStream is closed
        try (var is = file.getInputStream()) {
            originalImage = ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("Error reading the image: " + e.getMessage());
            throw e;
        }

        // Compress the image based on the format
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

        if (writers.hasNext()) {
            ImageWriter writer = writers.next();
            ImageWriteParam param = writer.getDefaultWriteParam();

            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality); // 0.0 to 1.0
            }

            writer.setOutput(ImageIO.createImageOutputStream(baos));
            writer.write(null, new IIOImage(originalImage, null, null), param);
            writer.dispose();
        } else {
            throw new IllegalStateException("No writers found");
        }

//        // Save the compressed image to the specified outputPath
//        try (var fos = new FileOutputStream(new File(savePicPath))) {
//            fos.write(baos.toByteArray());
//        }

        double sizeInMB = (double) baos.size() / (1024 * 1024);
        System.out.println("sizeInMB = " + sizeInMB);

        return baos.toByteArray();
    }
}
