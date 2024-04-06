package com.github.sonopsa.rejig.mixin.world;

import com.github.sonopsa.rejig.world.SnowHeightMap;
import com.github.sonopsa.rejig.world.SnowNoiseSampler;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class BiomeSnowHeight {
    @Unique
    private Registry<Biome> biomeRegistry;
    @Unique
    private SnowNoiseSampler snowNoiseSampler;
    @Unique
    private boolean isFlat;

    @Shadow
    public abstract long getSeed();
    @Shadow public abstract ServerWorld toServerWorld();
    @Shadow public abstract boolean isFlat();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        biomeRegistry = toServerWorld().getRegistryManager().get(RegistryKeys.BIOME);
        snowNoiseSampler = new SnowNoiseSampler(getSeed());
        isFlat = isFlat();
    }

    // TODO: does this affect performance
    @Inject(method = "tickIceAndSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;get(Lnet/minecraft/state/property/Property;)Ljava/lang/Comparable;"))
    private void injected(BlockPos pos, CallbackInfo ci, @Local(ordinal = 1) BlockPos blockPos, @Local(ordinal = 0) Biome biome, @Local(ordinal = 0) LocalIntRef intRef) {

        int snowAccumulationRule = intRef.get();

        double snowNoise = snowNoiseSampler.getSample(blockPos.getX(), blockPos.getZ(), 1.2f);
        snowNoise = Math.min(snowNoiseSampler.getSample(-blockPos.getX() + 1024, -blockPos.getZ() + 1024, 8.0f), snowNoise);

        // more accurate to bedrock but also is this necessary?
        if (isFlat && blockPos.getX() % 16 == 0 || blockPos.getY() % 16 == 0) {
            return;
        }

        int height = 0;

        if (snowNoise > -0.55) {
            height = 1;
        }

        if (snowNoise >= 0) {
            int minHeight = 0;
            int maxHeight = 1;

            var biomeKey = biomeRegistry.getKey(biome).orElse(null);

            if (biomeKey != null) {
                if (SnowHeightMap.hasSnowProperties(biomeKey)) {
                    Vector2i snowProperties = SnowHeightMap.getSnowProperties(biomeKey);
                    minHeight = snowProperties.get(0);
                    maxHeight = snowProperties.get(1);
                }
            }

            if (brighterThanNeighbors(blockPos)) {
                snowNoise += 0.125;
                height = Math.min(maxHeight, Math.max(1, (int) Math.floor(snowNoise * 8)));
            } else {
                height = Math.min(minHeight, Math.max(1, (int) Math.floor(snowNoise * 8)));
            }
        }

        intRef.set(height * snowAccumulationRule);
    }

    @Unique
    private boolean brighterThanNeighbors(BlockPos pos){
        int lightLevel = toServerWorld().getLightLevel(LightType.SKY, pos);

        return (lightLevel > toServerWorld().getLightLevel(LightType.SKY, pos.north())  ||
                lightLevel > toServerWorld().getLightLevel(LightType.SKY, pos.south())  ||
                lightLevel > toServerWorld().getLightLevel(LightType.SKY, pos.east())   ||
                lightLevel > toServerWorld().getLightLevel(LightType.SKY, pos.west())   );
    }
}
