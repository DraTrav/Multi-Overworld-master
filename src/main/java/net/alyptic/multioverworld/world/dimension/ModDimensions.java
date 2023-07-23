package net.alyptic.multioverworld.world.dimension;


import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.alyptic.multioverworld.multioverworld;
import net.minecraft.world.level.dimension.DimensionType;


public class ModDimensions {


    public static final ResourceKey<Level> KJDIM_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(multioverworld.MOD_ID, "overworld2"));
    public static final ResourceKey<DimensionType> KJDIM_TYPE =
            ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY,
                    new ResourceLocation(multioverworld.MOD_ID, "overworld2"));




    public static void register() {
        System.out.println("Registering ModDimensions for " + multioverworld.MOD_ID);
    }
}