package com.github.sonopsa.rejig;

import com.github.sonopsa.rejig.config.ConfigHolder;
import com.github.sonopsa.rejig.config.ConfigOption;

public class RejigClientConfig extends ConfigHolder {
    public static ConfigOption CHAT_PEEK;

    public static ConfigOption BEDROCK_VIEWBOB;
    public static ConfigOption BEDROCK_HANDSWAY;
    public static ConfigOption BEDROCK_HAND_IDLE_ANIMATION;

    public RejigClientConfig() {
        super("rejig/client");
    }

    @Override
    public void createOptions() {
        CHAT_PEEK = Config.registerOption("ChatPeek", new ConfigOption(true));

        BEDROCK_VIEWBOB = Config.registerOption("BedrockViewbob", new ConfigOption(true));
        BEDROCK_HANDSWAY = Config.registerOption("BedrockHandsway", new ConfigOption(true));
        BEDROCK_HAND_IDLE_ANIMATION = Config.registerOption("BedrockHandIdleAnimation", new ConfigOption(true));
    }
}
