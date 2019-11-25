package mod.common.Container

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot

import mod.common.Block.TileEntityNetController
import mod.common.Block.TileentityFleshgrater
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerFleshgrater(playerInv:InventoryPlayer, fleshgrater: TileentityFleshgrater): Container(){

    var fleshgrater: TileentityFleshgrater

    init {
        this.fleshgrater = fleshgrater
        val inventory = fleshgrater.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)
        val ind = 0

        //initializing inventory slots
        for (i in 0..2) {
            for (j in 0..8) {
                addSlotToContainer(Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18)) //original 84
            }
        }

        for (i in 0..8) {
            addSlotToContainer(Slot(playerInv, i, 8 + i * 18, 142)) //original 142
        }

        //Add flesh grater slots
        addSlotToContainer(Slot(fleshgrater, 0, 51, 35))
        addSlotToContainer(Slot(fleshgrater, 1, 105, 35))

    }

    override fun canInteractWith(player: EntityPlayer): Boolean {
        return true
    }
}
