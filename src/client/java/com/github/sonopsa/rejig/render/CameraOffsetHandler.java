package com.github.sonopsa.rejig.render;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class CameraOffsetHandler {
    public float heightOffset = 0.0f;
    public float lastHeightOffset = 0.0f;

    public float heightScale = 1.0f;
    public float lastHeightScale = 1.0f;

    public float bobFactor = 1.0f;
    public float smoothBob = 0.0f;
    public float verticalTilt = 0.0f;

    public float upwardSpeed = 0.0f;
    public float prevUpwardSpeed = 0.0f;

    public CameraOffsetHandler(){
    }

    public void updateTick(float delta, Entity focusedEntity){
        prevUpwardSpeed = upwardSpeed;
        upwardSpeed = (float) (focusedEntity.getY() - focusedEntity.prevY);

        heightScale = MathHelper.lerp(delta*1.5f, lastHeightScale, heightScale);
        lastHeightScale = heightScale;

        lastHeightOffset = heightOffset;
        heightOffset = MathHelper.lerp(delta*14, heightOffset, 0.0f);
    }
    public void updateFrame(float delta){
        smoothBob = MathHelper.clampedLerp(smoothBob, bobFactor, delta/10);
    }
}
