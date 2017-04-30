package com.kingarchie.commands;

import com.kingarchie.Factions;
import com.kingarchie.player.FPlayer;
import com.kingarchie.team.location.Location2D;
import com.kingarchie.team.location.Region;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FClaim extends FCommand {

    public FClaim(String command, String arg0, String permission, int minArgLength, int maxArgLength) {
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

        if (!fPlayer.getFaction().getLeader().equals(fPlayer.getPlayerID()) || !fPlayer.getFaction().getPlayers().get(fPlayer.getPlayerID())) {
            player.sendMessage(Factions.get().getConfigString("errors.playerIsMember"));
            return;
        }

        if (fPlayer.getFaction().getRegion() == null) {
            player.sendMessage(Factions.get().getConfigString("errors.alreadyClaimed"));
            return;
        }

        String rad = args[1];
        if (!StringUtils.isNumeric(rad)) {
            player.sendMessage(Factions.get().getConfigString("errors.mustBeNumeric"));
            return;
        }

        int half = Integer.parseInt(rad) / 2;
        Location2D loc = new Location2D(player.getWorld().getName(), player.getLocation().getBlockX() + half, (int) (player.getLocation().getBlockZ() + half));
        Location2D loc2 = new Location2D(player.getWorld().getName(), player.getLocation().getBlockX() - half, (int) (player.getLocation().getBlockZ() - half));
        // unfinished
    }
}
