package com.github.sonopsa.rejig.mixin.client.sodium;

import com.github.sonopsa.rejig.accessor.LightPipelineProviderAccess;
import com.github.sonopsa.rejig.sodium.GlowingLightPipeline;
import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.model.light.LightPipelineProvider;
import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LightPipelineProvider.class, remap = false)
public class GetGlowingLight implements LightPipelineProviderAccess {

    @Unique
    private LightPipeline glowing;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addGlowing(LightDataAccess cache, CallbackInfo ci){
        this.glowing = new GlowingLightPipeline(cache);
    }

    @Override
    public LightPipeline rejig$getGlowingLightPipeline(){
        return this.glowing;
    }
}