package com.github.sonopsa.rejig.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.item.BundleItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BundleItem.class)
public class LargerBundleCapacity {
    @Shadow @Final @Mutable
    public static int MAX_STORAGE;

    @Inject(method = "<clinit>", at = @At("HEAD"))
    private static void injected(CallbackInfo ci) {
        MAX_STORAGE *= 3;
    }

    @ModifyExpressionValue(method = "onStackClicked", at = @At(value = "CONSTANT", args = {"intValue=64"},ordinal = 0))
    private int rightClickFix(int original){
        return original*3;
    }

    @ModifyExpressionValue(method = "getItemBarStep", at = @At(value = "CONSTANT", args = {"intValue=64"},ordinal = 0))
    private int bundleBarSize(int original){
        return original*3;
    }

    @ModifyExpressionValue(method = "addToBundle", at = @At(value = "CONSTANT", args = {"intValue=64"},ordinal = 0))
    private static int bundleInputCheckSize(int original){
        return original*3;
    }

    @ModifyExpressionValue(method = "appendTooltip", at = @At(value = "CONSTANT", args = {"intValue=64"},ordinal = 0))
    private int largerTooltipNumber(int original){
        return original*3;
    }
}
