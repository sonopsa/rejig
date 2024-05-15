package com.github.sonopsa.rejig.mixin.client.render.camera;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
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
    private void viewBobbingInject(MatrixStack matrices, float tickDelta, CallbackInfo ci, @Local(ordinal = 2) float deltaVelocity, @Local(ordinal = 3) float bobFactor){
        deltaTime = client.isPaused() ? 0 : client.getLastFrameDuration(); // frame-time in ticks
//        deltaTime *= client.world.getTickManager().getTickRate()/20;

        // vertical tilt
        float deltaSpeed = camOffset.upwardSpeed - camOffset.prevUpwardSpeed;
        float tiltFac = -(camOffset.upwardSpeed + deltaSpeed * tickDelta);
        camOffset.verticalTilt = MathHelper.clampedLerp(camOffset.verticalTilt, (float) Math.tanh(tiltFac/1.8), deltaTime/2.4f); // max verticalTilt for jumping is about 0.5 (max possible is 1.0)

        // view offset
        float stepOffset = 0;
//        stepOffset = -MathHelper.lerp(tickDelta, camOffset.lastHeightOffset, camOffset.heightOffset);
        Vector3d viewOffset = new Vector3d(0, stepOffset, 0); // get step height offset

        float x = -camOffset.bobFactor + Math.abs(MathHelper.cos((deltaVelocity * (float)Math.PI)) * bobFactor);
        viewOffset.y += x * camOffset.smoothBob*10;

        viewOffset.rotateX(Math.toRadians(camera.getPitch()));
        matrices.translate(viewOffset.x, viewOffset.y, viewOffset.z);

        // vertical view tilt
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) (Math.tanh(camOffset.verticalTilt*2)*0.8f)));

        // set bobFactor
        camOffset.bobFactor = bobFactor;
        camOffset.updateFrame(deltaTime);

        camOffset.heightScale = 1 - bobFactor * 0.64f;

//        bobFactorRef.set(camOffset.bobFactor);
    }

    @Redirect(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;bobView(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    private void handViewBobRedirect(GameRenderer instance, MatrixStack matrices, float tickDelta) {
        if (!(client.getCameraEntity() instanceof PlayerEntity playerEntity)) {
            return;
        }

        float deltaSpeed = playerEntity.horizontalSpeed - playerEntity.prevHorizontalSpeed;
        float g = -(playerEntity.horizontalSpeed + deltaSpeed * tickDelta);
        float handBobFactor = camOffset.smoothBob * camOffset.bobFactor * 10;

        matrices.translate(MathHelper.sin(g * (float)Math.PI) * handBobFactor * 1.1f, -Math.abs(MathHelper.cos(g * (float)Math.PI) * handBobFactor) * 0.9f - camOffset.smoothBob * 0.30, 0.0f);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.sin((g * (float)Math.PI)) * handBobFactor * 3.0f));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(Math.abs(MathHelper.cos(g * (float)Math.PI - 0.2f) * handBobFactor) * 5.0f));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camOffset.verticalTilt * 8));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camOffset.smoothBob*20));
    }

    @ModifyArg(method = "bobView", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"), index = 1)
    private float verticalScale(float x) {
        return 0;
    }

//    @ModifyExpressionValue(method = "bobView", at = @At(value = "CONSTANT", args = "floatValue=0.5f"))
//    private float horizontalScale(float constant){
//        return constant * 1;
//    }
//
//    @ModifyExpressionValue(method = "bobView", at = @At(value = "CONSTANT", args = "floatValue=3.0f"))
//    private float rollScale(float constant){
//        return constant * 1;
//    }

    @ModifyExpressionValue(method = "bobView", at = @At(value = "CONSTANT", args = "floatValue=5.0f"))
    private float pitchScale(float constant){
        return constant * 0.6f;
    }
}
