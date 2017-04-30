package com.kingarchie.player;

import com.kingarchie.Factions;
import com.kingarchie.team.Faction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class FPlayer {

    private static Map<UUID, FPlayer> players = new HashMap<>();

    private UUID playerID;

    private Faction faction;

    private LinkedHashMap<UUID, Long> pastFactions = new LinkedHashMap<>();

    private boolean publicChat = true;

    private int elo;

    private long time = 0;

    public FPlayer(Player player) {
        this.players.put(player.getUniqueId(), this);
        this.playerID = player.getUniqueId();
        for (Faction f : Faction.getTeams()) {
            if (f.getLeader().equals(player.getUniqueId()) || f.getPlayers().containsKey(player.getUniqueId())) {
                this.faction = f;
            }
        }

        if (hasFaction()) {
            faction.getOnline().add(this);
        }
    }

    public static void reload() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            new FPlayer(player);
        }
    }

    public static FPlayer get(Player player) {
        return players.get(player.getUniqueId());
    }

    public static void remove(Player player) {
        FPlayer fPlayer = FPlayer.get(player);
        fPlayer.getFaction().getOnline().remove(fPlayer);
        players.remove(player.getUniqueId());
    }

    public UUID getPlayerID() {
        return playerID;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        if (faction != null) {
            if (pastFactions.containsKey(faction.getTeamID())) {
                this.time = System.currentTimeMillis();
            } else {
                this.time = System.currentTimeMillis();
                if (pastFactions.size() == Factions.get().getConfig().getInt("factions.maxPastFacs")) {
                    Object key = pastFactions.keySet().iterator().next();
                    pastFactions.remove(key);
                }

                pastFactions.put(faction.getTeamID(), 0L);
            }
        } else {
            long cur = pastFactions.get(this.faction.getTeamID());
            pastFactions.put(this.faction.getTeamID(), cur + (System.currentTimeMillis() - this.time));
        }

        if (hasFaction()) {
            this.faction.getOnline().remove(this);
        }

        this.faction = faction;
        if (faction != null) {
            faction.getOnline().add(this);
        }
    }

    public void updateTime() {
        if (hasFaction()) {
            long toAdd = pastFactions.get(faction.getTeamID());
            pastFactions.put(faction.getTeamID(), toAdd + (System.currentTimeMillis() - this.time));
        }
    }

    public void removeFaction() {
        long cur = pastFactions.get(this.faction.getTeamID());
        pastFactions.put(this.faction.getTeamID(), cur + (System.currentTimeMillis() - this.time));
        this.faction = null;
    }

    public boolean hasFaction() {
        return faction != null;
    }

    public Map<UUID, Long> getPastFactions() {
        return pastFactions;
    }

    public boolean isPublicChat() {
        return publicChat;
    }

    public void setPublicChat(boolean publicChat) {
        this.publicChat = publicChat;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public void addElo(int amount) {
        this.elo += amount;
    }

    public void removeElo(int amount) {
        this.elo -= amount;
    }

    public Long getTime() {
        return time;
    }

    public void setLong(long time) {
        this.time = time;
    }
}
