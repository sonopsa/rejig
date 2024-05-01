package com.github.sonopsa.rejig.mixin.client.sodium;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = LightDataAccess.class, remap = false)
public class LightBlockAO {
    @ModifyExpressionValue(method = "compute", at = @At(value = "CONSTANT", args = {"floatValue=1.0f"}, ordinal = 0))
    private float lightBlockAOLevel(float original){
        return original*0.75f;
    }
}