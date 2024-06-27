package dev.dynamic.commands.impl;

import dev.dynamic.FormatManager;
import dev.dynamic.WDirManager;
import dev.dynamic.commands.Command;

import java.io.File;
import java.io.IOException;

public class ConvertCommand extends Command {

    @Override
    public void execute(String[] args) {
        String input = args[0];
        String outputFormat = args[1];
        String output = args[2];

        String inputFilePath = WDirManager.getCurrentWdir() + "/" + input;

        boolean success;

        try {
            success = FormatManager.convertFile(inputFilePath, outputFormat, output);
        } catch (IOException e) {
            System.out.println("An error occurred while converting the file\n" + e.getMessage());
            return;
        }

        if (success) {
            System.out.printf("""
                ====================================
                          Image Converter
                ====================================
                Input File  : %s
                Output File : %s
                Status      : Conversion Successful
                ====================================
                """, input, output);
        } else {
            System.err.printf("""
                ====================================
                          Image Converter
                ====================================
                Input File  : %s
                Output File : %s
                Status      : Conversion Failed
                ====================================
                """, input, output);

            File file = new File(WDirManager.getCurrentWdir() + "/" + output);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public String getName() {
        return "convert";
    }

    @Override
    public String getDescription() {
        return "Convert an image from one format to another.";
    }

    @Override
    public String[] getRequiredArgs() {
        return new String[]{"input_file", "output_format", "output_file"};
    }
}
