package com.github.sonopsa.rejig.mixin.client.sodium;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.FluidRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FluidRenderer.class)
public class GlowingFluids {
    @ModifyExpressionValue(method = "render", at = @At(value = "CONSTANT", args = "floatValue=0.8f"))
    private float zAxisBrightness(float original, @Local(argsOnly = true) FluidState fluidState){
        boolean isLava = fluidState.isIn(FluidTags.LAVA);
        return isLava ? MathHelper.lerp(0.75f, original, 1.0f) : original;
    }

    @ModifyExpressionValue(method = "render", at = @At(value = "CONSTANT", args = "floatValue=0.6f"))
    private float xAxisBrightness(float original, @Local(argsOnly = true) FluidState fluidState){
        boolean isLava = fluidState.isIn(FluidTags.LAVA);
        return isLava ? MathHelper.lerp(0.75f, original, 1.0f) : original;
    }

}
