package dev.dynamic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class FormatManager {

    public static boolean convertFile(String inputFilePath, String outputFormat, String outputFile) throws IOException {
        BufferedImage image = ImageIO.read(new FileInputStream(inputFilePath));
        System.out.println(WDirManager.getCurrentWdir() + "/" + outputFile);

        if (Objects.equals(outputFormat, "jpg") || Objects.equals(outputFormat, "jpeg")) {
            BufferedImage outputImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            outputImage.createGraphics().drawImage(image, 0, 0, null);
            image = outputImage;
        }

        return ImageIO.write(image, outputFormat, new FileOutputStream(WDirManager.getCurrentWdir() + "/" + outputFile));
    }
}
