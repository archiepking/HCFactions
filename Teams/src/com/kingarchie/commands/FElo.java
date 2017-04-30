package com.kingarchie.commands;

import com.kingarchie.Factions;
import com.kingarchie.player.FPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FElo extends FCommand {

    public FElo(String command, String arg0, String permission, int minArgLength, int maxArgLength) {
        super(command, arg0, permission, minArgLength, maxArgLength);
        getCommandMap().put(command, this);
    }

    @Override
    public void execute(Player player, String[] args) {
        String message;
        if (args.length == 0) {
            message = Factions.get().getConfigString("player.elo");
            int elo = FPlayer.get(player).getElo();
            if (Factions.get().getConfig().getBoolean("player.eloPosNegColour")) {
                System.out.println("1");
                if (elo < 1) {
                    message = message.replaceAll("%elo%", ChatColor.RED + String.valueOf(elo));
                } else {
                    message = message.replaceAll("%elo%", ChatColor.GREEN + String.valueOf(elo));
                }
            } else {
                message = message.replaceAll("%elo%", String.valueOf(elo));
            }

            player.sendMessage(message);
        } else {
            message = Factions.get().getConfigString("player.otherElo");
            String target = args[0];
            if (Bukkit.getServer().getPlayer(target) != null) {
                int elo = FPlayer.get(Bukkit.getServer().getPlayer(target)).getElo();
                if (Factions.get().getConfig().getBoolean("player.eloPosNegColour")) {
                    if (elo < 1) {
                        message = message.replaceAll("%elo%", ChatColor.RED + String.valueOf(elo));
                    } else {
                        message = message.replaceAll("%elo%", ChatColor.GREEN + String.valueOf(elo));
                    }
                } else {
                    message = message.replaceAll("%elo%", String.valueOf(elo));
                }

                message = message.replaceAll("%name%", target);
                player.sendMessage(message);
            } else {
                player.sendMessage(Factions.get().getConfigString("errors.playerNotFound"));
            }
        }
    }
}
