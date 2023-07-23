package net.alyptic.multioverworld.block.custom;





import net.alyptic.multioverworld.utility.coconut;
import net.minecraft.core.BlockPos;
import net.alyptic.multioverworld.world.dimension.ModDimensions;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;



public class world2_portal extends BaseEntityBlock {
    public world2_portal(Properties p_49224_) {
        super(p_49224_);
    }

    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        if (player instanceof ServerPlayer) {
            teleportPlayer((ServerPlayer) player, pos);
        }

        return InteractionResult.SUCCESS;
    }

    //Check the blocks to not replace something important
    public boolean isDirtOrSTone(BlockState state){

        return state.is(Tags.Blocks.STONE) ||
                (state.is(Tags.Blocks.SAND)) ||
                (state.is(Tags.Blocks.SANDSTONE)) ||
                (state == Blocks.DIRT.defaultBlockState()) ||
                (state == Blocks.GRASS_BLOCK.defaultBlockState());

    }


    public void teleportPlayer(ServerPlayer player, BlockPos pos) {
        //Get the player who clicked on the blocks dimension
        Level level1 = player.level;
        MinecraftServer minecraftserver = level1.getServer();


        ResourceKey<Level> destination;

        //this checks which dimension the player is in and sets the destination as the opposite
        destination = player.level.dimension() == ModDimensions.KJDIM_KEY ? Level.OVERWORLD : ModDimensions.KJDIM_KEY;
        if(player.level.isClientSide)
        {
            return;
        }
        //verify that the target dimension is not null
        if (minecraftserver != null) {
            ServerLevel destinationWorld = minecraftserver.getLevel(destination);

            //very the game can teleport the player "saftley"
            if (destinationWorld != null && !player.isPassenger()) {
                // Take the block pos and get xyz
                double x = pos.getX() + 0.5;
                double y = pos.getY() + 1;
                double z = pos.getZ() + 0.5;

                //places a teleport block at the exact cords as the world you originate from
                destinationWorld.setBlockAndUpdate(pos, defaultBlockState());

                //This replaces 2 blocks above it to air so the player does not suffocate
                if (isDirtOrSTone(destinationWorld.getBlockState(pos.above(1))) && isDirtOrSTone(destinationWorld.getBlockState(pos.above(2))))
                {
                    destinationWorld.setBlockAndUpdate(pos.above(1), Blocks.TORCH.defaultBlockState());
                    destinationWorld.setBlockAndUpdate(pos.above(2), Blocks.AIR.defaultBlockState());
                }
                //Teleport the player to the new dim and teleport on top of the block
                //make sure the player made it and fix it not usually not needed but better be safe than sorry!
                Position newpos = player.position();
                ResourceKey<Level> newlevel = player.level.dimension();
                ServerLevel newdim = minecraftserver.getLevel(newlevel);
                //break players cords apart
                double newX = newpos.x();
                double newY = newpos.y();
                double newZ = newpos.z();

                while(newdim != destinationWorld)
                {
                    player.changeDimension(destinationWorld, new coconut());
                    newlevel = player.level.dimension();
                    newdim = minecraftserver.getLevel(newlevel);
                }

                while(newX != x && newY != y && newZ != z) {
                    //if failed go into loop until our player is safe
                    player.teleportTo(x, y, z);
                    newpos = player.position();
                    newX = newpos.x();
                    newY = newpos.y();
                    newZ = newpos.z();
                }
            }
        }
    }

    @Override
    public @NotNull RenderShape getRenderShape (@NotNull BlockState render){
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return null;
    }

}
