package com.mmodding.innovation_insights.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ExtractionSerializer implements RecipeSerializer<Extraction> {

    public Extraction.DefaultOutputEntry defaultOutput(JsonObject json) {
		return Extraction.DefaultOutputEntry.fromJson(json);
    }

    public Extraction.DefaultOutputEntry defaultOutput(FriendlyByteBuf buf) {
        return Extraction.DefaultOutputEntry.read(buf);
    }

	public Extraction.AdditionalOutputEntry additionalOutput(JsonObject json) {
		if (json != null) {
        	return Extraction.AdditionalOutputEntry.fromJson(json);
		}
		else {
			return new Extraction.AdditionalOutputEntry(ItemStack.EMPTY, 100);
		}
	}

    public Extraction.AdditionalOutputEntry additionalOutput(FriendlyByteBuf buf) {
        return Extraction.AdditionalOutputEntry.read(buf);
    }

    @Override
    public Extraction fromJson(Identifier id, JsonObject json) {
        Extraction.Json recipeJson = new Gson().fromJson(json, Extraction.Json.class);

        int time = recipeJson.extractionTime != 0 ? recipeJson.extractionTime : 2000;

        if (recipeJson.input == null || recipeJson.defaultOutput == null) {
            throw new JsonSyntaxException("Missing 'input' or 'defaultOutput' !");
        }

        Ingredient input = Ingredient.fromJson(recipeJson.input);

        Extraction.DefaultOutputEntry defaultOutput = this.defaultOutput(recipeJson.defaultOutput);

        Extraction.AdditionalOutputEntry firstAdditionalOutput = this.additionalOutput(recipeJson.firstAdditionalOutput);
        Extraction.AdditionalOutputEntry secondAdditionalOutput = this.additionalOutput(recipeJson.secondAdditionalOutput);
        Extraction.AdditionalOutputEntry thirdAdditionalOutput = this.additionalOutput(recipeJson.thirdAdditionalOutput);
        Extraction.AdditionalOutputEntry fourthAdditionalOutput = this.additionalOutput(recipeJson.fourthAdditionalOutput);
        Extraction.AdditionalOutputEntry fifthAdditionalOutput = this.additionalOutput(recipeJson.fifthAdditionalOutput);

        return new Extraction(id, time, input, defaultOutput, firstAdditionalOutput, secondAdditionalOutput, thirdAdditionalOutput, fourthAdditionalOutput, fifthAdditionalOutput);
    }

    @Override
    public Extraction fromNetwork(Identifier id, FriendlyByteBuf buf) {

        int time = buf.readInt();

        Ingredient input = Ingredient.fromNetwork(buf);

        Extraction.DefaultOutputEntry defaultOutput = this.defaultOutput(buf);

        Extraction.AdditionalOutputEntry firstAdditionalOutput = this.additionalOutput(buf);
        Extraction.AdditionalOutputEntry secondAdditionalOutput = this.additionalOutput(buf);
        Extraction.AdditionalOutputEntry thirdAdditionalOutput = this.additionalOutput(buf);
        Extraction.AdditionalOutputEntry fourthAdditionalOutput = this.additionalOutput(buf);
        Extraction.AdditionalOutputEntry fifthAdditionalOutput = this.additionalOutput(buf);

        return new Extraction(id, time, input, defaultOutput, firstAdditionalOutput, secondAdditionalOutput, thirdAdditionalOutput, fourthAdditionalOutput, fifthAdditionalOutput);
    }

    @Override
    public void write(FriendlyByteBuf buf, Extraction recipe) {

        buf.writeInt(recipe.getExtractionTime());

        recipe.getInput().toNetwork(buf);

        recipe.getDefaultOutput().write(buf);

        recipe.getFirstAdditionalOutput().write(buf);
        recipe.getSecondAdditionalOutput().write(buf);
        recipe.getThirdAdditionalOutput().write(buf);
        recipe.getFourthAdditionalOutput().write(buf);
        recipe.getFifthAdditionalOutput().write(buf);
    }
}
