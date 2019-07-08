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
}
