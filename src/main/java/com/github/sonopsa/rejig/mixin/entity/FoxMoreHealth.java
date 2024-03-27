package com.github.sonopsa.rejig.mixin.entity;

import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FoxEntity.class)
public class FoxMoreHealth {
    @ModifyConstant(method = "createFoxAttributes", constant = @Constant(doubleValue = 10.0))
    private static double injected(double constant){
        return true ? 20.0 : constant;
    }
}
