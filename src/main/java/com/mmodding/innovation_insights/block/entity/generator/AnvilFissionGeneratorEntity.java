package com.mmodding.innovation_insights.block.entity.generator;

import com.mmodding.innovation_insights.init.IIBlockEntities;
import com.mmodding.innovation_insights.init.IIItems;
import com.mmodding.innovation_insights.init.IITags;
import com.mmodding.innovation_insights.inventories.ImplementedInventory;
import com.mmodding.innovation_insights.menu.generator.AnvilFissionGeneratorScreenHandler;
import com.mmodding.mmodding_lib.library.blocks.interactions.data.FallingBlockInteractionData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
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

public class AnvilFissionGeneratorEntity extends BlockEntity implements InnovationEnergyFluxOld.Container, MenuProvider, ImplementedInventory {

	private final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(100000L, 100000L, 100000L);

    public AnvilFissionGeneratorEntity(BlockPos pos, BlockState state) {
        super(IIBlockEntities.ANVIL_FISSION_GENERATOR_ENTITY.getBlockEntityTypeIfCreated(), pos, state);
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

    public void tick() {
        ItemStack stack = this.items.get(0);
        if (stack.is(IITags.BATTERIES)) {
            this.transferTo(stack, this, 0, 1000);
        }
		else {
			InnovationEnergyFluxOld.findStorageForDirections(
                this.getLevel(), this.getBlockPos(), Direction.Plane.HORIZONTAL, storage -> InnovationEnergyFluxOld.transfer(this.getEnergyStorage(), storage, 1000)
            );
		}
    }

	public void triggerFission(FallingBlockInteractionData data) {
		ItemStack debris = this.items.get(1);
		ItemStack fragments = this.items.get(2);

		if (debris.getCount() < debris.getMaxStackSize() && fragments.getCount() < fragments.getMaxStackSize()) {
			this.addIEF((long) data.getFallHurtAmount() * 1000L);

			if (data.isDestroyedOnLanding()) {
				int count = Mth.clamp(0, fragments.getCount() + 1, fragments.getMaxStackSize());
				this.items.set(2, new ItemStack(IIItems.ANVIL_FRAGMENTS, count));
			}
			else if (!data.getCurrentBlockState().isOf(data.getInitialBlockState().getBlock())) {
				int count = Mth.clamp(0, debris.getCount() + 1, debris.getMaxStackSize());
				this.items.set(1, new ItemStack(IIItems.ANVIL_DEBRIS, count));
			}
		}
	}

    @Override
    public void load(CompoundTag nbt) {
		this.readIEF(nbt);
        this.items = NonNullList.withSize(this.size(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.items);
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
		this.writeIEF(nbt);
		ContainerHelper.saveAllItems(nbt, this.items);
		super.saveAdditional(nbt);
    }

    @Override
    public Component getDisplayName() {
        return Component.nullToEmpty("Anvil Fission Generator");
    }

	public SimpleEnergyStorage getEnergyStorage() {
		return this.energyStorage;
	}

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new AnvilFissionGeneratorScreenHandler(syncId, inv, this, 3);
    }
}
