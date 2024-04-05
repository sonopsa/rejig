package com.github.sonopsa.rejig.mixin.client.render;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.Math;

import static com.github.sonopsa.rejig.RejigClient.camOffset;

@Mixin(GameRenderer.class)
public abstract class FrameViewBobOffset {
    @Unique
    float deltaTime = 0;
    @Shadow @Final
    MinecraftClient client;
    @Shadow @Final
    private Camera camera;

    @Inject(method = "bobView", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"))
    private void stuff(MatrixStack matrices, float tickDelta, CallbackInfo ci, @Local(ordinal = 3) LocalFloatRef bobFactorRef){
        deltaTime = client.isPaused() ? 0 : client.getLastFrameDuration(); // frame-time in ticks
//        deltaTime *= client.world.getTickManager().getTickRate()/20;

        float deltaSpeed = camOffset.upwardSpeed - camOffset.prevUpwardSpeed;
        float tiltFac = -(camOffset.upwardSpeed + deltaSpeed * tickDelta);
        camOffset.verticalTilt = MathHelper.lerp(deltaTime/2, camOffset.verticalTilt, tiltFac);

        Vector3d stuff = new Vector3d(0, MathHelper.lerp(tickDelta, -camOffset.lastHeightOffset, -camOffset.heightOffset), 0);
        stuff.rotateX(Math.toRadians(camera.getPitch()));
        matrices.translate(stuff.x, stuff.y, stuff.z);

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) Math.tanh(camOffset.verticalTilt*2)));

        camOffset.bobFactor = bobFactorRef.get();
        camOffset.updateFrame(deltaTime);

        camOffset.heightScale = 1 - bobFactorRef.get() * 0.5f;

        bobFactorRef.set(camOffset.bobFactor);
    }

    @Redirect(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;bobView(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    private void handViewBobRedirect(GameRenderer instance, MatrixStack matrices, float tickDelta) {
        if (!(client.getCameraEntity() instanceof PlayerEntity playerEntity)) {
            return;
        }

        float deltaSpeed = playerEntity.horizontalSpeed - playerEntity.prevHorizontalSpeed;
        float g = -(playerEntity.horizontalSpeed + deltaSpeed * tickDelta);
        float handBobFactor = camOffset.smoothBob * camOffset.bobFactor * 10;

        matrices.translate(MathHelper.sin(g * (float)Math.PI) * handBobFactor * 1.3f, -Math.abs(MathHelper.cos(g * (float)Math.PI) * handBobFactor) * 1.4f - camOffset.smoothBob * 0.35, 0.0f);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.sin((g * (float)Math.PI)) * handBobFactor * 3.0f));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(Math.abs(MathHelper.cos(g * (float)Math.PI - 0.2f) * handBobFactor) * 5.0f));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) Math.tanh(camOffset.verticalTilt*1.5)*7));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camOffset.smoothBob*20));
    }

    @ModifyArg(method = "bobView", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"), index = 1)
    private float verticalScale(float x) {
        return -camOffset.bobFactor - x;
    }
    @ModifyConstant(method = "bobView", constant = @Constant(floatValue = 0.5f))
    private float horizontalScale(float constant){
        return constant * 1;
    }
    @ModifyConstant(method = "bobView", constant = @Constant(floatValue = 3.0f))
    private float rollScale(float constant){
        return constant * 1;
    }
    @ModifyConstant(method = "bobView", constant = @Constant(floatValue = 5.0f))
    private float pitchScale(float constant){
        return constant * 1;
    }
}
