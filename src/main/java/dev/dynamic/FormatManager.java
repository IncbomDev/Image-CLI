package dev.dynamic;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FormatManager {

    public static ImageFormats getImageFormat(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        try {
            return ImageFormats.valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean convertFile(String inputFilePath, String outputFile, String outputFormat) throws IOException, ImageReadException, ImageWriteException {
        File inputFile = new File(inputFilePath);
        File outputF = new File(outputFile);

        outputF.getParentFile().mkdirs();

        BufferedImage image = Imaging.getBufferedImage(inputFile);

        ImageFormats format = getImageFormat(outputFormat);

        if (format == null) {
            System.err.println("Invalid output format");
            return false;
        }

        Imaging.writeImage(image, outputF, getImageFormat(inputFile.getName()), null);
        return true;
    }

}
