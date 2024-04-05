package com.github.sonopsa.rejig;

import com.github.sonopsa.rejig.render.CameraOffsetHandler;
import net.fabricmc.api.ClientModInitializer;

public class RejigClient implements ClientModInitializer {
	public static CameraOffsetHandler camOffset = null;
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}

	public static void onClientPlayerSpwan(){
		// This runs every time the player loads in to a world or respawns.
		camOffset = new CameraOffsetHandler();
	}
}