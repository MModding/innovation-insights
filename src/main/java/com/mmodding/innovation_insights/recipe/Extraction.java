package com.mmodding.innovation_insights.recipe;

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

public class Extraction implements Recipe<SingleRecipeInput> {

	public static final MapCodec<Extraction> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(

		Codec.INT.fieldOf("extraction_time").forGetter(Extraction::getExtractionTime),
		Ingredient.CODEC.fieldOf("input").forGetter(Extraction::getInput),
		ExtractionResult.CODEC.listOf(1, 7).fieldOf("results").forGetter(Extraction::getResults)
	).apply(instance, Extraction::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, Extraction> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.INT, Extraction::getExtractionTime,
		Ingredient.CONTENTS_STREAM_CODEC, Extraction::getInput,
		ExtractionResult.STREAM_CODEC.apply(ByteBufCodecs.list(7)), Extraction::getResults,
		Extraction::new
	);

    private final int extractionTime;
    private final Ingredient input;
	private final List<ExtractionResult> results;

    public Extraction(int extractionTime, Ingredient input, List<ExtractionResult> results) {
        this.extractionTime = extractionTime;
        this.input = input;
        this.results = results;
    }

    public int getExtractionTime() {
        return extractionTime;
    }

    public Ingredient getInput() {
        return this.input;
    }

	public List<ExtractionResult> getResults() {
		return this.results;
	}

	public ExtractionResult getExtractionResult(int slotIndex) {
		ExtractionResult result = this.results.get(slotIndex);
		return result != null ? result : ExtractionResult.BLANK;
	}

	@Override
	public boolean matches(SingleRecipeInput input, Level level) {
		return this.input.test(input.getItem(0));
	}

	@Override
	public ItemStack assemble(SingleRecipeInput input) {
		return ItemStack.EMPTY;
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
    public RecipeSerializer<Extraction> getSerializer() {
        return IIRecipeSerializers.EXTRACTION;
    }

    @Override
    public RecipeType<Extraction> getType() {
        return IIRecipeTypes.EXTRACTION;
    }

	@Override
	public PlacementInfo placementInfo() {
		return PlacementInfo.create(this.input);
	}

	@Override
	public RecipeBookCategory recipeBookCategory() {
		return RecipeBookCategories.CRAFTING_MISC;
	}

	public record ExtractionResult(ItemStack stack, int successProbability) {

		public static final Codec<ExtractionResult> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ItemStack.CODEC.fieldOf("stack").forGetter(ExtractionResult::stack),
			Codec.INT.fieldOf("success_probability").forGetter(ExtractionResult::successProbability)
		).apply(instance, ExtractionResult::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, ExtractionResult> STREAM_CODEC = StreamCodec.composite(
			ItemStack.STREAM_CODEC, ExtractionResult::stack,
			ByteBufCodecs.INT, ExtractionResult::successProbability,
			ExtractionResult::new
		);

		public static final ExtractionResult BLANK = new ExtractionResult(ItemStack.EMPTY, 0);
	}
}
