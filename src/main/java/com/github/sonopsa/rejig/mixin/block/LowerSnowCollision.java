package com.github.sonopsa.rejig.mixin.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import static net.minecraft.block.SnowBlock.LAYERS;

@Mixin(SnowBlock.class)
public class LowerSnowCollision extends Block {
    @Shadow @Final protected static VoxelShape[] LAYERS_TO_SHAPE;

    public LowerSnowCollision(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[(int) (4*(Math.floor(state.get(LAYERS) / 4.0)))];
    }
}
