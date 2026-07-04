package com.mmodding.innovation_insights.block.entity.engine;

import com.mmodding.innovation_insights.init.IIBlockEntityTypes;
import com.mmodding.innovation_insights.init.IIRecipeTypes;
import com.mmodding.innovation_insights.menu.engine.ExtractorMenu;
import com.mmodding.innovation_insights.recipe.Extraction;
import com.mmodding.library.energy.api.block.entity.BaseContainerEnergyBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;

public class ExtractorBlockEntity extends BaseContainerEnergyBlockEntity {

	private final RecipeManager.CachedCheck<SingleRecipeInput, Extraction> quickCheck;

    private int extractionTime;

    public ExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(IIBlockEntityTypes.EXTRACTOR_ENTITY, pos, state);
	    this.quickCheck = RecipeManager.createCheck(IIRecipeTypes.EXTRACTION);
    }

    private NonNullList<ItemStack> items = NonNullList.withSize(7, ItemStack.EMPTY);

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

	@Override
	protected void setItems(NonNullList<ItemStack> items) {
		this.items = items;
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(input, this.items);
		this.extractionTime = input.getIntOr("extractionTime", 0);
	}

	@Override
    public void saveAdditional(ValueOutput output) {
        output.putInt("extractionTime", this.extractionTime);
        ContainerHelper.saveAllItems(output, items);
        super.saveAdditional(output);
    }

	@Override
	protected Component getDefaultName() {
		return Component.nullToEmpty("Extractor");
	}

	@Override
	protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
		return new ExtractorMenu(containerId, inventory, this);
	}

	@Override
	public int getContainerSize() {
		return 2;
	}

	private static boolean isSlotValid(ExtractorBlockEntity extractorEntity, Extraction extraction, int slotIndex) {
		ItemStack currentStack = extractorEntity.getItem(slotIndex);
		ItemStack outputStack = extraction.getExtractionResult(slotIndex).stack();

		if (ItemStack.isSameItem(currentStack, outputStack) || currentStack.isEmpty()) {
			return currentStack.getCount() <= currentStack.getMaxStackSize() - outputStack.getCount();
		}
		else {
			return false;
		}
	}

	private static boolean areSlotsValid(ExtractorBlockEntity extractorEntity, Extraction extraction) {
		for (int slotIndex = 0; slotIndex < 7; slotIndex++) {
			if (!isSlotValid(extractorEntity, extraction, slotIndex)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isSuccess(RandomSource random, Extraction extraction, int slotIndex) {
		return random.nextInt(100) < extraction.getExtractionResult(slotIndex).successProbability();
	}

	private static void createResult(RandomSource random, ExtractorBlockEntity extractorEntity, Extraction extraction, int slotIndex) {
		ItemStack actualStack = extractorEntity.getItem(slotIndex);
		ItemStack outputStack = extraction.getExtractionResult(slotIndex).stack();

		ItemStack resultStack = new ItemStack(outputStack.getItem(), actualStack.getCount() + outputStack.getCount());

		if (slotIndex != 1) {
			if (isSuccess(random, extraction, slotIndex)) {
				extractorEntity.setItem(slotIndex, resultStack);
			}
		}
		else {
			extractorEntity.setItem(slotIndex, resultStack);
		}
	}

	private static void createResults(RandomSource random, ExtractorBlockEntity extractorEntity, Extraction extraction) {
		extractorEntity.removeItem(0, 1);

		for (int slotIndex = 0; slotIndex < 7; slotIndex++) {
			createResult(random, extractorEntity, extraction, slotIndex);
		}
	}

	public static void tick(Level level, BlockPos blockPos, BlockState blockState, ExtractorBlockEntity blockEntity) {
		if (!(level instanceof ServerLevel serverLevel)) return;

		Optional<RecipeHolder<Extraction>> match = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(blockEntity.getItem(0)), serverLevel);

		match.ifPresent(holder -> {
			Extraction foundRecipe = holder.value();

			if (blockEntity.extractionTime >= foundRecipe.getExtractionTime()) {
				if (areSlotsValid(blockEntity, foundRecipe)) {
					createResults(level.getRandom(), blockEntity, foundRecipe);
				}
			}
		});
	}
}
