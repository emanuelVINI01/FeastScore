package com.emanuelvini.feastplugins.score.registry;

import com.emanuelvini.feastplugins.score.FeastScore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class Registry {

    protected FeastScore plugin;

    @Getter
    protected String name;

    abstract public void register();

}
