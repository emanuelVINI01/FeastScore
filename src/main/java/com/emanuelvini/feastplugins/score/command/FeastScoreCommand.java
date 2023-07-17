package com.emanuelvini.feastplugins.score.command;

import com.emanuelvini.feastplugins.score.FeastScore;
import com.emanuelvini.feastplugins.score.configuration.MessageValue;
import com.emanuelvini.feastplugins.score.manager.ScoreboardManager;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.command.CommandSender;

//Cria as anotações de comando usando o CommandFramework
public class FeastScoreCommand {


    @Command(
            name = "feastscore",
            permission = "feastcore.admin",
            aliases = {"score", "fs"}
    )

    public void onHelp(Context<CommandSender> context) {
        MessageValue.get(MessageValue::help).forEach(context::sendMessage);
    }


    @Command(
            name = "feastscore.reload",
            permission = "feastcore.admin"
    )

    public void onReload(Context<CommandSender> context) {
        FeastScore.getInstance().getScoreboardManager().reload();
        context.sendMessage(MessageValue.get(MessageValue::successfullyReloaded));
    }
}
