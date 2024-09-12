package io.github.orlouge.nootherfarms.forge;

import net.minecraftforge.fml.common.Mod;

import io.github.orlouge.nootherfarms.NoOtherFarmsMod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(NoOtherFarmsMod.MOD_ID)
public final class NoOtherFarmsForge {
    public NoOtherFarmsForge() {
        NoOtherFarmsMod.init(FMLPaths.CONFIGDIR.get());
    }
}
