package com.kingarchie.commands;

import com.kingarchie.Factions;
import com.kingarchie.player.FPlayer;
import com.kingarchie.team.Faction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.Map;
import java.util.UUID;

public class FPastFaction extends FCommand {

    public FPastFaction(String command, String arg0, String permission, int minArgLength, int maxArgLength) {
        super(command, arg0, permission, minArgLength, maxArgLength);
        getCommandMap().put(arg0, this);
    }


    @Override
    public void execute(Player player, String[] args) {
        StringBuilder builder = null;
        String target = player.getName();
        if (args.length == 2) {
            target = args[1];
            if (Bukkit.getServer().getPlayer(target) == null) {
                player.sendMessage(Factions.get().getConfigString("errors.playerNotFound"));
                return;
            }

            builder = getString(Bukkit.getServer().getPlayer(target));
        } else {
            builder = getString(player);
        }

        player.sendMessage(Factions.get().getConfigString("factions.pastFactionMessage")
                    .replaceAll("%name%", target)
                    .replaceAll("%pf%", builder.toString()));
    }

    private StringBuilder getString(Player player) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<UUID, Long> map : FPlayer.get(player).getPastFactions().entrySet()) {
            Faction fac = Faction.getFactionByID(map.getKey());
            if (fac == null) {
                builder.append(Factions.get().getConfigString("factions.pastFaction")
                        .replaceAll("%fname%", Faction.getDisbandedFactions().get(map.getKey()))
                        .replaceAll("%time%", String.valueOf(map.getValue() / 60000)) + " ");

            } else {
                builder.append(Factions.get().getConfigString("factions.pastFaction")
                        .replaceAll("%fname%", fac.getName())
                        .replaceAll("%time%", String.valueOf(map.getValue() + (System.currentTimeMillis() - FPlayer.get(player).getTime()) / 60000)) + " ");
            }
        }

        return builder;
    }
}
