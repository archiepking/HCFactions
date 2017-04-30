package com.kingarchie.events;

import com.kingarchie.Factions;
import com.kingarchie.player.FPlayer;
import com.kingarchie.team.Faction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatEvent implements Listener {

    public ChatEvent(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        FPlayer player = FPlayer.get(event.getPlayer());
        if (player.hasFaction()) {
            event.setFormat(Factions.get().getConfigString("chat.format")
                    .replaceAll("%team%", player.getFaction().getName())
                    .replaceAll("%username%", event.getPlayer().getName())
                    .replaceAll("%message%", event.getMessage()));
        } else {
            event.setFormat(Factions.get().getConfigString("chat.formatNoTeam")
                    .replaceAll("%username%", event.getPlayer().getName())
                    .replaceAll("%message%", event.getMessage()));
        }
    }
}
