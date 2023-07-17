package com.emanuelvini.feastplugins.score.registry.registries;

import com.emanuelvini.feastplugins.score.FeastScore;
import com.emanuelvini.feastplugins.score.hook.HookType;
import com.emanuelvini.feastplugins.score.registry.Registry;
import lombok.val;
import org.bukkit.Bukkit;

import java.util.Arrays;

public class HookRegistry extends Registry {
    public HookRegistry(FeastScore plugin) {
        super(plugin, "registro de hook");
    }

    @Override

    public void register() {
        val pluginManager = Bukkit.getPluginManager();
        Arrays.asList(HookType.values()).forEach(hookType -> {
            if (pluginManager.getPlugin(hookType.getName()) != null) {
                try {
                    plugin.getHookManager().registerHook(hookType.getHookClass().newInstance());
                } catch (Exception ignored) {
                }
                plugin.log(String.format("§b[HOOK] §a%s hookado realizado com sucesso.", hookType.getName()));
            }
        });
    }
}
