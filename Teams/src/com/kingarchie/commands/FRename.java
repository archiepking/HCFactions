package com.kingarchie.commands;

import com.kingarchie.Factions;
import com.kingarchie.player.FPlayer;
import com.kingarchie.team.Faction;
import org.bukkit.entity.Player;

public class FRename extends FCommand {

    public FRename(String command, String arg0, String permission, int minArgLength, int maxArgLength) {
        super(command, arg0, permission, minArgLength, maxArgLength);
        getCommandMap().put(arg0, this);
    }

    @Override
    public void execute(Player player, String[] args) {
        String newName = args[1];
        FPlayer fPlayer = FPlayer.get(player);
        if (Faction.factionExists(newName)) {
            player.sendMessage(Factions.get().getConfigString("errors.factionExists"));
            return;
        }

        if (!fPlayer.hasFaction()) {
            player.sendMessage(Factions.get().getConfigString("errors.noFaction"));
            return;
        }

        if (!fPlayer.getFaction().getLeader().equals(player.getUniqueId())) {
            player.sendMessage(Factions.get().getConfigString("errors.notLeader"));
            return;
        }

        for (FPlayer p : fPlayer.getFaction().getOnline()) {
            System.out.println("PLAYER");
        }

        fPlayer.getFaction().setName(newName);
        fPlayer.getFaction().sendMessage(Factions.get().getConfigString("factions.rename").replaceAll("%name%", newName));
    }
}
