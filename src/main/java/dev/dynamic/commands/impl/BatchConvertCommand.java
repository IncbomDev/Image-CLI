package dev.dynamic.commands.impl;

import dev.dynamic.FormatManager;
import dev.dynamic.commands.Command;
import org.apache.commons.imaging.ImageFormats;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BatchConvertCommand extends Command {

    @Override
    public void execute(String[] args) {
        String inputDirectory = args[0];
        String outputFormat = args[1];
        String outputDirectory = args[2];

        File directory = new File(inputDirectory);
        File outputDir = new File(outputDirectory);

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File[] files = directory.listFiles();

        List<File> finalFiles = new ArrayList<>();

        for (File iFile : files) {
            String ending = iFile.getName().substring(iFile.getName().lastIndexOf(".") + 1);
            for (ImageFormats formats : ImageFormats.values()) {
                if (formats.equals(ImageFormats.UNKNOWN)) continue;
                if (formats.name().equalsIgnoreCase(ending)) {
                    finalFiles.add(iFile);
                }
            }
        }

        System.out.printf("""
                ====================================
                        Batch Image Converter
                ====================================
                Input File  : %s
                Output File : %s
                Status: Converting...
                ====================================
                """, inputDirectory, outputDirectory);

        int successFiles = 0;
        for (File file : finalFiles) {
            String fileNameWithoutExtension = file.getName().substring(0, file.getName().lastIndexOf("."));
            String output = outputDirectory + "/" + fileNameWithoutExtension + "." + outputFormat;
            String input = file.getAbsolutePath();

            boolean success = false;
            try {
                success = FormatManager.convertFile(input, output, outputFormat);
            } catch (Exception e) {
                System.out.println("An error occurred while converting the file" + e.getMessage());
            }

            if (success) {
                successFiles++;
            } else {
                File file1 = new File(output);
                if (file1.exists()) {
                    file1.delete();
                }
            }
        }

        // Check if more than 90% of the files were successfully converted
        if (successFiles >= (finalFiles.size() * 0.9)) {
            System.out.printf("""
                        ====================================
                                Batch Image Converter
                        ====================================
                        Input Directory  : %s
                        Output Format    : %s
                        Output Directory : %s
                        Status           : Batch Conversion Successful (More than 90%%)
                        Info:            : %d/%d files converted successfully
                        ====================================
                        """, inputDirectory, outputFormat.toUpperCase(), outputDirectory, successFiles, finalFiles.size());
        } else {
            System.err.printf("""
                        ====================================
                                Batch Image Converter
                        ====================================
                        Input Directory  : %s
                        Output Format    : %s
                        Output Directory : %s
                        Status           : Batch Conversion Failed (Less than 90%%)
                        Info:            : %d/%d files converted successfully
                        ====================================
                        """, inputDirectory, outputFormat.toUpperCase(), outputDirectory, successFiles, finalFiles.size());
        }
    }

    @Override
    public String getName() {
        return "batch_convert";
    }

    @Override
    public String getDescription() {
        return "Convert an entire directory of files";
    }

    @Override
    public String[] getRequiredArgs() {
        return new String[]{"input_directory (Not based on WDIR)", "output_format", "output_directory"};
    }
}
