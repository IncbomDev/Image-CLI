package dev.dynamic.commands.impl;

import dev.dynamic.WDirManager;
import dev.dynamic.commands.Command;

public class SetWdirCommand extends Command {

    @Override
    public void execute(String[] args) {
        String wdir = args[0];
        WDirManager.setWdir(wdir);
        System.out.printf("""
                ====================================
                       Set Working Directory
                ====================================
                New Working Directory: %s
                Status                : Directory Set Successfully
                ====================================
                """, wdir);
    }

    @Override
    public String getName() {
        return "setwdir";
    }

    @Override
    public String getDescription() {
        return "Set the wdir to a specific directory";
    }

    @Override
    public String[] getRequiredArgs() {
        return new String[]{"wdir"};
    }
}
