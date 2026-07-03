package com.mmodding.innovation_insights.block.engine;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.block.entity.engine.ExtractorEntity;
import com.mmodding.innovation_insights.init.IIRecipeTypes;
import com.mmodding.innovation_insights.recipes.Extraction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.*;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.stream.IntStream;

public class Extractor extends CustomBlockWithEntity implements EntityBlock {

    public Extractor(Settings settings, boolean hasItem, CreativeModeTab itemGroup) {
        super(settings, hasItem, itemGroup);
        this.setDefaultState(this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ExtractorEntity(pos, state);
    }

    @Override
    public RenderShape getRenderType(BlockState state) {
        return RenderShape.MODEL;
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
            if (blockEntity instanceof ExtractorEntity) {
                Containers.dropContents(world, pos, (ExtractorEntity) blockEntity);
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

	private ItemStack getOutputForSlot(Extraction extraction, int slotIndex) {
		return switch (slotIndex) {
			default -> extraction.getDefaultOutput().stack();
			case 2 -> extraction.getFirstAdditionalOutput().stack();
			case 3 -> extraction.getSecondAdditionalOutput().stack();
			case 4 -> extraction.getThirdAdditionalOutput().stack();
			case 5 -> extraction.getFourthAdditionalOutput().stack();
			case 6 -> extraction.getFifthAdditionalOutput().stack();
		};
	}

	private int getOutputLuckForSlot(Extraction extraction, int slotIndex) {
		return switch (slotIndex) {
			default -> extraction.getFirstAdditionalOutput().luck();
			case 3 -> extraction.getSecondAdditionalOutput().luck();
			case 4 -> extraction.getThirdAdditionalOutput().luck();
			case 5 -> extraction.getFourthAdditionalOutput().luck();
			case 6 -> extraction.getFifthAdditionalOutput().luck();
		};
	}

	private boolean isSlotValid(ExtractorEntity extractorEntity, Extraction extraction, int slotIndex) {
		ItemStack actualStack = extractorEntity.getStack(slotIndex);
		ItemStack outputStack = this.getOutputForSlot(extraction, slotIndex);

		if (actualStack.sameItemStackIgnoreDurability(outputStack) || actualStack.isEmpty()) {
			return actualStack.getCount() <= actualStack.getMaxStackSize() - outputStack.getCount();
		}
		else {
			return false;
		}
	}

	private boolean areSlotsValid(ExtractorEntity extractorEntity, Extraction extraction) {
		for (int slotIndex : IntStream.range(1, 7).toArray()) {
			if (!this.isSlotValid(extractorEntity, extraction, slotIndex)) {
				return false;
			}
		}
		return true;
	}

	private boolean isLuckValid(RandomSource random, Extraction extraction, int slotIndex) {
		return random.nextInt(100) < this.getOutputLuckForSlot(extraction, slotIndex);
	}

	private void createResult(RandomSource random, ExtractorEntity extractorEntity, Extraction extraction, int slotIndex) {
		ItemStack actualStack = extractorEntity.getStack(slotIndex);
		ItemStack outputStack = this.getOutputForSlot(extraction, slotIndex);

		ItemStack resultStack = new ItemStack(outputStack.getItem(), actualStack.getCount() + outputStack.getCount());

		if (slotIndex != 1) {
			if (this.isLuckValid(random, extraction, slotIndex)) {
				extractorEntity.setStack(slotIndex, resultStack);
			}
		}
		else {
			extractorEntity.setStack(slotIndex, resultStack);
		}
	}

	private void createResults(RandomSource random, ExtractorEntity extractorEntity, Extraction extraction) {
		extractorEntity.removeStack(0, 1);

		for (int slotIndex : IntStream.range(1, 7).toArray()) {
			this.createResult(random, extractorEntity, extraction, slotIndex);
		}
	}

	@Override
    public void scheduledTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.scheduledTick(state, world, pos, random);

        ExtractorEntity extractorEntity = (ExtractorEntity) world.getBlockEntity(pos);

        assert extractorEntity != null;

        CompoundTag nbt = new CompoundTag();
		extractorEntity.saveAdditional(nbt);

        int extractionTime = nbt.getInt("extractionTime") + 1;
        nbt.putInt("extractionTime", extractionTime);
        extractorEntity.load(nbt);

        Optional<Extraction> match = world.getRecipeManager().getRecipeFor(IIRecipeTypes.EXTRACTION, extractorEntity, world);

        if (match.isPresent()) {
            if (extractionTime >= match.get().getExtractionTime()) {
				if (this.areSlotsValid(extractorEntity, match.get())) {

					CompoundTag resetExtractionTimeNbt = new CompoundTag();
					extractorEntity.saveAdditional(resetExtractionTimeNbt);

					resetExtractionTimeNbt.putInt("extractionTime", 0);
					extractorEntity.load(resetExtractionTimeNbt);

					this.createResults(random, extractorEntity, match.get());
				}
            }
        }

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
	protected void appendProperties(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(BlockStateProperties.HORIZONTAL_FACING);
	}

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, Level world, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
    }
}
