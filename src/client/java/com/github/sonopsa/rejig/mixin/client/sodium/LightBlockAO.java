package com.github.sonopsa.rejig.mixin.client.sodium;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightDataAccess.class)
public class LightBlockAO {
    @WrapOperation(method = "compute", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getLuminance()I"))
    private int stuff(BlockState instance, Operation<Integer> original){
        return 0;
    }
}