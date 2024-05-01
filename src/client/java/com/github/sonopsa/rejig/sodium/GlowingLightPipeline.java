package com.github.sonopsa.rejig.sodium;

import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import me.jellysquid.mods.sodium.client.model.light.data.QuadLightData;
import me.jellysquid.mods.sodium.client.model.light.flat.FlatLightPipeline;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadView;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Arrays;

public class GlowingLightPipeline extends FlatLightPipeline {
    private final LightDataAccess lightCache;

    public GlowingLightPipeline(LightDataAccess cache) {
        super(cache);
        this.lightCache = cache;
    }

    @Override
    public void calculate(ModelQuadView quad, BlockPos pos, QuadLightData out, Direction cullFace, Direction lightFace, boolean shade) {
        int lightmap;
        if (cullFace != null) {
            lightmap = this.getOffsetLightmap(pos, cullFace);
        } else {
            int flags = quad.getFlags();
            if ((flags & 4) == 0 && ((flags & 2) == 0 || !LightDataAccess.unpackFC(this.lightCache.get(pos)))) {
                lightmap = LightDataAccess.getEmissiveLightmap(this.lightCache.get(pos));
            } else {
                lightmap = this.getOffsetLightmap(pos, lightFace);
            }
        }

        Arrays.fill(out.lm, lightmap);

        float lightShade = this.lightCache.getWorld().getBrightness(lightFace, shade);
        lightShade += (1 - lightShade) * 0.75f;
        Arrays.fill(out.br, lightShade);
    }

    private int getOffsetLightmap(BlockPos pos, Direction face) {
        int word = this.lightCache.get(pos);
        if (LightDataAccess.unpackEM(word)) {
            return 15728880;
        } else {
            int adjWord = this.lightCache.get(pos, face);
            return LightmapTextureManager.pack(Math.max(LightDataAccess.unpackBL(adjWord), LightDataAccess.unpackLU(word)), LightDataAccess.unpackSL(adjWord));
        }
    }
}