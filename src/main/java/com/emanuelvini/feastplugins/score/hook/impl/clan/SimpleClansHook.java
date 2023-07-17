package com.emanuelvini.feastplugins.score.hook.impl.clan;

import com.emanuelvini.feastplugins.score.hook.Hook;
import com.emanuelvini.feastplugins.score.hook.HookType;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.entity.Player;

public class SimpleClansHook implements Hook {

    @Override
    public boolean test(Player player) {
        try {
            Clan clan = SimpleClans.getInstance().getClanManager().getClanPlayer(player).getClan();
            return clan != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public HookType getType() {
        return HookType.SIMPLECLANS;
    }
}
