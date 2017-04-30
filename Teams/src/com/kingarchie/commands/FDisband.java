package com.kingarchie.commands;

import com.kingarchie.Factions;
import com.kingarchie.player.FPlayer;
import com.kingarchie.team.Faction;
import org.bukkit.entity.Player;

public class FDisband extends FCommand {

    public FDisband(String command, String arg0, String permission, int minArgLength, int maxArgLength) {
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

        if (fPlayer.getFaction().getLeader() != player.getUniqueId()) {
            player.sendMessage(Factions.get().getConfigString("errors.cannotDisband"));
            return;
        }

        Faction.getTeams().remove(fPlayer.getFaction());
        fPlayer.getFaction().sendMessage(Factions.get().getConfigString("factions.disbanded")
                .replaceAll("%name%", player.getName()));

        Faction.getDisbandedFactions().put(fPlayer.getFaction().getTeamID(), fPlayer.getFaction().getName());
        for (FPlayer p : fPlayer.getFaction().getOnline()) {
            p.removeFaction();
        }
    }

}
