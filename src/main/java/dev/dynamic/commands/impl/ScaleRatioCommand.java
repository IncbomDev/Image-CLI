package dev.dynamic.commands.impl;

import dev.dynamic.WDirManager;
import dev.dynamic.commands.Command;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScaleRatioCommand extends Command {

    @Override
    public void execute(String[] args) {
        String input = args[0];
        double scale = Double.parseDouble(args[1]);
        String output = args[2];

        File inputFile = new File(WDirManager.getCurrentWdir() + "/" + input);

        if (!inputFile.exists()) {
            System.err.println("Input file does not exist");
            return;
        }

        Image image = null;
        try {
            image = Imaging.getBufferedImage(inputFile);
        } catch (ImageReadException | IOException e) {
            throw new RuntimeException(e);
        }

        int width = (int) (image.getWidth(null) * scale);
        int height = (int) (image.getHeight(null) * scale);

        Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(scaled, 0, 0, null);
        g2d.dispose();

        File outputFile = new File(WDirManager.getCurrentWdir() + "/" + output);

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
            Scale Ratio : %sx
            Output File : %s
            Status      : Resizing Successful
            ====================================
            """, input, scale, output);
    }

    @Override
    public String getName() {
        return "scale_ratio";
    }

    @Override
    public String getDescription() {
        return "Upscale/downscale your image by a specific scale factor (keeping the aspect ratio)";
    }

    @Override
    public String[] getRequiredArgs() {
        return new String[]{"input_file", "scale_multiplier", "output_file"};
    }
}
