package com.github.sonopsa.rejig.mixin.client.gui;

import com.github.sonopsa.rejig.RejigClient;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatHud.class)
public class ChatPeek {
    @Inject(method = "isChatFocused", at = @At("HEAD"), cancellable = true)
    private void isChatFocused(CallbackInfoReturnable<Boolean> cir) {
        if (RejigClient.chatPeekKeybind.isPressed()) {
            cir.setReturnValue(true);
        }
    }
}