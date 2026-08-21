package com.mmodding.innovation_insights.block.entity;

import com.mmodding.innovation_insights.energy.InnovationEnergyFlux;
import com.mmodding.innovation_insights.init.IIBlockEntityTypes;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.api.block.BlockEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CreativeEnergyProviderBlockEntity extends BlockEntity {

	public final EnergyAccess energyAccess = EnergyAccess.infinite(InnovationEnergyFlux.UNIT);

	public CreativeEnergyProviderBlockEntity(BlockPos worldPosition, BlockState blockState) {
		super(IIBlockEntityTypes.CREATIVE_ENERGY_PROVIDER, worldPosition, blockState);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, CreativeEnergyProviderBlockEntity blockEntity) {
		if (!(level instanceof ServerLevel serverLevel)) return;

		Direction.stream().forEach(direction -> {
			EnergyAccess targeted = BlockEnergy.query(serverLevel, pos.relative(direction), direction.getOpposite());
			blockEntity.energyAccess.transferTo(targeted, Long.MAX_VALUE, null);
		});
	}
}
