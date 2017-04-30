package com.kingarchie.commands;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class FCommand {

    private static Map<String, FCommand> commandMap;
    private String command;
    private String arg0;
    private String permission;
    private int minArgLength, maxArgLength;

    public static void create() {
        commandMap = new HashMap<>();
    }

    public FCommand(String command, String arg0, String permission, int minArgLength, int maxArgLength) {
        this.command = command;
        this.arg0 = arg0;
        this.permission = permission;
        this.minArgLength = minArgLength;
        this.maxArgLength = maxArgLength;
    }

    public abstract void execute(Player player, String[] args);

    public static Map<String, FCommand> getCommandMap() {
        return commandMap;
    }

    public String getCommand() {
        return command;
    }

    public String getArg0() {
        return arg0;
    }

    public String getPermission() {
        return permission;
    }

    public int getMinArgLength() {
        return minArgLength;
    }

    public int getMaxArgLength() {
        return maxArgLength;
    }
}
