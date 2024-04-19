package com.github.sonopsa.rejig;

import com.github.sonopsa.rejig.render.CameraOffsetHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class RejigClient implements ClientModInitializer {
	public static CameraOffsetHandler camOffset = null;
	public static KeyBinding chatPeekKeybind;

	@Override
	public void onInitializeClient() {
		chatPeekKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"rejig.keybind.chatpeek",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_Z,
			"rejig.keybinds"
		));
	}

	public static void onClientPlayerSpawn(){
		// This runs every time the player loads in to a world or respawns.
		camOffset = new CameraOffsetHandler();
	}
}