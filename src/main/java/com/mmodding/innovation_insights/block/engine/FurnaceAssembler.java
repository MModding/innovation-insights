package com.mmodding.innovation_insights.block.engine;

import com.mmodding.mmodding_lib.library.blocks.CustomBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

public class FurnaceAssembler extends CustomBlock/* WithEntity */ {

    public FurnaceAssembler(Settings settings, boolean hasItem, CreativeModeTab itemGroup) {
        super(settings, hasItem, itemGroup);
        this.setDefaultState(this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

//	@Nullable
//	@Override
//	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
//		return new FurnaceAssemblerEntity(pos, state);
//	}
//
//    @Override
//    public BlockRenderType getRenderType(BlockState state) {
//        return BlockRenderType.MODEL;
//    }
//
//    @Override
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        if (InnovationInsights.excludeBasics(player.getStackInHand(hand))) {
//            if (!world.isClient()) {
//                NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
//
//                if (screenHandlerFactory != null) {
//                    player.openHandledScreen(screenHandlerFactory);
//                }
//            }
//            return ActionResult.SUCCESS;
//        }
//        else {
//            return ActionResult.PASS;
//        }
//    }

    @Override
    public void onStateReplaced(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
//            BlockEntity blockEntity = world.getBlockEntity(pos);
//            if (blockEntity instanceof FurnaceAssemblerEntity) {
//                ItemScatterer.spawn(world, pos, (FurnaceAssemblerEntity) blockEntity);
//                world.updateComparators(pos, this);
//            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        world.scheduleTick(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), this, 1);
    }

    @Override
    public void scheduledTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.scheduledTick(state, world, pos, random);

        world.scheduleTick(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), this, 1);
    }

	@Nullable
	@Override
	public BlockState getPlacementState(BlockPlaceContext ctx) {
		return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
	}

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

//    @Override
//    public boolean hasComparatorOutput(BlockState state) {
//        return true;
//    }
//
//    @Override
//    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
//        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
//    }
}
