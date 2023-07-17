package com.emanuelvini.feastplugins.score.hook.impl.plot;

import com.emanuelvini.feastplugins.score.hook.Hook;
import com.emanuelvini.feastplugins.score.hook.HookType;
import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import lombok.val;
import org.bukkit.entity.Player;

public class PlotSquaredHook implements Hook {

    @Override
    public boolean test(Player player) {
        val api = new PlotAPI();
        return api.getPlot(player.getLocation()) != null;
    }

    @Override
    public HookType getType() {
        return HookType.PLOTSQUARED;
    }
}
