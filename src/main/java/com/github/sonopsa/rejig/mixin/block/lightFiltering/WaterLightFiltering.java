package com.github.sonopsa.rejig.mixin.block.lightFiltering;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FluidBlock.class)
public class WaterLightFiltering extends Block {
    @Shadow
    @Final
    protected FlowableFluid fluid;

    public WaterLightFiltering(Settings settings) {
        super(settings);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos p) {
        return this.fluid.isIn(FluidTags.WATER) ? 2 : super.getOpacity(state, world, p);
    }
}