package com.github.sonopsa.rejig.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin (PlayerEntity.class)
public abstract class SlowerAirSprintingSpeed {
    @Shadow public abstract float getMovementSpeed();

    @ModifyExpressionValue(method = "getOffGroundSpeed", at = @At(value = "CONSTANT", args = "floatValue=0.025999999f"))
    private float fixAirSprintSpeed(float original){
        return 0.02f * getMovementSpeed() * 7.28f;
    }

    @ModifyExpressionValue(method = "getOffGroundSpeed", at = @At(value = "CONSTANT", args = "floatValue=0.02f"))
    private float fixAirWalkSpeed(float original){
        return 0.02f * getMovementSpeed() * 8f;
    }
}
