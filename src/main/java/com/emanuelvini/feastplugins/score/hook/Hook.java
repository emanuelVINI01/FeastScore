package com.emanuelvini.feastplugins.score.hook;

import org.bukkit.entity.Player;

public interface Hook {

    boolean test(Player player);

    HookType getType();

}
