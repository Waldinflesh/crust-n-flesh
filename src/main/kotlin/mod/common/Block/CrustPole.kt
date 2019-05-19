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

public lateinit var GrowthState: PropertyInteger
val CrustPole: Block = object : BlockTileEntity<TileEntityCrustPole>(Material.ROCK) {

    init {
        setUnlocalizedName("CrustPole")
        setRegistryName("crustpole")
        setCreativeTab(CrustTab)
    }

    override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (!world.isRemote) {
            val tile = getTileEntity(world, pos)

            if(tile.count >= 100) {
                tile.count = 0
                tile.durability--
                val dropItem = EntityItem(world, player.posX, player.posY, player.posZ, ItemStack(CrustyFleshlet, 1))
                world.spawnEntity(dropItem)
                world.setBlockState(pos, world.getBlockState(pos).withProperty(GrowthState, 0))
                tile.markDirty()
                if(tile.durability <= 0) {
                    world.destroyBlock(pos, false)
                }
            }
        }
        return true
    }

    override fun createBlockState(): BlockStateContainer {
        GrowthState = PropertyInteger.create("growth", 0, 4)
        return BlockStateContainer(this, GrowthState)
    }

    /*
     * Set the tile entity's data.
     */
    override fun onBlockPlacedBy(worldIn: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack)
        if (stack.hasTagCompound() && stack!!.getTagCompound()!!.hasKey("Durability")) {
            val tag = stack!!.getTagCompound()!!.getCompoundTag("Durability")
            val tile = getTileEntity(worldIn, pos) as TileEntityCrustPole
            tile!!.durability = stack!!.getTagCompound()!!.getInteger("Durability")
        }
    }


    /*
     * Delay tile entity deletion.
     */
    override fun removedByPlayer(state: IBlockState, world: World, pos: BlockPos, player: EntityPlayer, willHarvest: Boolean): Boolean {
        if (willHarvest) {
            return true
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest)
    }

    /*
     * Save data to the itemstack.
     */
    override fun getDrops(drops: net.minecraft.util.NonNullList<ItemStack>, world: IBlockAccess, pos: BlockPos, state: IBlockState, fortune: Int) {
        val te = getTileEntity(world, pos)
        var ret = ItemStack(this, 1, 0)
        var nbt = NBTTagCompound()
        nbt.setInteger("Durability", te.durability)
        ret.setTagCompound(nbt)
        drops.add(ret)
    }

    override fun harvestBlock(world: World, player: EntityPlayer, pos: BlockPos, state: IBlockState, te: TileEntity?, stack: ItemStack) {
        super.harvestBlock(world, player, pos, state, te, stack)
        world.setBlockToAir(pos)
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
    var durability = 7

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setInteger("count", count)
        compound.setInteger("durability", durability)
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        count = compound.getInteger("count")
        compound.getInteger("durability")
        super.readFromNBT(compound)
    }

    /*
     * Don't reset the tile entity after the blockstate is changed
     */
    override fun shouldRefresh(world: World, pos: BlockPos, oldState: IBlockState, newState: IBlockState): Boolean {
        return newState.getBlock() != oldState.getBlock()
    }

    override fun update() {
        if(durability <= 0) {
            return
        }
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
