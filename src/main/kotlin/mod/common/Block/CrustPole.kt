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
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.properties.IProperty


public lateinit var GrowthState: PropertyInteger
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
                tile.count = 0
                val dropItem = EntityItem(world, player.posX, player.posY, player.posZ, ItemStack(BrownCrustlet, 1))
                world.spawnEntity(dropItem)
                world.setBlockState(pos, world.getBlockState(pos).withProperty(GrowthState, 0))
                tile.markDirty()
            }
        }
        return true
    }

    override fun createBlockState(): BlockStateContainer {
        GrowthState = PropertyInteger.create("growth", 0, 4)
        return BlockStateContainer(this, GrowthState)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        var a = defaultState.withProperty(GrowthState, meta)
        return a
    }

    override fun getMetaFromState(state: IBlockState?): Int {
        var a = state!!.getValue(GrowthState)
        return a
    }



    override fun isOpaqueCube(state: IBlockState): Boolean {
        return false;
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

    /*
     * Don't reset the tile entity after the blockstate is changed
     */
    override fun shouldRefresh(world: World, pos: BlockPos, oldState: IBlockState, newState: IBlockState): Boolean {
        return newState.getBlock() != oldState.getBlock()
    }

    override fun update() {
        if (!world.isRemote) {
            if (count >= 0 && count <= 20) {
                world.setBlockState(pos, getWorld().getBlockState(pos).withProperty(GrowthState, 0))
            }
            if (count >= 20 && count <= 40) {
                world.setBlockState(pos, getWorld().getBlockState(pos).withProperty(GrowthState, 1))
            }
            if (count >= 40 && count <= 80) {
                world.setBlockState(pos, getWorld().getBlockState(pos).withProperty(GrowthState, 2))
            }
            if (count >= 80 && count <= 100) {
                world.setBlockState(pos, getWorld().getBlockState(pos).withProperty(GrowthState, 3))
            }
            if (count >= 100) {
                world.setBlockState(pos, getWorld().getBlockState(pos).withProperty(GrowthState, 4))
            }
            count++
            markDirty()
        }
    }
}
