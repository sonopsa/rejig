package com.github.sonopsa.rejig.mixin.client.render.particle;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO: sodium compat
@Mixin(BillboardParticle.class)
public abstract class ParticlesFaceCamera {
    @Shadow @Final private Quaternionf rotation;

    @Inject(method = "buildGeometry", at = @At(value = "FIELD", target = "Lnet/minecraft/client/particle/BillboardParticle;angle:F", ordinal = 0))
    private void injected(VertexConsumer vertexConsumer, Camera camera, float tickDelta, CallbackInfo ci, @Local(ordinal = 1) float X, @Local(ordinal = 2) float Y, @Local(ordinal = 3) float Z){
        // X, Y, Z is the location of the particle relative to the camera in world space orientation.

        Quaternionf rotationTo = new Quaternionf().lookAlong(X, 0, -Z, 0, 1, 0);

        // im sure there's a better way of doing this but this works so im using it for now
        // calculate orthogonal the vector to remove this?
        double sqrt = Math.sqrt(Math.pow(X, 2) + Math.pow(Z, 2));
        float xAngle = (float) Math.atan2(Y - 0, sqrt - 0);
        rotationTo.mul(RotationAxis.POSITIVE_X.rotation(-xAngle));
        rotation.set(rotationTo);
    }
}
