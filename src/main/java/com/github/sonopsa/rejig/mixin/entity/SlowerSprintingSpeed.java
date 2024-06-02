package com.github.sonopsa.rejig.mixin.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin (LivingEntity.class)
public class SlowerSprintingSpeed {
    @Shadow @Final private static UUID SPRINTING_SPEED_BOOST_ID;
    private static final EntityAttributeModifier SPRINTING_SPEED_BOOST = new EntityAttributeModifier(SPRINTING_SPEED_BOOST_ID, "Sprinting speed boost", 0.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
}