package dev.dynamic.commands;

public abstract class Command {

    public abstract void execute(String[] args);
    public abstract String getName();
    public abstract String getDescription();
    public abstract String[] getRequiredArgs();
}
