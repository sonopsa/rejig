package com.github.sonopsa.rejig.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoxEntity.class)
public class FoxMoreHealth {
    @ModifyExpressionValue(method = "createFoxAttributes", at = @At(value = "CONSTANT", args = "doubleValue=10.0"))
    private static double injected(double constant){
        return 20.0;
    }
}
