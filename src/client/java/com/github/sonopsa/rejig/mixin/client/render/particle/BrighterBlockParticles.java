package com.github.sonopsa.rejig.mixin.client.render.particle;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(BlockDustParticle.class)
abstract class BrighterBlockParticles extends SpriteBillboardParticle {
    private BrighterBlockParticles() {
        super(null, 0, 0, 0);
    }
    @ModifyExpressionValue(method = "<init>(Lnet/minecraft/client/world/ClientWorld;DDDDDDLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(value = "CONSTANT", args = "floatValue=0.6f"))
    private float makeBrighterParticles(float constant) {
        return MathHelper.lerp(0.6f, constant, 1.0f);
    }
}