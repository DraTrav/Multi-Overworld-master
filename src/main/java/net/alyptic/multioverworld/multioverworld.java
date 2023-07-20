package net.alyptic.multioverworld;

import com.mojang.logging.LogUtils;
import net.alyptic.multioverworld.block.ModBlocks;
import net.alyptic.multioverworld.item.ModItems;
import net.alyptic.multioverworld.world.dimension.ModDimensions;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file

// rock and roll mcdonalds
@Mod(multioverworld.MOD_ID)
public class multioverworld
{

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "multioverworld";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public multioverworld()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModDimensions.register();

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);




        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
