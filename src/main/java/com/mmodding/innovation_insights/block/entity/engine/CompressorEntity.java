package com.mmodding.innovation_insights.block.entity.engine;

import com.mmodding.innovation_insights.init.IIBlockEntities;
import com.mmodding.innovation_insights.inventories.ImplementedInventory;
import com.mmodding.innovation_insights.menu.engine.CompressorScreenHandler;
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

public class CompressorEntity extends BlockEntity implements InnovationEnergyFluxOld.Container, MenuProvider, ImplementedInventory {

    private final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(10000L, 10000L, 10000L);

    private int compressionTime;

    public CompressorEntity(BlockPos pos, BlockState state) {
        super(IIBlockEntities.COMPRESSOR_ENTITY.getBlockEntityTypeIfCreated(), pos, state);
    }

    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

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
        this.compressionTime = nbt.getInt("compressionTime");
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        this.writeIEF(nbt);
        nbt.putInt("compressionTime", this.compressionTime);
        ContainerHelper.saveAllItems(nbt, this.items);
        super.saveAdditional(nbt);
    }

    @Override
    public Component getDisplayName() {
        return Component.nullToEmpty("Compressor");
    }

    public int getCompressionTime() {
        return compressionTime;
    }

    @Override
    public SimpleEnergyStorage getEnergyStorage() {
        return this.energyStorage;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new CompressorScreenHandler(syncId, inv, this, 3);
    }
}
