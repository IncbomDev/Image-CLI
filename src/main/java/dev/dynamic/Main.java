package dev.dynamic;

import dev.dynamic.commands.Command;
import dev.dynamic.commands.Registry;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        Terminal terminal = TerminalBuilder.builder()
            .system(true)
            .build();

        LineReader reader = LineReaderBuilder.builder()
            .terminal(terminal)
            .build();

        WDirManager.setWdir(WDirManager.getExecutionDirectory());

        Registry.registerAllCommands();

        System.out.printf("""
                ====================================
                       Welcome to Image Converter
                ====================================
                An efficient tool for all your image processing needs!
                       \s
                With Image Converter, you can easily:
                1. Convert image formats (e.g., JPEG to PNG, BMP to GIF).
                2. Resize images to specified dimensions.
                3. Crop images to focus on important areas.
                4. Compress images to save space.
                5. Batch convert multiple images effortlessly.
                       \s
                Set your working directory (defaults to the current directory) using:
                   set_wdir <directory_path>
                       \s
                Current working directory: %s
                       \s
                Type 'help' to see a list of available commands and get started.
                       \s
                Thank you for choosing Image Converter CLI!
                ====================================
                %n""", WDirManager.getCurrentWdir());

        while (true) {
            String line = reader.readLine("> ");
            if (line == null || line.equalsIgnoreCase("exit")) {
                break;
            }

            boolean found = false;
            for (String command : Registry.commands.keySet()) {
                if (line.startsWith(command)) {
                    found = true;
                    Command cmd = Registry.getCommand(command);
                    String[] inputArgs = line.substring(command.length()).trim().split("\\s+");

                    if (cmd.getRequiredArgs().length > 0 && (inputArgs.length != cmd.getRequiredArgs().length || inputArgs[0].isEmpty())) {
                        System.out.println("Invalid number of arguments. Expected: " + String.join(", ", cmd.getRequiredArgs()));
                        continue;
                    }
                    cmd.execute(inputArgs);
                    break;
                }
            }

            if (!found) {
                System.out.println("Command not found. Type 'help' to see a list of available commands.");
            }

            reader.getHistory().add(line);
        }
    }
}