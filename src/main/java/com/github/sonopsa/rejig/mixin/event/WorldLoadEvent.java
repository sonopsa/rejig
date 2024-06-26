package com.github.sonopsa.rejig.mixin.event;

import com.github.sonopsa.rejig.Rejig;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class WorldLoadEvent {
    @Inject(at = @At("TAIL"), method = "loadWorld")
    private void init(CallbackInfo info) {
        Rejig.Instance.onWorldLoad();
    }
}