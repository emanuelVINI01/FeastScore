package com.emanuelvini.feastplugins.score.hook;

import com.emanuelvini.feastplugins.score.FeastScore;
import com.emanuelvini.feastplugins.score.hook.impl.clan.SimpleClansHook;
import com.emanuelvini.feastplugins.score.hook.impl.plot.PlotSquaredHook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.function.Predicate;


@AllArgsConstructor
@NonNull
public enum HookType {

    NONE("Nenhum",
            null,
            (player) -> true),
    SIMPLECLANS("SimpleClans",
            SimpleClansHook.class,
            player -> FeastScore.getInstance().getHookManager().getHookByName("SimpleClans").test(player)),
    PLOTSQUARED("PlotSquared",
            PlotSquaredHook.class,
            player -> FeastScore.getInstance().getHookManager().getHookByName("PlotSquared").test(player));


    @Getter
    private final String name;

    @Getter
    private final Class<? extends Hook> hookClass;

    private final Predicate<Player> checkHookFunction;

    public boolean isHooked(Player player) {
        return checkHookFunction.test(player);
    }

}
