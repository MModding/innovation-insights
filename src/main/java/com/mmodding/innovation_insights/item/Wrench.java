package com.mmodding.innovation_insights.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class Wrench extends Item {

    public Wrench(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOnBlock(UseOnContext context) {
        Block block = context.getLevel().getBlockState(context.getClickedPos()).getBlock();

        if (context.getPlayer() != null) {
			context.getPlayer().displayClientMessage(Component.nullToEmpty(block.getName().getString()), true);
		}

        return super.useOnBlock(context);
    }
}
