package com.kingarchie.team;

import com.kingarchie.Factions;
import com.kingarchie.player.FPlayer;
import com.kingarchie.team.chat.ChatType;
import com.kingarchie.team.location.Region;
import com.kingarchie.team.power.Power;
import org.bukkit.Bukkit;

import java.util.*;

public class Faction {

    private static Set<Faction> teams = new HashSet<>();
    private static Map<UUID, String> disbandedFactions = new HashMap<>();
    private static int rosterLimit = Factions.get().getConfig().getInt("factions.roster");
    private List<FPlayer> online = new ArrayList<>();
    private UUID teamID = UUID.randomUUID();
    private String announcement = "";
    private String name;
    private int[] home;
    private UUID leader;
    private Map<UUID, Boolean> players = new HashMap<>();
    private Map<UUID, ChatType> chatMode = new HashMap<>();
    private Set<UUID> invites = new HashSet<>();
    private Region region;
    private Power power;
    private double balance = 0;
    private int size = 1;
    private int elo;

    public Faction(String name, UUID leaderUUID) {
        this.getTeams().add(this);
        this.setName(name);
        this.setLeader(leaderUUID);
        this.power = new Power(this);
    }

    public static Set<Faction> getTeams() {
        return teams;
    }

    public static Map<UUID, String> getDisbandedFactions() {
        return disbandedFactions;
    }

    public static Faction getFactionByID(UUID teamID) {
        for (Faction f : teams) {
            if (f.getTeamID().equals(teamID)) {
                return f;
            }
        }

        return null;
    }

    public static boolean factionExists(String name) {
        for (Faction fac : teams) {
            if (fac.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    public void sendMessage(String message) {
        for (FPlayer p : online) {
            Bukkit.getPlayer(p.getPlayerID()).sendMessage(message);
        }
    }

    public static int getRosterLimit() {
        return rosterLimit;
    }

    public List<FPlayer> getOnline() {
        return online;
    }

    public UUID getTeamID() {
        return teamID;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getHome() {
        return home;
    }

    public void setHome(int[] coordinates) {
        this.home = home;
    }

    public UUID getLeader() {
        return leader;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public Map<UUID, Boolean> getPlayers() {
        return players;
    }

    public Map<UUID, ChatType> getChatMode() {
        return chatMode;
    }

    public Set<UUID> getInvites() {
        return invites;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Power getPower() {
        return power;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(int balance) {
        this.balance += balance;
    }

    public void removeBalance(int balance) {
        this.balance -= balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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
}
