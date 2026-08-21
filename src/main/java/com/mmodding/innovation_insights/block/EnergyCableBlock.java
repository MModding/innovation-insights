package com.mmodding.innovation_insights.block;

import com.mmodding.innovation_insights.core.PipeMode;
import com.mmodding.innovation_insights.energy.access.EnergyCableAccess;
import com.mmodding.innovation_insights.init.IIBlocks;
import com.mmodding.library.energy.api.block.BlockEnergy;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class EnergyCableBlock extends PipeBlock {

	public static final MapCodec<EnergyCableBlock> CODEC = simpleCodec(EnergyCableBlock::new);

	public static final EnumProperty<PipeMode> MODE = EnumProperty.create("pipe_mode", PipeMode.class);

	/* public static final BooleanProperty EXPOSED_NORTH = BooleanProperty.create("exposed_north");
	public static final BooleanProperty EXPOSED_EAST = BooleanProperty.create("exposed_east");
	public static final BooleanProperty EXPOSED_SOUTH = BooleanProperty.create("exposed_south");
	public static final BooleanProperty EXPOSED_WEST = BooleanProperty.create("exposed_west");
	public static final BooleanProperty EXPOSED_UP = BooleanProperty.create("exposed_up");
	public static final BooleanProperty EXPOSED_DOWN = BooleanProperty.create("exposed_down");

	public static final Map<Direction, BooleanProperty> EXPOSURE_BY_DIRECTION = ImmutableMap.copyOf(Maps.newEnumMap(Map.of(
		Direction.NORTH, EXPOSED_NORTH,
		Direction.EAST, EXPOSED_EAST,
		Direction.SOUTH, EXPOSED_SOUTH,
		Direction.WEST, EXPOSED_WEST,
		Direction.UP, EXPOSED_UP,
		Direction.DOWN, EXPOSED_DOWN
	))); */

	public EnergyCableBlock(Properties properties) {
		super(4.0f, properties);
		this.registerDefaultState(
			this.defaultBlockState()
				.setValue(NORTH, false)/* .setValue(EXPOSED_NORTH, false) */
				.setValue(EAST, false)/* .setValue(EXPOSED_EAST, false) */
				.setValue(SOUTH, false)/* .setValue(EXPOSED_SOUTH, false) */
				.setValue(WEST, false)/* .setValue(EXPOSED_WEST, false) */
				.setValue(UP, false)/* .setValue(EXPOSED_UP, false) */
				.setValue(DOWN, false)/* .setValue(EXPOSED_DOWN, false) */
		);
		BlockEnergy.defineEnergyDelegate(this, (level, pos, state, _, side) -> {
			if (state.getValue(PROPERTY_BY_DIRECTION.get(side))) {
				return new EnergyCableAccess(level, pos, state.getValue(MODE));
			}
			else {
				return null;
			}
		});
	}

	@Override
	protected MapCodec<? extends PipeBlock> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
		// builder.add(EXPOSED_NORTH, EXPOSED_EAST, EXPOSED_SOUTH, EXPOSED_WEST, EXPOSED_UP, EXPOSED_DOWN);
		builder.add(MODE);
	}

	/* @Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(EXPOSURE_BY_DIRECTION.get(context.getNearestLookingDirection().getOpposite()), true);
	} */

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
		BlockState resulting = state;
		if (level instanceof ServerLevel serverLevel) {
			resulting = state.setValue(PROPERTY_BY_DIRECTION.get(directionToNeighbour), neighbourState.is(this) || neighbourState.is(IIBlocks.CREATIVE_ENERGY_PROVIDER) || (!(neighbourState.getBlock() instanceof EnergyCableBlock) && BlockEnergy.query(serverLevel, neighbourPos, directionToNeighbour.getOpposite()) != null));
		}
		return resulting;
	}
}
