package com.emanuelvini.feastplugins.score.manager;

import com.emanuelvini.feastplugins.score.FeastScore;
import com.emanuelvini.feastplugins.score.model.ScoreboardInfo;
import com.emanuelvini.feastplugins.score.registry.registries.ScoreboardRegistry;
import fr.mrmicky.fastboard.FastBoard;
import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ScoreboardManager {

    private final HashMap<String, AbstractMap.SimpleEntry<ScoreboardInfo, fr.mrmicky.fastboard.FastBoard>> scoreboards = new HashMap<>();
    private FeastScore plugin;

    public void reload() {
        ScoreboardRegistry.getInstance().reload();
        scoreboards.clear();
        Bukkit.getOnlinePlayers().forEach(player -> {
            createScoreboard(player, getAllowedScoreboard(player));
        });
    }

    public void updateAllScoreboards() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            val toBeRemoved = new ArrayList<String>();
            scoreboards.forEach((key, entry) -> {
                val player = Bukkit.getPlayer(key);
                if (player == null) {
                    toBeRemoved.add(key);
                    return;
                }
                val allowedScoreboard = getAllowedScoreboard(player);
                if (allowedScoreboard != entry.getKey()) {
                    toBeRemoved.add(key);
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        createScoreboard(player, allowedScoreboard);
                    });
                    return;
                }
                if (entry.getValue().isDeleted()) {
                    toBeRemoved.add(key);
                    return;
                }
                Bukkit.getScheduler().runTask(plugin, () -> updateScoreboard(player, entry.getKey(), entry.getValue()));

            });
            toBeRemoved.forEach(toRemove -> {
                val board = scoreboards.get(toRemove).getValue();
                if (!board.isDeleted()) {
                    board.delete();
                }
                scoreboards.remove(toRemove);
            });
        });
    }

    //Remove o jogador do map de scoreboards
    public void remove(String name) {
        scoreboards.get(name).getValue().delete();
        scoreboards.remove(name);
    }

    public ScoreboardInfo getAllowedScoreboard(Player player) {
        var scoreboardInfoAllowed = new AtomicReference<ScoreboardInfo>(null);

        ScoreboardRegistry.getInstance().getScoreboards().forEach(scoreboardInfo -> {
            if ((
                    !scoreboardInfo.getRequiredPermission().equals("") &&
                            !player.hasPermission(scoreboardInfo.getRequiredPermission())
            ) || (
                    !scoreboardInfo.getRequiredWorld().equals("") &&
                            !player.getWorld().getName().equals(scoreboardInfo.getRequiredWorld())
            )
                    || (
                    !scoreboardInfo.getRequiredHook().isHooked(player)
            )
            ) return;
            if (scoreboardInfoAllowed.get() == null) {
                scoreboardInfoAllowed.set(scoreboardInfo);
            } else if (scoreboardInfoAllowed.get().getWeight() < scoreboardInfo.getWeight()) {
                scoreboardInfoAllowed.set(scoreboardInfo);
            }
        });

        return scoreboardInfoAllowed.get();
    }


    public void createScoreboard(Player player, ScoreboardInfo scoreboardInfo) {

        val board = new FastBoard(player);
        val lines = scoreboardInfo.
                getLines().
                stream().
                map(line -> PlaceholderAPI.setPlaceholders(player, line)).
                map(line -> line.substring(0, Math.min(30, line.length()))).
                collect(Collectors.toList());
        board.updateTitle(scoreboardInfo.getTitle());
        board.updateLines(lines);
        scoreboards.put(player.getName(), new AbstractMap.SimpleEntry<>(scoreboardInfo, board));
    }

    private void updateScoreboard(Player player, ScoreboardInfo scoreboardInfo, FastBoard board) {
        if (board.isDeleted()) return;
        val lines = scoreboardInfo.
                getLines().
                stream().
                map(line -> PlaceholderAPI.setPlaceholders(player, line)).
                map(line -> line.substring(0, Math.min(30, line.length()))).
                collect(Collectors.toList());
        board.updateLines(lines);
    }

}
