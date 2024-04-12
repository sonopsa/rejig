package com.github.sonopsa.rejig.mixin.client.event;

import com.github.sonopsa.rejig.RejigClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEvents {
    @Inject(method = "init", at = @At("HEAD"))
    private void clientPlayerInit(CallbackInfo ci) {
        RejigClient.onClientPlayerSpawn();
    }
}
