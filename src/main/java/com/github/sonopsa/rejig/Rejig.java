package com.github.sonopsa.rejig;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rejig implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Rejig");
	public static Rejig Instance;

	public static RejigConfig Config;

	@Override
	public void onInitialize() {
		Instance = this;

//		Config = new RejigConfig();
//		Config.Config.loadFromFile();
	}

	public void onWorldLoad() {
//		Rejig.LOGGER.info("test");
	}
}