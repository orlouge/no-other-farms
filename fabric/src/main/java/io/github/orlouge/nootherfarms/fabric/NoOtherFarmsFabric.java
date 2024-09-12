package io.github.orlouge.nootherfarms.fabric;

import net.fabricmc.api.ModInitializer;

import io.github.orlouge.nootherfarms.NoOtherFarmsMod;
import net.fabricmc.loader.api.FabricLoader;

public final class NoOtherFarmsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NoOtherFarmsMod.init(FabricLoader.getInstance().getConfigDir());
    }
}
