package com.mmodding.innovation_insights.block;

import com.mmodding.innovation_insights.block.entity.CreativeEnergyProviderBlockEntity;
import com.mmodding.innovation_insights.init.IIBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class CreativeEnergyProviderBlock extends BaseEntityBlock {

	public CreativeEnergyProviderBlock(Properties properties) {
		super(properties);
	}

	@Override
	@Nullable
	public BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
		return new CreativeEnergyProviderBlockEntity(worldPosition, blockState);
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
		return createTickerHelper(type, IIBlockEntityTypes.CREATIVE_ENERGY_PROVIDER, CreativeEnergyProviderBlockEntity::tick);
	}
}
