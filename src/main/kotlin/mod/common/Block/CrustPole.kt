package mod.common.Block

import mod.common.Item.*
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.block.Block
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.Entity
import net.minecraft.world.World

val CrustPoleBlock: Block = object : BlockTileEntity<TileEntityCrustPole>(Material.ROCK) {

    init {
        setUnlocalizedName("Crust Pole")
        setRegistryName("crustpole")
        setCreativeTab(CreativeTabs.MISC)
        setHardness(1.5F)
    }

    override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (!world.isRemote) {
            val tile = getTileEntity(world, pos)
            if(tile.count >= 100) {
                val dropItem = EntityItem(world, player.posX, player.posY, player.posZ, ItemStack(brownCrustlet, 1))
                world.spawnEntity(dropItem)
                tile.count = 0
                tile.markDirty()
            }
        }
        return true
    }

    override fun hasTileEntity(state: IBlockState): Boolean {
        return true
    }

    override fun createTileEntity(world: World, state: IBlockState): TileEntityCrustPole {
        return TileEntityCrustPole()
    }

}

class TileEntityCrustPole : TileEntity(), ITickable {
    var count: Int = 0

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setInteger("count", count)
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        count = compound.getInteger("count")
        super.readFromNBT(compound)
    }

    override fun update() {
        count++
        markDirty()
    }
}
