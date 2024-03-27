package com.github.sonopsa.rejig.mixin.block.lightFiltering;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BrewingStandBlock.class)
public abstract class BrewingStandLightFiltering extends Block {

    public BrewingStandLightFiltering(Settings settings) {
        super(settings);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos p) {
        return true ? 3 : super.getOpacity(state, world, p);
    }
}