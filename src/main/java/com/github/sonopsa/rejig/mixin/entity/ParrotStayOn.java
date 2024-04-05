package com.github.sonopsa.rejig.mixin.entity;

import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(PlayerEntity.class)
public class ParrotStayOn {
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWater()Z"))
    private boolean submergedInWater(PlayerEntity instance){
        return true ? instance.isSubmergedInWater() : instance.isTouchingWater();
    }

    @ModifyConstant(method = "tickMovement", constant = @Constant(floatValue = 0.5f))
    private float higherFallDistance(float constant){
        return true ? 1.6f : constant;
    }

    @Redirect(method = "tickMovement", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;flying:Z", opcode = Opcodes.GETFIELD))
    private boolean ignoreFlying(PlayerAbilities instance){
        return !true && instance.flying;
    }
}