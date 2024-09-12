package io.github.orlouge.nootherfarms.mixin;

import io.github.orlouge.nootherfarms.NoOtherFarmsMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockMixin {
    @Shadow public abstract Block getBlock();

    @Inject(method = "getPistonBehavior", cancellable = true, at = @At("HEAD"))
    public void extendPistonBehavior(CallbackInfoReturnable<PistonBehavior> cir) {
        if (NoOtherFarmsMod.config.pistonMovable.contains(this.getBlock())) {
            cir.setReturnValue(PistonBehavior.NORMAL);
            cir.cancel();
        }
    }

    @Inject(method = "getDroppedStacks", cancellable = true, at = @At("HEAD"))
    public void extendDroppedStacks(LootContextParameterSet.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (NoOtherFarmsMod.config.dropRequiresPlayer.contains(this.getBlock())) {
            if (NoOtherFarmsMod.allowedCropDrops < 1 && builder.getOptional(LootContextParameters.THIS_ENTITY) == null) {
                cir.setReturnValue(Collections.emptyList());
                cir.cancel();
            } else {
                NoOtherFarmsMod.allowedCropDrops += 3;
            }
        }
    }
}
