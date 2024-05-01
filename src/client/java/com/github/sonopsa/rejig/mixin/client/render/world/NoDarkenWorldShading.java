package com.github.sonopsa.rejig.mixin.client.render.world;

import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientWorld.class)
public class NoDarkenWorldShading {
    @Redirect(method = "getBrightness", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/DimensionEffects;isDarkened()Z"))
    private boolean noDarken(DimensionEffects instance){
        return false;
    }
}