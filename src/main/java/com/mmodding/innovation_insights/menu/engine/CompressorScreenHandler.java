package com.mmodding.innovation_insights.menu.engine;

import com.mmodding.innovation_insights.init.IIScreenHandlers;
import com.mmodding.mmodding_lib.library.screenhandlers.BasicScreenHandler;
import com.mmodding.mmodding_lib.library.screenhandlers.slots.OutputSlot;
import com.mmodding.mmodding_lib.library.utils.ScreenHandlerUtils;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class CompressorScreenHandler extends BasicScreenHandler {

    public CompressorScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(3), 3);
    }

    public CompressorScreenHandler(int syncID, Inventory playerInventory, Container inventory, int inventorySize) {
        super(IIScreenHandlers.COMPRESSOR_HANDLER, syncID, playerInventory, inventory, inventorySize);

        this.addSlot(new OutputSlot(this.inventory, 0, 80,49));
        this.addSlot(new Slot(this.inventory, 1, 10, 10));
        this.addSlot(new Slot(this.inventory, 2, 150, 10));

		ScreenHandlerUtils.createPlayerSlots(this, playerInventory);
    }
}
