package io.github.orlouge.nootherfarms;

import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class NoOtherFarmsMod {
    public static final String MOD_ID = "nootherfarms";
    public static Config config = new Config();

    public static int allowedCropDrops = 0;

    public static void init(Path configDir) {
        config = Config.read(Paths.get(configDir.toString(), MOD_ID + ".json").toString());
    }

    public static class Config {
        public final HashSet<Block> pistonMovable;
        public final HashSet<Block> dropRequiresPlayer;
        public final boolean farmerInstantPickup;
        public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

        public Config() {
            this(
                new HashSet<>(Set.of(Blocks.MELON, Blocks.PUMPKIN)),
                new HashSet<>(Set.of(Blocks.BAMBOO, Blocks.CACTUS, Blocks.SUGAR_CANE, Blocks.MELON, Blocks.PUMPKIN)),
                true
            );
        }

        public Config(HashSet<Block> pistonMovable, HashSet<Block> dropRequiresPlayer, boolean farmerInstantPickup) {
            this.pistonMovable = pistonMovable;
            this.dropRequiresPlayer = dropRequiresPlayer;
            this.farmerInstantPickup = farmerInstantPickup;
        }

        public static Config read(String fname) {
            File file = new File(fname);
            if (file.isFile()) {
                try (FileReader in = new FileReader(file)) {
                    return CODEC.parse(JsonOps.INSTANCE, JsonParser.parseReader(in)).getOrThrow(false, System.out::println);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try (FileWriter out = new FileWriter(file)) {
                    Optional<JsonElement> result = CODEC.encodeStart(JsonOps.INSTANCE, new Config()).resultOrPartial(System.out::println);
                    if (result.isPresent()) {
                        out.write(GSON.toJson(result.get()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new Config();
        }

        public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Registries.BLOCK.getCodec()).xmap(HashSet::new, ArrayList::new).fieldOf("piston_movable").forGetter(config -> config.pistonMovable),
            Codec.list(Registries.BLOCK.getCodec()).xmap(HashSet::new, ArrayList::new).fieldOf("drop_requires_player").forGetter(config -> config.dropRequiresPlayer),
            Codec.BOOL.fieldOf("farmer_instant_pickup").forGetter(config -> config.farmerInstantPickup)
        ).apply(instance, Config::new));
    }
}
