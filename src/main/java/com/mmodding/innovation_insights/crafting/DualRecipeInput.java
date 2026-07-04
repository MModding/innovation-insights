package com.mmodding.innovation_insights.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record DualRecipeInput(ItemStack firstStack, ItemStack secondStack) implements RecipeInput {

	@Override
	public ItemStack getItem(int index) {
		return switch (index) {
			case 0 -> this.firstStack;
			case 1 -> this.secondStack;
			default -> throw new IllegalArgumentException("No item for index " + index);
		};
	}

	@Override
	public int size() {
		return 2;
	}
}
