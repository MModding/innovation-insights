package com.mmodding.innovation_insights.menu.engine;

import com.mmodding.innovation_insights.init.IIScreenHandlers;
import com.mmodding.mmodding_lib.library.screenhandlers.BasicScreenHandler;
import com.mmodding.mmodding_lib.library.screenhandlers.slots.OutputSlot;
import com.mmodding.mmodding_lib.library.utils.ScreenHandlerUtils;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class ExtractorScreenHandler extends BasicScreenHandler {

    public ExtractorScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(7), 7);
    }

    public ExtractorScreenHandler(int syncID, Inventory playerInventory, Container inventory, int inventorySize) {
        super(IIScreenHandlers.EXTRACTOR_HANDLER, syncID, playerInventory, inventory, inventorySize);

        this.addSlot(new Slot(this.inventory, 0, 17,34));
        this.addSlot(new OutputSlot(this.inventory, 1, 54, 34));
        this.addSlot(new OutputSlot(this.inventory, 2, 72, 34));
        this.addSlot(new OutputSlot(this.inventory, 3, 90, 34));
        this.addSlot(new OutputSlot(this.inventory, 4, 108, 34));
        this.addSlot(new OutputSlot(this.inventory, 5, 126, 34));
        this.addSlot(new OutputSlot(this.inventory, 6, 144, 34));

		ScreenHandlerUtils.createPlayerSlots(this, playerInventory);
    }
}
