package com.github.sonopsa.rejig.mixin.client.render.camera;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class IdleHandAnimation {
    @Unique
    private static final float SCALE_SETTING = 1.5f;
    @Unique private double timer = 0;
    @Unique private float scale = SCALE_SETTING;
    @Shadow @Final private MinecraftClient client;
    @Shadow private ItemStack mainHand;

    @Inject(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;" +
            "Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At("HEAD"))
    private void idleHandAnimation(float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light, CallbackInfo ci){
        timer += client.isPaused() ? 0 : client.getLastFrameDuration()/20;
        scale = MathHelper.clampedLerp(scale, mainHand != ItemStack.EMPTY ? SCALE_SETTING : 0, client.getLastFrameDuration()/8);

        // IDK if this is necessary but it cant hurt I guess?
        if (scale > 0.001){
            float sinTime = (float) Math.sin(timer * 1.85); // I did it like this because it looks nicer
            matrices.translate(0, sinTime * 0.001 * scale, 0);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(sinTime * 0.25f * scale));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float) Math.sin(timer * 1.85 / 2) * -0.15f * scale));
        }
    }
}
