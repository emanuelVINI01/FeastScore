package com.emanuelvini.feastplugins.score;

import com.emanuelvini.feastplugins.score.bstats.Metrics;
import com.emanuelvini.feastplugins.score.configuration.ConfigurationValue;
import com.emanuelvini.feastplugins.score.manager.HookManager;
import com.emanuelvini.feastplugins.score.manager.ScoreboardManager;
import com.emanuelvini.feastplugins.score.registry.Registry;
import com.emanuelvini.feastplugins.score.registry.registries.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class FeastScore extends JavaPlugin {

    @Getter
    private static FeastScore instance;

    @Getter
    private HookManager hookManager;

    @Getter
    private ScoreboardManager scoreboardManager;

    @Getter
    private Metrics metrics;
    private List<Registry> registries;

    public void log(String... messages) {
        Bukkit.getConsoleSender().sendMessage(String.format("§d[FeastScore] %s", String.join(" ", messages)));
    }

    @Override
    public void onLoad() {
        if (instance != null) {
            log("§cDESATIVANDO PLUGIN! POR FAVOR NÃO USE RELOAD.");
            log("§cUSE O COMANDO §f/feastscore reload§cPARA RECARREGAR O PLUGIN");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;
        //Cria novas instancias dos registros usados
        registries = Arrays.asList(
                new ConfigurationRegistry(this),
                new CommandRegistry(this),
                new HookRegistry(this),
                new ScoreboardRegistry(this),
                new EventsRegistry(this)
        );

        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        log("\n" +
                "            §d§l______ _____  ___   _____ _____   _____ _____ ___________ _____ \n" +
                "            §d§l|  ___|  ___|/ _ \\ /  ___|_   _| /  ___/  __ \\  _  | ___ \\  ___|\n" +
                "            §d§l| |_  | |__ / /_\\ \\\\ `--.  | |   \\ `--.| /  \\/ | | | |_/ / |__  \n" +
                "            §d§l|  _| |  __||  _  | `--. \\ | |    `--. \\ |   | | | |    /|  __| \n" +
                "            §d§l| |   | |___| | | |/\\__/ / | |   /\\__/ / \\__/\\ \\_/ / |\\ \\| |___ \n" +
                "            §d§l\\_|   \\____/\\_| |_/\\____/  \\_/   \\____/ \\____/\\___/\\_| \\_\\____/ \n" +
                "                                                                \n" +
                "            §ehttps://github.com/feastplugins/FeastScore | emanuelVINI      \n");
        //Crie um novo Metrics para ter os dados do plugin

        metrics = new Metrics(this, 19124);

        //Cria um novo hook manager antes do carregamento dos registries
        hookManager = new HookManager();


        //Carrega todos registry já criados para ativação do plugin
        registries.forEach(registry -> {
            log(String.format("§6Carregando o §e%s§6...", registry.getName()));
            try {
                registry.register();
                log(String.format("§aO §e%s§a foi carregado com sucesso!", registry.getName()));
            } catch (Exception e) {
                //Em caso de erro, envia ele no console para uma issue ser aberta
                log(String.format("§cOcorreu um erro ao carregar o §e%s§c. Informe o erro abaixo:", registry.getName()));
                e.printStackTrace();
            }
        });

        //Cria um novo scoreboard manager que gerencia todas scoreboards do plugin

        scoreboardManager = new ScoreboardManager(this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            scoreboardManager.updateAllScoreboards();
        }, 5L, 20L * ConfigurationValue.get(ConfigurationValue::updateTime));

        log("§aPlugin inicializado com sucesso!");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        metrics.shutdown();
        log("§cPlugin desativado com sucesso!");
    }
}
