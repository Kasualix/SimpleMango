package net.anvian.simplemango.world.features;

import net.anvian.simplemango.MangoMod;
import net.anvian.simplemango.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> MANGO_KEY = registerKey("mango_tree");
    public static final RegistryKey<ConfiguredFeature<?,?>> MANGO_SPAWN_KEY = registerKey("mango_tree_spawn");

    public static void bootstrap(Registerable<ConfiguredFeature<?,?>> context){
        var placedFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

        register(context, MANGO_KEY,  Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.MANGO_LOG),
                new StraightTrunkPlacer(5, 3, 1),
                BlockStateProvider.of(ModBlocks.MANGO_LEAVES),
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                new TwoLayersFeatureSize(1, 0, 2)).build());
        register(context, MANGO_SPAWN_KEY, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(ModPlacedFeatures.MANGO_TREE_PLACED_KEY),
                        0.1f)), placedFeatureRegistryEntryLookup.getOrThrow(ModPlacedFeatures.MANGO_TREE_PLACED_KEY)));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(MangoMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
