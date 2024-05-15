package com.github.sonopsa.rejig.mixin.client.render.world;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientWorld.class)
public class BrighterStars {
    @ModifyExpressionValue(method = "method_23787", at = @At(value = "CONSTANT", args = "floatValue=0.5f"))
    private float makeStarsBrighter(float original){
        return original*1.5f;
    }
}