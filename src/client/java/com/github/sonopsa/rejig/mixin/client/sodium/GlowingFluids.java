package com.github.sonopsa.rejig.mixin.client.sodium;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.FluidRenderer;
import me.jellysquid.mods.sodium.client.world.WorldSlice;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FluidRenderer.class, remap = false)
public class GlowingFluids {
    // TODO: make this compatible with glowing modded fluids
    // also is this the best way to do this?
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Direction;getAxis()Lnet/minecraft/util/math/Direction$Axis;"))
    private void stuff(WorldSlice world, FluidState fluidState, BlockPos blockPos, BlockPos offset, ChunkBuildBuffers buffers, CallbackInfo ci, @Share("isGlowing") LocalBooleanRef isLavaRef){
        isLavaRef.set(fluidState.isIn(FluidTags.LAVA));
    }

    @ModifyExpressionValue(method = "render", at = @At(value = "CONSTANT", args = "floatValue=0.8f"))
    private float zAxisBrightness(float original, @Share("isGlowing") LocalBooleanRef isLavaRef){
        return isLavaRef.get() ? original + (1 - original) * 0.75f : original;
    }

    @ModifyExpressionValue(method = "render", at = @At(value = "CONSTANT", args = "floatValue=0.6f"))
    private float xAxisBrightness(float original, @Share("isGlowing") LocalBooleanRef isLavaRef){
        return isLavaRef.get() ? original + (1 - original) * 0.75f : original;
    }
}
