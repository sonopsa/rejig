package com.github.sonopsa.rejig;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rejig implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("rejig");
	public static Rejig Instance;

	@Override
	public void onInitialize() {
		Instance = this;
	}

	public void onWorldLoad() {
		Rejig.LOGGER.info("test");
	}
}