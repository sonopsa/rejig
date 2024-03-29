package com.github.sonopsa.rejig.mixin.world;

import com.github.sonopsa.rejig.world.SnowHeightMap;
import com.github.sonopsa.rejig.world.SnowNoiseSampler;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.RandomSequencesState;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerWorld.class)
public abstract class BiomeSnowHeight {
    @Unique
    private Registry<Biome> biomeRegistry;
    @Unique
    private ServerWorld serverWorld;
    @Unique
    private SnowNoiseSampler snowNoiseSampler;
    @Unique
    private boolean isFlat;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld, long seed, List spawners, boolean shouldTickTime, RandomSequencesState randomSequencesState, CallbackInfo ci) {
        serverWorld = (ServerWorld) (Object) this;
        biomeRegistry = serverWorld.getRegistryManager().get(RegistryKeys.BIOME);
        snowNoiseSampler = new SnowNoiseSampler(serverWorld.getSeed());
        isFlat = false;
    }

    // random ticks seem to take 2% longer with this

    @Inject(method = "tickIceAndSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;canSetSnow(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z", shift = At.Shift.AFTER))
    private void injected(BlockPos pos, CallbackInfo ci, @Local(ordinal = 1) BlockPos blockPos, @Local(ordinal = 0) Biome biome, @Local(ordinal = 0) LocalIntRef intRef) {

        int snowAccumulationHeightRule = intRef.get();

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

        intRef.set(height * snowAccumulationHeightRule);
    }

    @Unique
    private boolean brighterThanNeighbors(BlockPos pos){
        boolean north = serverWorld.getLightLevel(LightType.SKY, pos) > serverWorld.getLightLevel(LightType.SKY, pos.north());
        boolean south = serverWorld.getLightLevel(LightType.SKY, pos) > serverWorld.getLightLevel(LightType.SKY, pos.south());
        boolean east = serverWorld.getLightLevel(LightType.SKY, pos) > serverWorld.getLightLevel(LightType.SKY, pos.east());
        boolean west = serverWorld.getLightLevel(LightType.SKY, pos) > serverWorld.getLightLevel(LightType.SKY, pos.west());
        return north | south | east | west;
    }
}
