package com.emanuelvini.feastplugins.score.registry.registries;

import com.emanuelvini.feastplugins.score.FeastScore;
import com.emanuelvini.feastplugins.score.configuration.ConfigurationValue;
import com.emanuelvini.feastplugins.score.configuration.MessageValue;
import com.emanuelvini.feastplugins.score.registry.Registry;
import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import lombok.val;

import java.io.File;

public class ConfigurationRegistry extends Registry {
    public ConfigurationRegistry(FeastScore plugin) {
        //Usa o construtor super para instanciar esse registry
        super(plugin, "registro de configurações");
    }

    @Override
    public void register() {
        val configurationInjector = new BukkitConfigurationInjector(plugin);

        //Salva as configurações padrões caso não exista
        configurationInjector.saveDefaultConfiguration(plugin,

                "configuration.yml",
                "messages.yml"
        );
        val scoreboardsDirectory = new File(plugin.getDataFolder(), "scoreboards");
        if (!scoreboardsDirectory.exists()) {
            scoreboardsDirectory.mkdirs();
            configurationInjector.saveDefaultConfiguration(plugin,
                    "scoreboards/default.yml",
                    "scoreboards/require_clan.yml",
                    "scoreboards/require_plot.yml"
            );
        }

        //Injeta as configurações nas classes
        configurationInjector.injectConfiguration(
                ConfigurationValue.instance(),
                MessageValue.instance()
        );
    }
}
