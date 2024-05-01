package com.github.sonopsa.rejig.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(PlayerEntity.class)
public class ParrotStayOn {
    @WrapOperation(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWater()Z"))
    private boolean submergedInWater(PlayerEntity instance, Operation<Boolean> original){
        return instance.isSubmergedInWater();
    }

    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "CONSTANT", args = "floatValue=0.5f"))
    private float higherFallDistance(float constant){
        return 1.6f;
    }

    @Redirect(method = "tickMovement", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;flying:Z", opcode = Opcodes.GETFIELD))
    private boolean ignoreFlying(PlayerAbilities instance){
        return false;
    }
}