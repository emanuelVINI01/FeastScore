package com.emanuelvini.feastplugins.score.listeners;

import com.emanuelvini.feastplugins.score.FeastScore;
import lombok.AllArgsConstructor;
import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerListener implements Listener {

    private FeastScore plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        val scoreboardInfo = plugin.getScoreboardManager().getAllowedScoreboard(event.getPlayer());
        plugin.getScoreboardManager().createScoreboard(event.getPlayer(), scoreboardInfo);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getScoreboardManager().remove(event.getPlayer().getName());
    }

}
