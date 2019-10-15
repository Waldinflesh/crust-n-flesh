package mod.common.Container

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot

import mod.common.Block.TileEntityNetController
import mod.common.Block.TileentityFleshcifuge
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerFleshcifuge(playerInv:InventoryPlayer, fleshcifuge: TileentityFleshcifuge): Container(){

    var fleshcifuge: TileentityFleshcifuge

    init {
        this.fleshcifuge = fleshcifuge
        val inventory = fleshcifuge.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)
        val ind = 0
        addSlotToContainer(Slot(fleshcifuge, 0, 38, 34))
        addSlotToContainer(Slot(fleshcifuge, 1, 123, 34))

        //initializing inventory slots
        for (i in 0..2) {
            for (j in 0..8) {
                addSlotToContainer(Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 117 + i * 18)) //original 84
            }
        }

        for (i in 0..8) {
            addSlotToContainer(Slot(playerInv, i, 8 + i * 18, 175)) //original 142
        }

        //Add the 2 slots
    }

    override fun canInteractWith(player: EntityPlayer): Boolean {
        return true
    }
}
