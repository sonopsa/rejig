package com.github.sonopsa.rejig.mixin.client.render.world;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldRenderer.class)
public class LargerStars {
    @ModifyExpressionValue(method = "renderStars(Lnet/minecraft/client/render/BufferBuilder;)" +
            "Lnet/minecraft/client/render/BufferBuilder$BuiltBuffer;",
            at = @At(value = "CONSTANT", args = "floatValue=0.15f"))
    private float starWidth(float constant){
        return constant * 1.25f;
    }

    @ModifyExpressionValue(method = "renderStars(Lnet/minecraft/client/render/BufferBuilder;)" +
            "Lnet/minecraft/client/render/BufferBuilder$BuiltBuffer;",
            at = @At(value = "CONSTANT", args = "floatValue=0.10f"))
    private float starHeight(float constant){
        return constant * 1.25f;
    }
}
