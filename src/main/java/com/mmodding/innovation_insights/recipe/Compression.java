package com.mmodding.innovation_insights.recipe;

import com.mmodding.innovation_insights.crafting.DualRecipeInput;
import com.mmodding.innovation_insights.init.IIRecipeSerializers;
import com.mmodding.innovation_insights.init.IIRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class Compression implements Recipe<DualRecipeInput> {

	public static final MapCodec<Compression> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.INT.fieldOf("compression_time").forGetter(Compression::getCompressionTime),
		Ingredient.CODEC.fieldOf("first_ingredient").forGetter(Compression::getFirstIngredient),
		Ingredient.CODEC.fieldOf("second_ingredient").forGetter(Compression::getSecondIngredient),
		ItemStack.CODEC.fieldOf("result").forGetter(Compression::getResultItem)
	).apply(instance, Compression::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, Compression> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.INT, Compression::getCompressionTime,
		Ingredient.CONTENTS_STREAM_CODEC, Compression::getFirstIngredient,
		Ingredient.CONTENTS_STREAM_CODEC, Compression::getSecondIngredient,
		ItemStack.STREAM_CODEC, Compression::getResultItem,
		Compression::new
	);

	private final int compressionTime;
	private final Ingredient firstIngredient;
	private final Ingredient secondIngredient;
	private final ItemStack result;

    public Compression(int compressionTime, Ingredient firstIngredient, Ingredient secondIngredient, ItemStack result) {
		this.compressionTime = compressionTime;
		this.firstIngredient = firstIngredient;
		this.secondIngredient = secondIngredient;
		this.result = result;
    }

	public int getCompressionTime() {
		return this.compressionTime;
	}

	public Ingredient getFirstIngredient() {
		return this.firstIngredient;
	}

	public Ingredient getSecondIngredient() {
		return this.secondIngredient;
	}

	public ItemStack getResultItem() {
		return this.result;
	}

	@Override
	public boolean matches(DualRecipeInput input, Level level) {
		return (this.firstIngredient.test(input.firstStack()) && this.secondIngredient.test(input.secondStack())
		 || (this.firstIngredient.test(input.secondStack()) && this.secondIngredient.test(input.firstStack())));
	}

	@Override
	public ItemStack assemble(DualRecipeInput input) {
		return this.result.copy();
	}

	@Override
	public boolean showNotification() {
		return false;
	}

	@Override
	public String group() {
		return "";
	}

	@Override
    public RecipeSerializer<Compression> getSerializer() {
        return IIRecipeSerializers.COMPRESSION;
    }

    @Override
    public RecipeType<Compression> getType() {
        return IIRecipeTypes.COMPRESSION;
    }

	@Override
	public PlacementInfo placementInfo() {
		return PlacementInfo.create(List.of(this.firstIngredient, this.secondIngredient));
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;
	}
}
