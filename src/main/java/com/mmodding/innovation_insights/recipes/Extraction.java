package com.mmodding.innovation_insights.recipes;

import com.google.gson.JsonObject;
import com.mmodding.innovation_insights.init.IIRecipeSerializers;
import com.mmodding.innovation_insights.init.IIRecipeTypes;
import com.mmodding.innovation_insights.inventories.ImplementedInventory;
import com.mmodding.mmodding_lib.library.utils.RecipeSerializationUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class Extraction implements Recipe<ImplementedInventory> {

    private final ResourceLocation extractionId;
    private final int extractionTime;
    private final Ingredient input;
    private final DefaultOutputEntry defaultOutput;
    private final AdditionalOutputEntry firstAdditionalOutput;
    private final AdditionalOutputEntry secondAdditionalOutput;
    private final AdditionalOutputEntry thirdAdditionalOutput;
    private final AdditionalOutputEntry fourthAdditionalOutput;
    private final AdditionalOutputEntry fifthAdditionalOutput;

    public Extraction(ResourceLocation extractionId, int extractionTime, Ingredient input, DefaultOutputEntry defaultOutput, AdditionalOutputEntry firstAdditionalOutput, AdditionalOutputEntry secondAdditionalOutput, AdditionalOutputEntry thirdAdditionalOutput, AdditionalOutputEntry fourthAdditionalOutput, AdditionalOutputEntry fifthAdditionalOutput) {
        this.extractionId = extractionId;
        this.extractionTime = extractionTime;
        this.input = input;
        this.defaultOutput = defaultOutput;
        this.firstAdditionalOutput = firstAdditionalOutput;
        this.secondAdditionalOutput = secondAdditionalOutput;
        this.thirdAdditionalOutput = thirdAdditionalOutput;
        this.fourthAdditionalOutput = fourthAdditionalOutput;
        this.fifthAdditionalOutput = fifthAdditionalOutput;
    }

    public static class Type implements RecipeType<Extraction> {}

    public static class Json {
        int extractionTime;
        JsonObject input;
        JsonObject defaultOutput;
        JsonObject firstAdditionalOutput;
        JsonObject secondAdditionalOutput;
        JsonObject thirdAdditionalOutput;
        JsonObject fourthAdditionalOutput;
        JsonObject fifthAdditionalOutput;
    }

    @Override
    public ResourceLocation getId() {
        return this.extractionId;
    }

    public int getExtractionTime() {
        return extractionTime;
    }

    public Ingredient getInput() {
        return this.input;
    }

    @Override
    public ItemStack getResultItem() {
        return this.getDefaultOutput().stack();
    }

	public DefaultOutputEntry getDefaultOutput() {
		return this.defaultOutput;
	}
    public AdditionalOutputEntry getFirstAdditionalOutput() {
        return firstAdditionalOutput;
    }

    public AdditionalOutputEntry getSecondAdditionalOutput() {
        return secondAdditionalOutput;
    }

    public AdditionalOutputEntry getThirdAdditionalOutput() {
        return thirdAdditionalOutput;
    }

    public AdditionalOutputEntry getFourthAdditionalOutput() {
        return fourthAdditionalOutput;
    }

    public AdditionalOutputEntry getFifthAdditionalOutput() {
        return fifthAdditionalOutput;
    }

    @Override
    public ItemStack craft(ImplementedInventory inventory) {
        return getResultItem().copy();
    }

    @Override
    public boolean matches(ImplementedInventory inventory, Level world) {
        return input.test(inventory.getStack(0));
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public RecipeSerializer<Extraction> getSerializer() {
        return IIRecipeSerializers.EXTRACTION_SERIALIZER;
    }

    @Override
    public RecipeType<Extraction> getType() {
        return IIRecipeTypes.EXTRACTION;
    }

	public record DefaultOutputEntry(ItemStack stack) {

		public static DefaultOutputEntry fromJson(JsonObject json) {
			return new DefaultOutputEntry(RecipeSerializationUtils.getStack(json));
		}

        public static DefaultOutputEntry read(FriendlyByteBuf buf) {
            return new DefaultOutputEntry(buf.readItem());
        }

        public void write(FriendlyByteBuf buf) {
            buf.writeItem(this.stack());
        }
	}

	public record AdditionalOutputEntry(ItemStack stack, int luck) {

        public static AdditionalOutputEntry fromJson(JsonObject json) {
            ItemStack stack = RecipeSerializationUtils.getStack(json);
            int luck = GsonHelper.getAsInt(json, "luck");
            luck = luck != 0 ? luck : 1;
            return new AdditionalOutputEntry(stack, luck);
        }

        public static AdditionalOutputEntry read(FriendlyByteBuf buf) {
            return new AdditionalOutputEntry(buf.readItem(), buf.readInt());
        }

        public void write(FriendlyByteBuf buf) {
            buf.writeItem(this.stack());
            buf.writeInt(this.luck());
        }
    }
}
