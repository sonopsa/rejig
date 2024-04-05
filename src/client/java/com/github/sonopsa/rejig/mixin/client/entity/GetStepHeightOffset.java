package com.github.sonopsa.rejig.mixin.client.entity;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.sonopsa.rejig.RejigClient.camOffset;

@Mixin(Entity.class)
public abstract class GetStepHeightOffset {

    @Inject(method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;", at = @At(value = "RETURN", ordinal = 0))
    private void injected(Vec3d movement, CallbackInfoReturnable<Vec3d> cir) {
        if ((Object)this instanceof ClientPlayerEntity){
            camOffset.heightOffset -= (float) cir.getReturnValue().y;
        }
    }
}
