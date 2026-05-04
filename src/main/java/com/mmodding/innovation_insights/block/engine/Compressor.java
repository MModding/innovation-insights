package com.mmodding.innovation_insights.block.engine;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.block.entity.engine.CompressorEntity;
import com.mmodding.innovation_insights.init.IIRecipeTypes;
import com.mmodding.innovation_insights.recipes.Compression;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.*;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Compressor extends BaseEntityBlock implements EntityBlock {

    public Compressor(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CompressorEntity(pos, state);
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (InnovationInsights.excludeBasics(player.getItemInHand(hand))) {
            if (!world.isClientSide()) {
                MenuProvider screenHandlerFactory = state.getMenuProvider(world, pos);

                if (screenHandlerFactory != null) {
                    player.openMenu(screenHandlerFactory);
                }
            }
            return InteractionResult.SUCCESS;
        }
        else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public void onStateReplaced(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CompressorEntity) {
                Containers.dropContents(world, pos, (CompressorEntity) blockEntity);
                world.updateNeighbourForOutputSignal(pos, this);
            }
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

        CompressorEntity compressorEntity = (CompressorEntity) world.getBlockEntity(pos);

        assert compressorEntity != null;

        CompoundTag nbt = new CompoundTag();
		compressorEntity.saveAdditional(nbt);

        int compressionTime = nbt.getInt("compressionTime") + 1;
        nbt.putInt("compressionTime", compressionTime);
        compressorEntity.load(nbt);

        Optional<Compression> match = world.getRecipeManager().getRecipeFor(IIRecipeTypes.COMPRESSION, compressorEntity, world);

        if (match.isPresent() && compressionTime >= match.get().getCompressionTime()) {
			ItemStack actualStack = compressorEntity.getStack(0);
			ItemStack outputStack = match.get().getResultItem();

            if (actualStack.sameItemStackIgnoreDurability(outputStack) || actualStack.isEmpty() && actualStack.getCount() <= actualStack.getMaxStackSize() - outputStack.getCount()) {

                CompoundTag resetCompressionTimeNbt = new CompoundTag();
				compressorEntity.saveAdditional(resetCompressionTimeNbt);

				resetCompressionTimeNbt.putInt("compressionTime", 0);
                compressorEntity.load(resetCompressionTimeNbt);

                compressorEntity.removeStack(1, 1);
                compressorEntity.removeStack(2, 1);

                compressorEntity.setStack(0, new ItemStack(match.get().getResultItem().copy().getItem(), compressorEntity.getStack(0).getCount() + 1));

                world.playSound(null, pos, SoundEvents.ANVIL_DESTROY, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }

        world.scheduleTick(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), this, 1);
    }

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite());
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }
}
