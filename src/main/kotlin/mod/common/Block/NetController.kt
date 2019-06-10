package mod.common.Block

import net.minecraft.block.Block
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.material.Material
import mod.client.CrustTab
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

class TileEntityNetController : TileEntity() {
   var pipeCount = -1

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setInteger("pipeCount", pipeCount)
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        pipeCount = compound.getInteger("pipeCount")
        super.readFromNBT(compound)
    }

    /*
     * A pipe has been added to the network, scan for machines.
     */
    fun notifyAdded(pos: BlockPos) {
        pipeCount++;
        markDirty();
    }
}
