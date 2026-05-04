package com.mmodding.innovation_insights;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.function.Consumer;

public class InnovationEnergyFlux {

	public static void findStorageForDirections(Level world, BlockPos blockPos, Direction.Plane type, Consumer<EnergyStorage> energyStorageConsumer) {
		type.forEach(direction -> Optional.ofNullable(EnergyStorage.SIDED.find(world, blockPos.relative(direction), direction)).ifPresent(energyStorageConsumer));
	}

    public static EnergyStorage findItemStorage(ItemStack stack, net.minecraft.world.Container inventory, int index) {
        Optional<EnergyStorage> storage = Optional.ofNullable(EnergyStorage.ITEM.find(
            stack, ContainerItemContext.ofSingleSlot(InventoryStorage.of(inventory, null).getSlots().get(index))
        ));
        if (storage.isPresent()) {
            return storage.get();
        }
        else {
            throw new IllegalArgumentException("Stack does not contain any Energy Storage");
        }
    }

    public static void transfer(EnergyStorage from, EnergyStorage to, long value) {
        try (Transaction transaction = Transaction.openOuter()) {
            long extracted = from.extract(value, transaction);
            long inserted = to.insert(extracted, transaction);
            if (extracted == inserted) {
                transaction.commit();
            }
            else if (extracted > inserted) {
                from.insert(extracted - inserted, transaction);
                transaction.commit();
            }
        }
    }

    public interface Container {

        SimpleEnergyStorage getEnergyStorage();

        default long getIEF() {
            return this.getEnergyStorage().amount;
        }

        default void setIEF(long value) {
            this.getEnergyStorage().amount = Math.max(Math.min(value, this.getEnergyStorage().capacity), 0);
        }

        default void addIEF(long value) {
            this.getEnergyStorage().amount += Math.min(value, this.getEnergyStorage().capacity - this.getEnergyStorage().amount);
        }

        default void removeIEF(long value) {
            this.getEnergyStorage().amount -= Math.min(value, this.getEnergyStorage().amount);
        }

        default void transferTo(InnovationEnergyFlux.Container IEF, long value) {
            InnovationEnergyFlux.transfer(this.getEnergyStorage(), IEF.getEnergyStorage(), value);
        }

        default void transferTo(ItemStack stack, net.minecraft.world.Container inventory, int index, long value) {
            EnergyStorage storage = InnovationEnergyFlux.findItemStorage(stack, inventory, index);
            InnovationEnergyFlux.transfer(this.getEnergyStorage(), storage, value);
        }

        default long getCapacity() {
            return this.getEnergyStorage().capacity;
        }

        default void readIEF(CompoundTag nbt) {
            this.setIEF(nbt.getLong("IEF"));
        }

        default void writeIEF(CompoundTag nbt) {
            nbt.putLong("IEF", this.getIEF());
        }
    }

    public interface Item extends SimpleEnergyItem {

        default long getIEF(ItemStack stack) {
            return this.getStoredEnergy(stack);
        }

        default void setIEF(ItemStack stack, long value) {
            this.setStoredEnergy(stack, Math.max(Math.min(value, this.getEnergyCapacity(stack)), 0));
        }

        default void addIEF(ItemStack stack, long value) {
            this.setStoredEnergy(stack, this.getStoredEnergy(stack) + Math.min(value, this.getEnergyCapacity(stack) - this.getStoredEnergy(stack)));
        }

        default void removeIEF(ItemStack stack, long value) {
            this.setStoredEnergy(stack, this.getStoredEnergy(stack) - Math.min(value, this.getStoredEnergy(stack)));
        }

        default void transferTo(ItemStack stack, net.minecraft.world.Container inventory, int index, InnovationEnergyFlux.Container IEF, long value) {
            EnergyStorage storage = InnovationEnergyFlux.findItemStorage(stack, inventory, index);
            InnovationEnergyFlux.transfer(storage, IEF.getEnergyStorage(), value);
        }

        default void transferTo(ItemStack sourceStack, net.minecraft.world.Container sourceInventory, int sourceIndex, ItemStack targetStack, net.minecraft.world.Container targetInventory, int targetIndex, long value) {
            EnergyStorage fromStorage = InnovationEnergyFlux.findItemStorage(sourceStack, sourceInventory, sourceIndex);
            EnergyStorage toStorage = InnovationEnergyFlux.findItemStorage(targetStack, targetInventory, targetIndex);
            InnovationEnergyFlux.transfer(fromStorage, toStorage, value);
        }

        default long getCapacity(ItemStack stack) {
            return this.getEnergyCapacity(stack);
        }

        default boolean isIEFVisible(ItemStack stack) {
            return this.getIEF(stack) > 0;
        }

        default int getIEFStep(ItemStack stack) {
            return Math.round(13.0f - (this.getCapacity(stack) - this.getIEF(stack)) * 13.0f / this.getCapacity(stack));
        }

        default Colors.RGB getIEFColor(ItemStack stack) {
            int alteration = (int) ((double) this.getIEF(stack) / this.getCapacity(stack) * 60);
            Colors.RGB rgb = new Colors.RGB(120, 15, 245);
            rgb.alterRed(Math.negateExact(alteration));
            rgb.alterGreen(alteration);
            return rgb;
        }
    }
}
