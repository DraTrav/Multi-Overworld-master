package net.alyptic.multioverworld.item;


import net.alyptic.multioverworld.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab {
    public static final CreativeModeTab multioverworldTab = new CreativeModeTab("multioverworld") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModBlocks.WORLD2_PORTAL.get());
        }
    };
}
