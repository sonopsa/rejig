package com.github.sonopsa.rejig.mixin.client.render.camera;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HeldItemRenderer.class)
public class ViewModelSway {
    @WrapOperation(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/RotationAxis;rotationDegrees(F)Lorg/joml/Quaternionf;", ordinal = 0))
        private Quaternionf handSwayVertical(RotationAxis instance, float deg, Operation<Quaternionf> original){
            return instance.rotationDegrees((float) Math.tanh(deg/1.8)*10);
    }
    @WrapOperation(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;" +
            "Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"
            , at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/RotationAxis;rotationDegrees(F)Lorg/joml/Quaternionf;", ordinal = 1))
    private Quaternionf handSwayHorizontal(RotationAxis instance, float deg, Operation<Quaternionf> original){
        return instance.rotationDegrees((float) Math.tanh(deg/1.8)*10);
    }
}
