package com.kingarchie.commands;

import com.kingarchie.Factions;
import com.kingarchie.player.FPlayer;
import com.kingarchie.team.Faction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FCreate extends FCommand {

    public FCreate(String command, String arg0, String permission, int minArgLength, int maxArgLength) {
        super(command, arg0, permission, minArgLength, maxArgLength);
        getCommandMap().put(arg0, this);
    }

    @Override
    public void execute(Player player, String[] args) {
        String factionName = args[1];
        FPlayer fPlayer = FPlayer.get(player);
        if (Faction.factionExists(factionName)) {
            player.sendMessage(Factions.get().getConfigString("errors.factionExists"));
            return;
        }

        if (fPlayer.hasFaction()) {
            player.sendMessage(Factions.get().getConfigString("errors.inFaction"));
            return;
        }

        if (!(factionName.length() > 2 && factionName.length() <= Factions.get().getConfig().getInt("factions.nameSize"))) {
            player.sendMessage(Factions.get().getConfigString("errors.factionNameLength"));
            return;
        }

        if (!isAlphaNumeric(factionName)) {
            player.sendMessage(Factions.get().getConfigString("errors.factionAlphaNumeric"));
            return;
        }

        fPlayer.setFaction(new Faction(factionName, player.getUniqueId()));
        fPlayer.getFaction().setElo(fPlayer.getElo());
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(Factions.get().getConfigString("factions.factionCreated")
                    .replaceAll("%faction%", factionName)
                    .replaceAll("%name%", player.getName()));
        }
    }

    private boolean isAlphaNumeric(String s) {
        for (Character c : s.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }

        return true;
    }
}
