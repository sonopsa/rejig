package com.github.sonopsa.rejig.mixin.block.lightFiltering;

import net.minecraft.block.BeaconBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BeaconBlock.class)
public abstract class BeaconLightFiltering extends Block {

    public BeaconLightFiltering(Settings settings) {
        super(settings);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos p) {
        return 14;
    }
}