package com.github.sonopsa.rejig.mixin.client.render.world;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.world.ClientWorld;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LightmapTextureManager.class)
public abstract class CustomLightmap {

    @Shadow private float flickerIntensity;

    @Shadow
    private static void clamp(Vector3f vec){} // i think this is the right way of doing this

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "tick", at = @At("RETURN"))
    private void onTick(CallbackInfo ci){
        flickerIntensity = 0.1f; // bedrock value and doesnt change
//        flickerIntensity = Math.min(client.options.getGamma().getValue().floatValue() * 0.3f - 0.05f, 0.1f);
    }

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lorg/joml/Vector3f;mul(F)Lorg/joml/Vector3f;", ordinal = 0, remap = false))
    private Vector3f lerpSunsetColor(Vector3f instance, float scalar, @Local ClientWorld clientWorld){
        float[] color;
        color = clientWorld.getDimensionEffects().getFogColorOverride(clientWorld.getSkyAngle(1.0f), 1.0f);
        if (color != null && (color[0] + color[1] + color[2] <= 2.98f)){
            instance.lerp(new Vector3f(color[0], color[1], color[2]), color[3]*0.5f);
        }
        return instance.mul(scalar);
    }

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/LightmapTextureManager;easeOutQuart(F)F"))
    private float customGamma(LightmapTextureManager instance, float x){
//        return 1 - (float) Math.pow(1-x, 4); // vanilla
//        return (float) Math.pow(x, 0.62);
//        return (float) Math.log10((1-Math.pow(1-x,1.2))*9+1);
//        return (float) Math.sin((Math.pow(x, 0.75))*Math.PI/2);
        if (client.options.getGamma().getValue() > 0.5){
            return (float) Math.sin((Math.pow(x, 0.78))*Math.PI/2);
        }
        return (float) Math.pow(x, 1.6);
    }

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Double;floatValue()F", ordinal = 1))
    private float fullbirght(Double instance){
        return Math.abs(2.0f*instance.floatValue()-1.0f);
    }

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lorg/joml/Vector3f;add(Lorg/joml/Vector3fc;)Lorg/joml/Vector3f;", remap = false))
    private void netherColorLerp(float delta, CallbackInfo ci, @Local ClientWorld clientWorld, @Local(ordinal = 1) LocalRef<Vector3f> vectorRef) {
        if (clientWorld.getDimensionEffects().isDarkened()){
            Vector3f skyLightColor = vectorRef.get();

            skyLightColor.mul(new Vector3f(1.56f, 1.78f, 2.19f));
            clamp(skyLightColor);

            vectorRef.set(skyLightColor);
        }
    }

    // end dimension color
    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "Lorg/joml/Vector3f;<init>(FFF)V", ordinal = 2, remap = false))
    private void endColorLerp(Args args) {
        float a0 = args.get(0);
        float a1 = args.get(1);
        float a2 = args.get(2);
        args.set(0, a0*  0.888f); // not setting them directly in case a custom dimension messes with this
        args.set(1, a1);
        args.set(2, a2);
    }
}
