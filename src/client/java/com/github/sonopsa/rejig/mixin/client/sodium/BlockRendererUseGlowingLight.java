package com.github.sonopsa.rejig.mixin.client.sodium;

import com.github.sonopsa.rejig.accessor.LightPipelineProviderAccess;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.jellysquid.mods.sodium.client.model.light.LightMode;
import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.model.light.LightPipelineProvider;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderContext;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BlockRenderer.class, remap = false)
public class BlockRendererUseGlowingLight {
    @WrapOperation(method = "renderModel", at = @At(value = "INVOKE",
            target = "Lme/jellysquid/mods/sodium/client/model/light/LightPipelineProvider;getLighter" +
                    "(Lme/jellysquid/mods/sodium/client/model/light/LightMode;)Lme/jellysquid/mods/sodium/client/model/light/LightPipeline;"))
    private LightPipeline injected(LightPipelineProvider instance, LightMode type, Operation<LightPipeline> original, @Local(argsOnly = true) BlockRenderContext ctx) {
        if (type == LightMode.FLAT && ctx.state().getLuminance() != 0
                && instance instanceof LightPipelineProviderAccess lightPipelineProviderAccess){

            return lightPipelineProviderAccess.rejig$getGlowingLightPipeline();
        }

        return original.call(instance, type);
    }
}