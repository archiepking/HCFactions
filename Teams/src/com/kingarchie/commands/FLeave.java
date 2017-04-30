package com.kingarchie.commands;

import com.kingarchie.Factions;
import com.kingarchie.player.FPlayer;
import org.bukkit.entity.Player;


public class FLeave extends FCommand {

    public FLeave(String command, String arg0, String permission, int minArgLength, int maxArgLength) {
        super(command, arg0, permission, minArgLength, maxArgLength);
        getCommandMap().put(arg0, this);
    }

    @Override
    public void execute(Player player, String[] args) {
        FPlayer fPlayer = FPlayer.get(player);
        if (!fPlayer.hasFaction()) {
            player.sendMessage(Factions.get().getConfigString("errors.noFaction"));
            return;
        }

        if (fPlayer.getFaction().getLeader() == player.getUniqueId()) {
            player.sendMessage(Factions.get().getConfigString("errors.cannotLeave"));
            return;
        }

        fPlayer.getFaction().sendMessage(Factions.get().getConfigString("factions.leftFaction")
                    .replaceAll("%name%", player.getName()));
        fPlayer.setFaction(null);
    }
}
