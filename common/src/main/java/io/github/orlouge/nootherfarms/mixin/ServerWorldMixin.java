package io.github.orlouge.nootherfarms.mixin;

import io.github.orlouge.nootherfarms.NoOtherFarmsMod;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    public void decreaseAllowedCropDropsOnTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        NoOtherFarmsMod.allowedCropDrops = Math.max(0, NoOtherFarmsMod.allowedCropDrops - 1);
    }
}
