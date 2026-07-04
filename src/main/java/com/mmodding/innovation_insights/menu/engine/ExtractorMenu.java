package com.mmodding.innovation_insights.menu.engine;

import com.mmodding.innovation_insights.init.IIScreenHandlers;
import com.mmodding.library.inventory.api.menu.BasicInventoryAccessContainerMenu;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ExtractorMenu extends BasicInventoryAccessContainerMenu {

	public ExtractorMenu(int containerId, Inventory inventory, Container container) {
		super(IIScreenHandlers.EXTRACTOR, 7, containerId, inventory, container);
		this.addSlot(new Slot(this.container, 0, 17,34));
		this.addSlot(new Slot(this.container, 1, 54, 34) { @Override public boolean mayPlace(ItemStack itemStack) { return false; } });
		this.addSlot(new Slot(this.container, 2, 72, 34) { @Override public boolean mayPlace(ItemStack itemStack) { return false; } });
		this.addSlot(new Slot(this.container, 3, 90, 34) { @Override public boolean mayPlace(ItemStack itemStack) { return false; } });
		this.addSlot(new Slot(this.container, 4, 108, 34) { @Override public boolean mayPlace(ItemStack itemStack) { return false; } });
		this.addSlot(new Slot(this.container, 5, 126, 34) { @Override public boolean mayPlace(ItemStack itemStack) { return false; } });
		this.addSlot(new Slot(this.container, 6, 144, 34) { @Override public boolean mayPlace(ItemStack itemStack) { return false; } });
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		return null;
	}
}
