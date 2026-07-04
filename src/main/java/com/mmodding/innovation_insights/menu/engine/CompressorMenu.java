package com.mmodding.innovation_insights.menu.engine;

import com.mmodding.innovation_insights.init.IIScreenHandlers;
import com.mmodding.library.inventory.api.menu.BasicInventoryAccessContainerMenu;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CompressorMenu extends BasicInventoryAccessContainerMenu {

    public CompressorMenu(int containerId, Inventory playerInventory, Container inventory) {
        super(IIScreenHandlers.COMPRESSOR, 3, containerId, playerInventory, inventory);

        this.addSlot(new Slot(this.container, 0, 80,49) { @Override public boolean mayPlace(ItemStack stack) { return false; } });
        this.addSlot(new Slot(this.container, 1, 10, 10));
        this.addSlot(new Slot(this.container, 2, 150, 10));
    }

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		return null;
	}
}
