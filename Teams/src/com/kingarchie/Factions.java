package com.kingarchie;

import com.kingarchie.commands.*;
import com.kingarchie.events.ChatEvent;
import com.kingarchie.events.JoinQuitEvent;
import com.kingarchie.player.FPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Factions extends JavaPlugin {

    private static Factions plugin;

    private void createListeners() {
        new ChatEvent(this);
        new JoinQuitEvent(this);
    }

    private void setupCommands() {
        FCommand.create();
        new FCreate("faction", "create", "faction.create", 2, 2);
        new FLeave("faction", "leave", "faction.leave", 1, 1);
        new FHelp("faction", "help", "faction.help", 1, 1);
        new FDisband("faction", "disband", "faction.disband", 1, 1);
        new FRename("faction", "rename", "faction.rename", 2, 2);
        new FPastFaction("faction", "pf", "faction.pf", 1, 2);
        new FClaim("faction", "claim", "faction.claim", 2, 2);
        new FElo("elo", "", "player.elo", 0, 1);
    }

    @Override
    public void onEnable() {
        plugin = this;
        FPlayer.reload();
        saveDefaultConfig();
        createListeners();
        setupCommands();
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public String getConfigString(String path) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path));
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("faction")) {
            if (sender instanceof ConsoleCommandSender) {
                getLogger().info(ChatColor.RED + "This is not a console command.");
                return true;
            }

            Player player = (Player) sender;

            if (args.length == 0) {
                FCommand.getCommandMap().get("help").execute(player, args);
                return true;
            }

            if (!FCommand.getCommandMap().containsKey(args[0])) {
                player.sendMessage(Factions.get().getConfigString("errors.unrecognised")
                            .replaceAll("%command%", getArgString(command, args)));
                return true;
            }

            FCommand cmd = FCommand.getCommandMap().get(args[0]);
            if (args.length < cmd.getMinArgLength()) {
                player.sendMessage(Factions.get().getConfigString("errors.argsLength")
                            .replaceAll("%command%", getArgString(command, args))
                            .replaceAll("%required%", String.valueOf(cmd.getMinArgLength())));
                return true;
            }

            if (args.length > cmd.getMaxArgLength()) {
                player.sendMessage(Factions.get().getConfigString("errors.argsLength")
                        .replaceAll("%command%", getArgString(command, args))
                        .replaceAll("%required%", String.valueOf(cmd.getMaxArgLength())));
                return true;
            }

            if (!player.hasPermission(cmd.getPermission())) {
                player.sendMessage(Factions.get().getConfigString("errors.noPermission")
                        .replaceAll("%permission%", cmd.getPermission()));
                return true;
            }

            cmd.execute(player, args);
        }

        if (command.getName().equalsIgnoreCase("elo")) {
            if (sender instanceof ConsoleCommandSender) {
                getLogger().info(ChatColor.RED + "This is not a console command.");
                return true;
            }

            Player player = (Player) sender;
            if (!FCommand.getCommandMap().containsKey("elo")) {
                player.sendMessage(Factions.get().getConfigString("errors.unrecognised")
                        .replaceAll("%command%", getArgString(command, args)));
                return true;
            }

            FCommand cmd = FCommand.getCommandMap().get("elo");
            if (args.length < cmd.getMinArgLength()) {
                player.sendMessage(Factions.get().getConfigString("errors.argsLength")
                        .replaceAll("%command%", getArgString(command, args))
                        .replaceAll("%required%", String.valueOf(cmd.getMinArgLength())));
                return true;
            }

            if (args.length > cmd.getMaxArgLength()) {
                player.sendMessage(Factions.get().getConfigString("errors.argsLength")
                        .replaceAll("%command%", getArgString(command, args))
                        .replaceAll("%required%", String.valueOf(cmd.getMaxArgLength())));
                return true;
            }

            if (!player.hasPermission(cmd.getPermission())) {
                player.sendMessage(Factions.get().getConfigString("errors.noPermission")
                        .replaceAll("%permission%", cmd.getPermission()));
                return true;
            }

            cmd.execute(player, args);

        }

        return true;
    }

    private String getArgString(Command command, String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.append(command.getName() + " ");
        for (String s : args) {
            builder.append(s + " ");
        }

        return builder.toString();
    }

    public static Factions get() {
        return plugin;
    }
}
