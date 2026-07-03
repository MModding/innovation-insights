package com.mmodding.innovation_insights.block.entity.engine;

import com.mmodding.innovation_insights.init.IIBlockEntities;
import com.mmodding.innovation_insights.inventories.ImplementedInventory;
import com.mmodding.innovation_insights.menu.engine.ExtractorScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class ExtractorEntity extends BlockEntity implements InnovationEnergyFluxOld.Container, MenuProvider, ImplementedInventory {

    private final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(100000L, 10000L, 10000L);

    private int extractionTime;

    public ExtractorEntity(BlockPos pos, BlockState state) {
        super(IIBlockEntities.EXTRACTOR_ENTITY.getBlockEntityTypeIfCreated(), pos, state);
    }

    private NonNullList<ItemStack> items = NonNullList.withSize(7, ItemStack.EMPTY);

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.readIEF(nbt);
        this.items = NonNullList.withSize(this.size(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.items);
        this.extractionTime = nbt.getInt("extractionTime");
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        this.writeIEF(nbt);
        nbt.putInt("extractionTime", this.extractionTime);
        ContainerHelper.saveAllItems(nbt, items);
        super.saveAdditional(nbt);
    }

    @Override
    public Component getDisplayName() {
        return Component.nullToEmpty("Extractor");
    }

    public int getExtractionTime() {
        return this.extractionTime;
    }

    @Override
    public SimpleEnergyStorage getEnergyStorage() {
        return this.energyStorage;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new ExtractorScreenHandler(syncId, inv, this, 7);
    }
}
