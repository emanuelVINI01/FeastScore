package com.emanuelvini.feastplugins.score.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;

@Getter
@Accessors(fluent = true)
@ConfigFile("messages.yml")
@TranslateColors()
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageValue implements ConfigurationInjectable {

    @Getter
    private static MessageValue instance = new MessageValue();

    @ConfigField("help")
    private List<String> help;

    @ConfigField("not_have_permission")
    private String notHavePermission;

    @ConfigField("successfully_reloaded")
    private String successfullyReloaded;

    public static <T> T get(Function<MessageValue, T> function) {
        return function.apply(instance);
    }

}