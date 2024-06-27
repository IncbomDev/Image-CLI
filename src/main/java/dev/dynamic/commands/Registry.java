package dev.dynamic.commands;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Registry {
    public static Map<String, Command> commands = new HashMap<>();

    public static void register(String name, Command command) {
        commands.put(name, command);
    }

    public static Command getCommand(String name) {
        return commands.get(name);
    }

    public static void registerAllCommands() {
        Reflections reflections = new Reflections("dev.dynamic.commands");

        Set<Class<? extends Command>> commands = reflections.getSubTypesOf(Command.class);

        for (Class<? extends Command> command : commands) {
            try {
                Command instance = command.getDeclaredConstructor().newInstance();
                Registry.register(instance.getName(), instance);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("Failed to register command " + command.getName(), e);
            }
        }
    }
}
