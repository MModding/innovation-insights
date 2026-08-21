package com.mmodding.innovation_insights.item;

import com.mmodding.innovation_insights.energy.InnovationEnergyFlux;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.api.block.BlockEnergy;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class InnovationEnergyFluxMeter extends Item implements FabricItem {

	public InnovationEnergyFluxMeter(Item.Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getPlayer() != null && context.getLevel() instanceof ServerLevel serverLevel) {
			EnergyAccess checked = BlockEnergy.query(serverLevel, context.getClickedPos(), context.getClickedFace());
			if (checked != null) {
				context.getPlayer().sendOverlayMessage(Component.literal(EnergyUnit.convert(checked.amount(), checked.unit(), InnovationEnergyFlux.UNIT) + " IEF"));
				return InteractionResult.SUCCESS_SERVER;
			}
			else {
				return InteractionResult.FAIL;
			}
		}
		else {
			return InteractionResult.PASS;
		}
	}

	@Override
	public boolean allowComponentsUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
		return false;
	}
}
