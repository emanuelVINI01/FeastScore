package com.emanuelvini.feastplugins.score.manager;

import com.emanuelvini.feastplugins.score.hook.Hook;

import java.util.HashMap;

public class HookManager {

    private final HashMap<String, Hook> hooks = new HashMap<>();

    public Hook getHookByName(String name) {
        return hooks.get(name);
    }

    public void registerHook(Hook hook) {
        hooks.put(hook.getType().getName(), hook);
    }

}
