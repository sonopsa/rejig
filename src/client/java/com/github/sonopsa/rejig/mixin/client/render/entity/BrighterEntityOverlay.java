package com.github.sonopsa.rejig.mixin.client.render.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.OverlayTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(OverlayTexture.class)
public class BrighterEntityOverlay {
    @ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "intValue=-1308622593"))
    private int brighterHurtOverlay(int value) {
        // ABGR int of 255 red and 160 alpha
        return 0x700000FF;
    }

    @ModifyExpressionValue(method = "<init>", at = @At(value = "CONSTANT", args = "floatValue=0.75f"))
    private float brighterWhiteOverlay(float value) {
        // makes the white overlay used on tnt more opaque
        return 0.9f;
    }
}
