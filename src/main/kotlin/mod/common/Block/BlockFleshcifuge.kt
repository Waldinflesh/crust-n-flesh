package mod.common.Block

import mod.CrustNFleshMod
import mod.ModGuiHandler
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
import mod.common.energy.DualityGenerator;
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraft.block.BlockContainer
import net.minecraft.inventory.IInventory;

val Fleshcifuge: Block = object : BlockTileEntity<TileentityFleshcifuge>(Material.IRON) {

    init {
        setUnlocalizedName("Fleshcifuge")
        setRegistryName("fleshcifuge")
        setCreativeTab(CrustTab)

        setHardness(1.5F)
    }

    override fun hasTileEntity(state: IBlockState): Boolean {
        return true
    }

    override fun createTileEntity(world: World, state: IBlockState): TileentityFleshcifuge {
        return TileentityFleshcifuge()
    }

    override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if(!world.isRemote){
        //        player.openGui(CrustNFleshMod, ModGuiHandler.NETCONTROLLER, world, pos.x, pos.y, pos.z)
            player.openGui(CrustNFleshMod, ModGuiHandler.FLESHGRATER, world, pos.x, pos.y, pos.z)
        }

        return true
    }
}

/*
 * TODO: Add function to get the full spectrum of energy on the network.
 */
class TileentityFleshcifuge : TileEntity(), ITickable, IInventory{
    override fun setInventorySlotContents(slotIndex: Int, itemstack: ItemStack) {
        itemStacks[slotIndex] = itemstack;
        if (!itemstack.isEmpty() && itemstack.getCount() > getInventoryStackLimit()) {  // isEmpty();  getStackSize()
            itemstack.setCount(getInventoryStackLimit());  //setStackSize()
        }
        markDirty();
    }

    var itemStacks: Array<ItemStack?> = arrayOfNulls<ItemStack>(2)

    init {
        clear()
    }

    override fun getField(p0: Int): Int {
        //get stuff like to cook time and the burn time remaining/value.
        return 0;
    }

    override fun setField(p0: Int, p1: Int) {
    }

    override fun hasCustomName(): Boolean {
        return false
    }

    override fun getStackInSlot(p0: Int): ItemStack {
        var ret = itemStacks[p0]
        return ret!!
    }

    override fun decrStackSize(slotIndex: Int, count: Int): ItemStack {
        val itemStackInSlot = getStackInSlot(slotIndex)
        if (itemStackInSlot.isEmpty) return ItemStack.EMPTY  //isEmpty(), EMPTY_ITEM

        val itemStackRemoved: ItemStack
        if (itemStackInSlot.count <= count) { //getStackSize
            itemStackRemoved = itemStackInSlot
            setInventorySlotContents(slotIndex, ItemStack.EMPTY) // EMPTY_ITEM
        } else {
            itemStackRemoved = itemStackInSlot.splitStack(count)
            if (itemStackInSlot.count == 0) { //getStackSize
                setInventorySlotContents(slotIndex, ItemStack.EMPTY) //EMPTY_ITEM
            }
        }
        markDirty()
        return itemStackRemoved
    }

    override fun clear() {
       for (i in 0..1) {
           itemStacks[i] = ItemStack.EMPTY
       }
    }

    override fun getSizeInventory(): Int {
        return itemStacks.size
    }

    override fun getName(): String {
        return Fleshcifuge.unlocalizedName
    }

    override fun isEmpty(): Boolean {
        for (itemstack in itemStacks) {
            if (!itemstack!!.isEmpty) {  // isEmpty()
                return false
            }
        }

        return true
    }

    override fun isItemValidForSlot(p0: Int, p1: ItemStack): Boolean {
        return true;
    }

    override fun getInventoryStackLimit(): Int {
        return 64;
    }

    override fun isUsableByPlayer(p0: EntityPlayer): Boolean {
        if (this.world.getTileEntity(this.pos) != this) return false;
        return true;
    }

    override fun openInventory(p0: EntityPlayer) {
    }

    override fun closeInventory(p0: EntityPlayer) {
    }

    override fun removeStackFromSlot(slotIndex: Int): ItemStack {
        val itemStack = getStackInSlot(slotIndex)
        if (!itemStack.isEmpty) setInventorySlotContents(slotIndex, ItemStack.EMPTY)  //isEmpty();  EMPTY_ITEM
        return itemStack
    }

    override fun getFieldCount(): Int {
        return 3 //probably wrong
    }

    override fun update() {
        //do calculations here.
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
    }
}