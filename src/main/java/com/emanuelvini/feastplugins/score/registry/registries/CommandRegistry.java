package com.emanuelvini.feastplugins.score.registry.registries;

import com.emanuelvini.feastplugins.score.FeastScore;
import com.emanuelvini.feastplugins.score.command.FeastScoreCommand;
import com.emanuelvini.feastplugins.score.configuration.MessageValue;
import com.emanuelvini.feastplugins.score.registry.Registry;
import lombok.val;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.message.MessageType;

public class CommandRegistry extends Registry {

    public CommandRegistry(FeastScore plugin) {
        //Usa o construtor super para instanciar esse registry
        super(plugin, "registro de comandos");
    }

    @Override
    public void register() {
        //Cria um novo bukkit frame usando a API do CommandFramework
        val bukkitFrame = new BukkitFrame(plugin);

        bukkitFrame.getMessageHolder().setMessage(MessageType.NO_PERMISSION, MessageValue.get(MessageValue::notHavePermission));

        bukkitFrame.registerCommands(new FeastScoreCommand());

    }
}
