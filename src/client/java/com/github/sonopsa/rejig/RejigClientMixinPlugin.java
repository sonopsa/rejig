package com.github.sonopsa.rejig;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

// https://github.com/ArkoSammy12/Aquifer/blob/main/src/main/java/xd/arkosammy/aquifer/AquiferMixinPlugin.java
// example mixin plugin

public class RejigClientMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        if (mixinClassName.equals("com.github.sonopsa.rejig.mixin.client.render.particle.ParticlesFaceCamera") && FabricLoader.getInstance().isModLoaded("sodium")) {
            return false;
        }

        if (mixinClassName.startsWith("com.github.sonopsa.rejig.mixin.client.sodium") && !FabricLoader.getInstance().isModLoaded("sodium")) {
            return false;
        }

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}