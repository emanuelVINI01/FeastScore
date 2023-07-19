package com.emanuelvini.feastplugins.score.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.function.Function;

@Getter
@Accessors(fluent = true)
@ConfigFile("configuration.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigurationValue implements ConfigurationInjectable {

    @Getter
    private static ConfigurationValue instance = new ConfigurationValue();

    @ConfigField("scoreboard.update_time")
    private int updateTime;


    public static <T> T get(Function<ConfigurationValue, T> function) {
        return function.apply(instance);
    }

}