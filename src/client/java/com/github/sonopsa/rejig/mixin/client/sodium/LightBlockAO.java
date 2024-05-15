package com.github.sonopsa.rejig.mixin.client.sodium;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = LightDataAccess.class, remap = false)
public class LightBlockAO {
    @ModifyExpressionValue(method = "compute", at = @At(value = "CONSTANT", args = {"floatValue=1.0f"}, ordinal = 0))
    private float lightBlockAOLevel(float original, @Local BlockPos pos, @Local BlockRenderView world, @Local BlockState state){
        float ao = state.getAmbientOcclusionLightLevel(world, pos);
        return Math.min(ao * 3.25f, 1.0f);
    }
}