package com.kingarchie.events;

import com.kingarchie.player.FPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinQuitEvent implements Listener {

    public JoinQuitEvent(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        new FPlayer(e.getPlayer());
        if (FPlayer.get(e.getPlayer()).hasFaction()) {
            FPlayer.get(e.getPlayer()).setLong(System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        FPlayer.remove(e.getPlayer());
        FPlayer.get(e.getPlayer()).updateTime();
    }
}
