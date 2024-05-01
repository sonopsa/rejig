package com.github.sonopsa.rejig.mixin.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(OverlayTexture.class)
public class BrighterEntityOverlay {
    @ModifyConstant(method = "<init>", constant = @Constant(intValue = -1308622593))
    private int injected(int value) {
        // ABGR int of 255 red and 160 alpha
        return 0x700000FF;
    }

    @ModifyConstant(method = "<init>", constant = @Constant(floatValue = 0.75f))
    private float injectedz(float value) {
        // makes the white overlay used on tnt more opaque
        return 0.9f;
    }
}
