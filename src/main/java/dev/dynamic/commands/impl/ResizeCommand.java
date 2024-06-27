package dev.dynamic.commands.impl;

import dev.dynamic.WDirManager;
import dev.dynamic.commands.Command;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResizeCommand extends Command {

    @Override
    public void execute(String[] args) {
        String input = args[0];
        int width = Integer.parseInt(args[1]);
        int height = Integer.parseInt(args[2]);
        String output = args[3];

        File inputFile = new File(WDirManager.getCurrentWdir() + "/" + input);
        File outputFile = new File(WDirManager.getCurrentWdir() + "/" + output);

        if (!inputFile.exists()) {
            System.err.println("Input file does not exist");
            return;
        }

        BufferedImage image = null;
        try {
            image = Imaging.getBufferedImage(inputFile);
        } catch (ImageReadException | IOException e) {
            throw new RuntimeException(e);
        }

        BufferedImage resized = new BufferedImage(width, height, image.getType());
        resized.getGraphics().drawImage(image, 0, 0, width, height, null);

        ImageFormats format = ImageFormats.valueOf(input.split("\\.")[1].toUpperCase());
        try {
            Imaging.writeImage(resized, outputFile, format, null);
        } catch (IOException | ImageWriteException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("""
            ====================================
                      Image Resizer
            ====================================
            Input File  : %s
            Dimensions  : %sx%s
            Output File : %s
            Status      : Resizing Successful
            ====================================
            """, input, width, height, output);
    }

    @Override
    public String getName() {
        return "resize";
    }

    @Override
    public String getDescription() {
        return "Resize an image to new dimensions";
    }

    @Override
    public String[] getRequiredArgs() {
        return new String[]{"input_file", "width", "height", "output_file"};
    }
}
