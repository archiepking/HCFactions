package com.kingarchie.commands;

import com.kingarchie.Factions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FHelp extends FCommand {

    public FHelp(String command, String arg0, String permission, int minArgLength, int maxArgLength) {
        super(command, arg0, permission, minArgLength, maxArgLength);
        getCommandMap().put(arg0, this);
    }

    @Override
    public void execute(Player player, String[] args) {
        for (String string : Factions.get().getConfig().getStringList("factions.help")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
        }
    }
}
