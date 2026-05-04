package com.mmodding.innovation_insights.block.generator;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.block.entity.generator.AnvilFissionGeneratorEntity;
import com.mmodding.innovation_insights.init.IIBlockEntities;
import com.mmodding.mmodding_lib.library.blocks.CustomBlockWithEntity;
import com.mmodding.mmodding_lib.library.blocks.interactions.FallingBlockInteraction;
import com.mmodding.mmodding_lib.library.blocks.interactions.data.FallingBlockInteractionData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class AnvilFissionGenerator extends CustomBlockWithEntity implements FallingBlockInteraction {

    public AnvilFissionGenerator(Settings settings, boolean hasItem, CreativeModeTab itemGroup) {
        super(settings, hasItem, itemGroup);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AnvilFissionGeneratorEntity(pos, state);
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
            if (blockEntity instanceof AnvilFissionGeneratorEntity) {
                Containers.dropContents(world, pos, (AnvilFissionGeneratorEntity) blockEntity);
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

        world.scheduleTick(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), this, 1);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, Level world, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
    }

	@Override
	public void onFallingBlockInteract(FallingBlockInteractionData data) {
		if (data.getFallingBlockState().getBlock() instanceof AnvilBlock) {
			data.getWorld().getBlockEntity(
				data.getInteractPos(),
				IIBlockEntities.ANVIL_FISSION_GENERATOR_ENTITY.getBlockEntityTypeIfCreated()
			).ifPresent(blockEntity -> blockEntity.triggerFission(data));
		}
	}

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return !world.isClientSide() ? checkType(
            type,
            IIBlockEntities.ANVIL_FISSION_GENERATOR_ENTITY.getBlockEntityTypeIfCreated(),
            (a, b, c, blockEntity) -> blockEntity.tick()
        ) : null;
    }
}
