package com.mmodding.innovation_insights.mixin;

import com.mmodding.innovation_insights.InnovationEnergyFlux;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.reborn.energy.api.base.SimpleEnergyItem;

@Mixin(SimpleEnergyItem.class)
public interface SimpleEnergyItemMixin {

    @Inject(method = "getStoredEnergyUnchecked(Lnet/minecraft/nbt/NbtCompound;)J", at = @At("HEAD"), cancellable = true)
    private static void getStoredEnergyUnchecked(CompoundTag nbt, CallbackInfoReturnable<Long> cir) {
        if (nbt != null && nbt.contains("IEF")) {
            cir.setReturnValue(nbt.getLong("IEF"));
        }
    }

    @Inject(method = "setStoredEnergyUnchecked", at = @At("HEAD"), cancellable = true)
    private static void setStoredEnergyUnchecked(ItemStack stack, long newAmount, CallbackInfo ci) {
        if (stack.getItem() instanceof InnovationEnergyFlux.Item) {
            if (newAmount != 0) {
                stack.getOrCreateTag().putLong("IEF", newAmount);
            }
            else {
                stack.removeTagKey("IEF");
            }
            ci.cancel();
        }
    }
}
