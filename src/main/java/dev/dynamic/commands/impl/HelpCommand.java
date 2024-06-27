package dev.dynamic.commands.impl;

import dev.dynamic.commands.Command;
import dev.dynamic.commands.Registry;

public class HelpCommand extends Command {

    @Override
    public void execute(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (Command command : Registry.commands.values()) {
            builder.append(command.getName()).append(" - ").append(command.getDescription()).append("\n");
        }

        System.out.printf("""
                ====================================
                            Help Command
                ====================================
                %s
                ====================================
                """, builder.toString().trim());
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Help command";
    }

    @Override
    public String[] getRequiredArgs() {
        return new String[0];
    }
}
