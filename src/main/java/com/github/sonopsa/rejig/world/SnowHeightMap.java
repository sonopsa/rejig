package com.github.sonopsa.rejig.world;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.joml.Vector2i;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Map.entry;

public class SnowHeightMap {
    private static final Map<RegistryKey<Biome>, Vector2i> vanillaProperties = new HashMap<>(Map.ofEntries(
            entry(BiomeKeys.DEEP_FROZEN_OCEAN,  new Vector2i(1, 2   )),

            entry(BiomeKeys.FROZEN_RIVER,       new Vector2i(1, 2   )),
            entry(BiomeKeys.FROZEN_OCEAN,       new Vector2i(1, 2   )),
            entry(BiomeKeys.SNOWY_PLAINS,       new Vector2i(2, 8   )),
            entry(BiomeKeys.ICE_SPIKES,         new Vector2i(4, 12  )),
            entry(BiomeKeys.GROVE,              new Vector2i(1, 2   )),
            entry(BiomeKeys.FROZEN_PEAKS,       new Vector2i(1, 2   )),
            entry(BiomeKeys.JAGGED_PEAKS,       new Vector2i(1, 2   )),
            entry(BiomeKeys.SNOWY_SLOPES,       new Vector2i(1, 4   )),
            entry(BiomeKeys.SNOWY_TAIGA,        new Vector2i(1, 4   )),
            entry(BiomeKeys.SNOWY_BEACH,        new Vector2i(1, 2   )),

            entry(BiomeKeys.WINDSWEPT_HILLS,    new Vector2i(0, 2   )),
            entry(BiomeKeys.STONY_SHORE,        new Vector2i(0, 2   ))
    ));

    private static final ConcurrentMap<RegistryKey<Biome>, Vector2i> propertyCache = new ConcurrentHashMap<>(vanillaProperties);

    public static Vector2i getSnowProperties(RegistryKey<Biome> biomeKey){
        return propertyCache.get(biomeKey);
    }

    public static boolean hasSnowProperties(RegistryKey<Biome> biomeKey){
        return propertyCache.containsKey(biomeKey);
    }
}
