package com.github.sonopsa.rejig.mixin.block.lightFiltering;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IceBlock.class)
public abstract class IceLightFiltering extends Block {

    public IceLightFiltering(Settings settings) {
        super(settings);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos p) {
        return 3;
    }
}