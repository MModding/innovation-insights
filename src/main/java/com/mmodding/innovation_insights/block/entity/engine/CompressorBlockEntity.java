package com.mmodding.innovation_insights.block.entity.engine;

import com.mmodding.innovation_insights.crafting.DualRecipeInput;
import com.mmodding.innovation_insights.init.IIBlockEntityTypes;
import com.mmodding.innovation_insights.init.IIRecipeTypes;
import com.mmodding.innovation_insights.menu.engine.CompressorMenu;
import com.mmodding.innovation_insights.recipe.Compression;
import com.mmodding.library.energy.api.block.entity.BaseContainerEnergyBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;

public class CompressorBlockEntity extends BaseContainerEnergyBlockEntity {

	private final RecipeManager.CachedCheck<DualRecipeInput, Compression> quickCheck;

    private int compressionTime;

    public CompressorBlockEntity(BlockPos pos, BlockState state) {
	    super(IIBlockEntityTypes.COMPRESSOR_ENTITY, pos, state);
	    this.quickCheck = RecipeManager.createCheck(IIRecipeTypes.COMPRESSION);
    }

    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

	@Override
	protected void setItems(NonNullList<ItemStack> items) {
		this.items = items;
	}

	@Override
    public void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
        this.compressionTime = input.getIntOr("compressionTime", 0);
    }

    @Override
    public void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
        output.putInt("compressionTime", this.compressionTime);
        ContainerHelper.saveAllItems(output, this.items);
        super.saveAdditional(output);
    }

	@Override
	protected Component getDefaultName() {
		return Component.nullToEmpty("Compressor");
	}

	@Override
	protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
		return new CompressorMenu(containerId, inventory, this);
	}

	@Override
	public int getContainerSize() {
		return 3;
	}

	public static void tick(Level level, BlockPos blockPos, BlockState blockState, CompressorBlockEntity blockEntity) {
		if (!(level instanceof ServerLevel serverLevel)) return;

		Optional<RecipeHolder<Compression>> match = blockEntity.quickCheck.getRecipeFor(new DualRecipeInput(blockEntity.getItem(1), blockEntity.getItem(2)), serverLevel);

		match.ifPresent(holder -> {
			Compression foundRecipe = holder.value();

			if (blockEntity.compressionTime >= foundRecipe.getCompressionTime()) {
				ItemStack currentOutput = blockEntity.getItem(0);
				ItemStack newOutput = foundRecipe.getResultItem();

				if (ItemStack.isSameItem(currentOutput, newOutput) || currentOutput.isEmpty() && currentOutput.getCount() <= currentOutput.getMaxStackSize() - newOutput.getCount()) {
					blockEntity.removeItem(1, 1);
					blockEntity.removeItem(2, 1);

					blockEntity.setItem(0, new ItemStack(foundRecipe.getResultItem().copy().getItem(), blockEntity.getItem(0).getCount() + 1));

					level.playSound(null, blockPos, SoundEvents.ANVIL_DESTROY, SoundSource.BLOCKS, 1.0f, 1.0f);
				}
			}
		});
	}
}
