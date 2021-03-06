package mod.common.Block


import mod.common.Item.*
import net.minecraft.item.ItemStack
import net.minecraft.block.Block
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.world.IBlockAccess
import mod.client.CrustTab
import net.minecraft.entity.EntityLivingBase

object DualityPipe : Block(Material.IRON){

    init{
        setUnlocalizedName("DualityPipe")
        setRegistryName("dualitypipe")
        setCreativeTab(CrustTab)

        setHardness(1.5F)
    }

    /*
     * Notify controller of the new pipe.
     */
    override fun onBlockPlacedBy(worldIn: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
    }


    /*
     * Trigger a network rescan.
     */
    override fun removedByPlayer(state: IBlockState, worldIn: World, pos: BlockPos, placer: EntityPlayer, willHarvest: Boolean): Boolean {
        var block = worldIn.getBlockState(pos).getBlock()
        /*
         * find controller
         */
        var adjController = findBlockAdjacent(worldIn, pos, NetController::class.java)
        if(!adjController.isEmpty()) {
            var controller = worldIn.getTileEntity(adjController[0]) as TileEntityNetController
            controller.rescan(
            )
            return true
        }
        /*
         * look for controller connected to pipes
         */
        var pipes = mutableListOf<BlockPos>(pos)
        var len = pipes.size
        var i = 0
        loop@ while(i < len) {
            var pipe = pipes.get(i)
            var adjPipes = findBlockAdjacent(worldIn, pipe, DualityPipe::class.java)
            if (!adjPipes.isEmpty()) {
                for (pipe2 in adjPipes) {
                    var adjController = findBlockAdjacent(worldIn, pipe2!!, NetController::class.java)
                    if (!adjController.isEmpty()) {
                        var controller = worldIn.getTileEntity(adjController[0]) as TileEntityNetController
                        controller.rescan()
                        break@loop
                    } else {
                        if(pipes.find { a -> pipe2 == a} == null) {
                            pipes.add(pipe2)
                            len++
                        }
                    }
                }
            }
            i++
        }
        return super.removedByPlayer(state, worldIn, pos, placer, willHarvest)
    }
}