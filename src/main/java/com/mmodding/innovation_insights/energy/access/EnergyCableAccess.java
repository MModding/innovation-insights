package com.mmodding.innovation_insights.energy.access;

import com.mmodding.innovation_insights.block.EnergyCableBlock;
import com.mmodding.innovation_insights.core.PipeMode;
import com.mmodding.innovation_insights.energy.InnovationEnergyFlux;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.api.access.catalog.CompilingEnergyAccess;
import com.mmodding.library.energy.api.block.BlockEnergy;
import com.mmodding.library.java.api.container.Pair;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class EnergyCableAccess extends CompilingEnergyAccess {

	private final ServerLevel level;
	private final BlockPos pos;
	private final PipeMode mode;

	public EnergyCableAccess(ServerLevel level, BlockPos pos, PipeMode mode) {
		super(InnovationEnergyFlux.UNIT);
		this.level = level;
		this.pos = pos;
		this.mode = mode;
	}

	// if this gets laggy: only actualizing at given (config, default like 5) tick intervals
	public void actualizeDelegates() {
		this.collection.clear();

		Block type = this.level.getBlockState(this.pos).getBlock();
		Queue<Pair<BlockPos, Direction>> processing = new ArrayDeque<>();
		processing.add(Pair.create(this.pos, null));

		Set<BlockPos> previouslyVisited = new HashSet<>(); previouslyVisited.add(this.pos);

		while (!processing.isEmpty()) {
			Pair<BlockPos, Direction> polled = processing.poll();
			BlockPos delegatingPos = polled.first();
			Direction sourcingDirection = polled.second();
			if (this.level.getBlockState(delegatingPos).is(type)) {
				for (Direction side : Direction.stream().filter(side -> this.level.getBlockState(delegatingPos).getValue(EnergyCableBlock.PROPERTY_BY_DIRECTION.get(side))).toList()) {
					if (!previouslyVisited.contains(delegatingPos.relative(side))) {
						processing.add(Pair.create(delegatingPos.relative(side), side.getOpposite()));
						previouslyVisited.add(delegatingPos);
					}
				}
			}
			else {
				EnergyAccess access = BlockEnergy.query(this.level, delegatingPos, sourcingDirection);
				if (access != null) {
					this.collection.add(access);
				}
			}
		}
	}

	public final long insertSubsequently(int index, long amount, TransactionContext context) {
		EnergyAccess targetingAccess = this.collection.get(index);
		long insertedInTarget = targetingAccess.insert(EnergyUnit.convert(amount, this.unit(), targetingAccess.unit()), context);
		return EnergyUnit.convert(insertedInTarget, targetingAccess.unit(), this.unit());
	}

	@Override
	public long insert(long amount, TransactionContext context) {
		this.actualizeDelegates();
		long remaining = amount;
		switch (this.mode) {
			case CLOSEST -> {
				int index = 0;
				while (remaining > 0 && index < this.collection.size()) {
					remaining = this.insertSubsequently(index, remaining, context);
					index++;
				}
			}
			case FURTHEST -> {
				int index = this.collection.size() - 1;
				while (remaining > 0 && index >= 0) {
					remaining = this.insertSubsequently(index, remaining, context);
					index--;
				}
			}
			case ROUND_ROBIN -> {
				int index = Math.toIntExact(this.level.getGameTime() % this.collection.size()); int stack = 0;
				while ((remaining = this.insertSubsequently(index, remaining, context)) > 0 && stack < this.collection.size()) {
					index = (index + 1) % this.collection.size();
					stack++;
				}
			}
		}
		return remaining;
	}

	public final long extractSubsequently(int index, long amount, TransactionContext context) {
		EnergyAccess targetingAccess = this.collection.get(index);
		long extractFromTarget = targetingAccess.extract(EnergyUnit.convert(amount, this.unit(), targetingAccess.unit()), context);
		return EnergyUnit.convert(extractFromTarget, targetingAccess.unit(), this.unit());
	}

	@Override
	public long extract(long amount, TransactionContext context) {
		this.actualizeDelegates();
		long remaining = amount;
		switch (this.mode) {
			case CLOSEST -> {
				int index = 0;
				while (remaining > 0 && index < this.collection.size()) {
					remaining = this.extractSubsequently(index, remaining, context);
					index++;
				}
			}
			case FURTHEST -> {
				int index = this.collection.size() - 1;
				while (remaining > 0 && index >= 0) {
					remaining = this.extractSubsequently(index, remaining, context);
					index--;
				}
			}
			case ROUND_ROBIN -> {
				int index = Math.toIntExact(this.level.getGameTime() % this.collection.size()); int stack = 0;
				while ((remaining = this.extractSubsequently(index, remaining, context)) > 0 && stack < this.collection.size()) {
					index = (index + 1) % this.collection.size();
					stack++;
				}
			}
		}
		return remaining;
	}
}
