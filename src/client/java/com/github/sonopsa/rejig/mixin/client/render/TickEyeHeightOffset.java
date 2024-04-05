package com.github.sonopsa.rejig.mixin.client.render;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.sonopsa.rejig.RejigClient.camOffset;

@Mixin(Camera.class)
public abstract class TickEyeHeightOffset {
    @Shadow private float cameraY;
    @Shadow private Entity focusedEntity;

    @Inject(method = "updateEyeHeight", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/Camera;lastCameraY:F", ordinal = 0))
    public void lowerEyeHeightStuff(CallbackInfo ci) {
        cameraY /= camOffset.lastHeightScale;
//        cameraY -= lastHeightOffset;
    }

    @Inject(method = "updateEyeHeight", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/Camera;cameraY:F", ordinal = 3, shift = At.Shift.AFTER))
    public void lowerEyeHeightStuffz(CallbackInfo ci) {
        camOffset.updateTick((float) 1 /20, focusedEntity);

//        cameraY += heightOffset;
        cameraY *= camOffset.heightScale;
    }

    @ModifyConstant(method = "updateEyeHeight", constant = @Constant(floatValue = 0.5f))
    private float smoother(float constant){
        return constant*0.8f;
    }
}