package net.anvian.simplemango.world.features;

import net.anvian.simplemango.MangoMod;
import net.anvian.simplemango.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> MANGO_TREE_CHECKED_KEY = registerKey("mango_tree_checked");
    public static final RegistryKey<PlacedFeature> MANGO_TREE_PLACED_KEY = registerKey("mango_tree_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, MANGO_TREE_CHECKED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MANGO_KEY),
                List.of(PlacedFeatures.wouldSurvive(ModBlocks.MANGO_SAPLING)));
        register(context, MANGO_TREE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MANGO_KEY),
                VegetationPlacedFeatures.modifiers(PlacedFeatures.createCountExtraModifier(1, 0.2f, 1)));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MangoMod.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
