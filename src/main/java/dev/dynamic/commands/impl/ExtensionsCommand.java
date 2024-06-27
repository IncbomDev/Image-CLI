package dev.dynamic.commands.impl;

import dev.dynamic.commands.Command;
import org.apache.commons.imaging.ImageFormats;

public class ExtensionsCommand extends Command {
    @Override
    public void execute(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (ImageFormats formats : ImageFormats.values()) {
            if (formats.equals(ImageFormats.UNKNOWN)) continue;
            builder.append(formats.name()).append("\n");
        }

        System.out.printf("""
                ====================================
                            File Formats
                ====================================
                %s
                ====================================
                """, builder.toString().trim());
    }

    @Override
    public String getName() {
        return "extensions";
    }

    @Override
    public String getDescription() {
        return "Get all the extensions this program supports";
    }

    @Override
    public String[] getRequiredArgs() {
        return new String[0];
    }
}
