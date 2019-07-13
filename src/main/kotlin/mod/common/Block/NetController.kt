package mod.common.Block

import net.minecraft.block.Block
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.material.Material
import mod.client.CrustTab
import mod.common.energy.DualityMachine
import net.minecraft.item.ItemStack
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
import net.minecraft.block.state.IBlockState;
import mod.common.energy.DualityGenerator

val NetController: Block = object : BlockTileEntity<TileEntityNetController>(Material.IRON) {

    init{
        setUnlocalizedName("NetController")
        setRegistryName("networkcontroller")
        setCreativeTab(CrustTab)

        setHardness(1.5F)
    }

    override fun hasTileEntity(state: IBlockState): Boolean {
        return true
    }

    override fun createTileEntity(world: World, state: IBlockState): TileEntityNetController {
        return TileEntityNetController()
    }
    }

/*
 * TODO: Add function to get the full spectrum of energy on the network.
 */
class TileEntityNetController : TileEntity() {
    var machineList: MutableList<BlockPos> = arrayListOf()

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        var x = 0
        for (machine in machineList) {
            compound.setLong("machine" + x, machine.toLong())
            x++
        }
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        var x = 0
        while(true) {
            if(compound.hasKey("machine" + x)) {
                var pos = BlockPos.fromLong(compound.getLong("machine" + x))
                machineList.add(pos)
            } else {
                break
            }
        }
        super.readFromNBT(compound)
    }

    fun addMachine(pos: BlockPos) {
        machineList.add(pos)
        markDirty()
    }

    fun rescan() {
        var pos = getPos()
        var worldIn = getWorld()
        var x = pos.getX()
        var y = pos.getY()
        var z = pos.getZ()
        var block = worldIn.getBlockState(pos).getBlock()
        /*
         * find generators
         */
        var adjGenerators = findBlockAdjacent(worldIn, pos, DualityGenerator::class.java)
        if(!adjGenerators.isEmpty()) {
            for(gen in adjGenerators) {
                machineList.add(gen)
            }
        }
        /*
         * look for generators connected to pipes
         */
        var pipes = mutableListOf<BlockPos>(pos)
        var len = pipes.size
        var i = 0
        loop@ while(i < len) {
            var pipe = pipes.get(i)
            var adjPipes = findBlockAdjacent(worldIn, pipe, DualityPipe::class.java)
            if (!adjPipes.isEmpty()) {
                for (pipe2 in adjPipes) {
                    var adjGenerators = findBlockAdjacent(worldIn, pipe2!!, DualityGenerator::class.java)
                    if (!adjGenerators.isEmpty()) {
                        for(gen in adjGenerators) {
                            machineList.add(gen)
                        }
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
    }
}
