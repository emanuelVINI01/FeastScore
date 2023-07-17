package com.emanuelvini.feastplugins.score.registry.registries;

import com.emanuelvini.feastplugins.score.FeastScore;
import com.emanuelvini.feastplugins.score.listeners.PlayerListener;
import com.emanuelvini.feastplugins.score.registry.Registry;
import org.bukkit.Bukkit;

public class EventsRegistry extends Registry {
    public EventsRegistry(FeastScore plugin) {
        super(plugin, "registro de eventos");
    }

    @Override
    public void register() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(plugin), plugin);
    }
}
