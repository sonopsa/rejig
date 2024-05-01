package com.github.sonopsa.rejig.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoxEntity.class)
public class FoxLongerWindup {
    @ModifyExpressionValue(method = "tick", at = @At(value = "CONSTANT", args = "floatValue=0.2f", ordinal = 1))
    private float windupSpeed(float constant){
        return constant/3.45F;
    }
}