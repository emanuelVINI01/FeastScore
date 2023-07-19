package com.emanuelvini.feastplugins.score.registry.registries;

import com.emanuelvini.feastplugins.score.FeastScore;
import com.emanuelvini.feastplugins.score.hook.HookType;
import com.emanuelvini.feastplugins.score.model.ScoreboardInfo;
import com.emanuelvini.feastplugins.score.registry.Registry;
import lombok.Getter;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ScoreboardRegistry extends Registry {

    @Getter
    private static ScoreboardRegistry instance;

    @Getter
    private List<ScoreboardInfo> scoreboards = new ArrayList<>();

    public ScoreboardRegistry(FeastScore plugin) {
        super(plugin, "registro de scoreboard");
    }

    @Override
    public void register() {
        instance = this;
        val scoreboardsDirectory = new File(plugin.getDataFolder(), "scoreboards");
        scoreboardsDirectory.mkdirs();
        Arrays.asList(scoreboardsDirectory.listFiles()).forEach(file -> {
            if (file.getName().endsWith(".yml")) {
                try {
                    val configurationYaml = YamlConfiguration.loadConfiguration(file);
                    val scoreboard = loadScoreboard(configurationYaml);
                    if (scoreboard == null) {
                        plugin.log(String.format("§cNão foi possivel carregar a scoreboard §f%s§c, verifique os erros acima.", file.getName()));
                        return;
                    }
                    scoreboards.add(scoreboard);
                    plugin.log(String.format("§aA scoreboard §f%s§a com §f%d§a linhas foi carregada com sucesso.",
                            file.getName(),
                            scoreboard.getLines().size()
                    ));
                } catch (Exception e) {
                    plugin.log(String.format("§cHouve um erro ao carregar a scoreboard §f%s§c, verifique o erro abaixo ou informe numa issue:", file.getName()));
                    e.printStackTrace();
                }
            }
        });
    }

    private HookType getHookTypeFromString(String hookTypeString) {
        if (hookTypeString.equalsIgnoreCase("")) {
            return HookType.NONE;
        }
        return HookType.valueOf(hookTypeString.toUpperCase(Locale.ROOT));
    }

    public void reload() {
        scoreboards.clear();
        register();
    }

    private ScoreboardInfo loadScoreboard(ConfigurationSection section) {
        HookType hookType = HookType.NONE;
        if (!section.getString("required_hook").equals("")) {
            try {
                hookType = getHookTypeFromString(section.getString("required hook"));
            } catch (Exception e) {
                plugin.log(String.format("§b[SCOREBOARD] §cO tipo de hook §f%s§c não existe!", section.getString("required_hook")));
                return null;
            }
        }

        if (hookType != HookType.NONE && plugin.getHookManager().getHookByName(hookType.getName()) == null) {
            plugin.log(String.format("§b[SCOREBOARD] §cO hook §f%s§c não está ativado!", hookType.getName()));
            return null;
        }

        return ScoreboardInfo.
                create().
                requiredHook(hookType).
                requiredWorld(section.getString("required_world")).
                requiredPermission(section.getString("required_permission")).
                weight(section.getInt("weight")).
                title(ChatColor.translateAlternateColorCodes('&', section.getString("scoreboard.title"))).
                lines(
                        section.getStringList("scoreboard.lines").
                                stream().
                                map(line -> ChatColor.
                                        translateAlternateColorCodes('&', line)).
                                map(line -> line.equals("") ? "§r" : line).
                                collect(Collectors.toList())).
                build();
    }

}
