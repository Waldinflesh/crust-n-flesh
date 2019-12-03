package mod.common.Container

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot

import mod.common.Block.TileEntityFleshgrater
import mod.common.Item.CrustyFleshlet
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.CapabilityItemHandler

class ContainerFleshgrater(playerInv:InventoryPlayer, fleshgrater: TileEntityFleshgrater): Container(){

    var fleshgrater: TileEntityFleshgrater

    init {
        this.fleshgrater = fleshgrater
        val inventory = fleshgrater.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)
        val ind = 0

        //initializing inventory slots

        // Flesh grater inventory slots: 0..1
        addSlotToContainer(Slot(fleshgrater, 0, 51, 35))    //input slot
        addSlotToContainer(Slot(fleshgrater, 1, 105, 35))   //output slot

        // Vanilla inventory slots: 2..37
        for (i in 0..2) {
            for (j in 0..8) {
                addSlotToContainer(Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18)) //original 84
            }
        }

        for (i in 0..8) {
            addSlotToContainer(Slot(playerInv, i, 8 + i * 18, 142)) //original 142
        }

    }

    override fun canInteractWith(player: EntityPlayer): Boolean {
        return true
    }

    override fun transferStackInSlot(player: EntityPlayer, sourceSlotIndex: Int): ItemStack {
        val sourceSlot: Slot = inventorySlots.get(sourceSlotIndex) as Slot
        if(!sourceSlot.hasStack) return ItemStack.EMPTY

        val sourceStack: ItemStack = sourceSlot.stack
        val sourceStackCopy: ItemStack = sourceStack.copy()

        if(sourceStack.item == CrustyFleshlet){
            if(sourceSlotIndex in 2..37){
                if(!mergeItemStack(sourceStack, 0, 1, false)){
                    return ItemStack.EMPTY
                }
            } else if (sourceSlotIndex in 0..1){
                if(!mergeItemStack(sourceStack, 2, 37, true)){
                    return ItemStack.EMPTY
                }
            } else {
                print("Invalid slot index" + sourceSlotIndex)
                return ItemStack.EMPTY
            }
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.putStack(ItemStack.EMPTY)
        } else {
            sourceSlot.onSlotChanged()
        }

        sourceSlot.onTake(player, sourceStack)
        return sourceStackCopy
    }
}
