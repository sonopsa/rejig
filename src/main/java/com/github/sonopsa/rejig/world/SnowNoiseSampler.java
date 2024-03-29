package com.github.sonopsa.rejig.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;

public class SnowNoiseSampler {
    OctaveSimplexNoiseSampler noiseSampler;
    public SnowNoiseSampler(long seed){
        noiseSampler = new OctaveSimplexNoiseSampler(new ChunkRandom(new CheckedRandom(seed)), ImmutableList.of(-4, -3, -1));
    }

    public double getSample (double x, double z, float scale){
        return noiseSampler.sample(x/scale,z/scale, true);
    }
}
