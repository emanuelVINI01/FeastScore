package com.emanuelvini.feastplugins.score.model;

import com.emanuelvini.feastplugins.score.hook.HookType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder(builderMethodName = "create")
@Getter
public class ScoreboardInfo {

    private HookType requiredHook;

    private String requiredWorld;

    private String requiredPermission;

    private int weight;

    private String title;

    private List<String> lines;
}
