package com.github.sonopsa.rejig.mixin.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin (LivingEntity.class)
public class SlowerSprintingSpeed {
    @Shadow @Final private static UUID SPRINTING_SPEED_BOOST_ID;

    @Mutable
    @Shadow @Final private static EntityAttributeModifier SPRINTING_SPEED_BOOST;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void stuff(CallbackInfo ci){
        SPRINTING_SPEED_BOOST = new EntityAttributeModifier(SPRINTING_SPEED_BOOST_ID, "Sprinting speed boost", 0.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}