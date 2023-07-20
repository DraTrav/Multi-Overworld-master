package net.alyptic.multioverworld.utility;


import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.alyptic.multioverworld.multioverworld;


public class sleepUntilDay{
    @Mod.EventBusSubscriber(modid = multioverworld.MOD_ID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void sleepUntilDay(SleepFinishedTimeEvent event) {
            //get the player and server

            MinecraftServer minecraftserver = event.getLevel().getServer();

                //dimensions take attributes from the overworld
                //set time to morning and stop rain
                //its a clone, no reason to have diffrent weather

                ServerLevel mainworld = minecraftserver.getLevel(Level.OVERWORLD);

                //sleep becomes available at 12500
                //sleep stops being available at 23500
                if(mainworld.getDayTime() > 12500 && mainworld.dayTime() < 23500) {

                    mainworld.setDayTime(1000);

                    //to do this set raining is stored in level data, mainworld is already there
                    //Thundering however is serverdata to get there we can use minecraftserver,
                    // to get overworld data, then presto we are there

                    LevelData mainworlddata = mainworld.getLevelData();
                    ServerLevelData serverdata = minecraftserver.getWorldData().overworldData();
                    mainworlddata.setRaining(false);
                    serverdata.setThundering(false);



            }
        }
    }
}
