package mod.common.Container

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot

import mod.common.Block.TileEntityNetController
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerNetController(playerInv:InventoryPlayer, netController: TileEntityNetController): Container(){

    var netController: TileEntityNetController

    init {
        this.netController = netController
        val inventory = netController.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)

        //initializing inventory slots
        for (i in 0..2) {
            for (j in 0..8) {
                addSlotToContainer(Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 117 + i * 18)) //original 84
            }
        }

        for (i in 0..8) {
            addSlotToContainer(Slot(playerInv, i, 8 + i * 18, 175)) //original 142
        }
    }

    override fun canInteractWith(player: EntityPlayer): Boolean{
        return true
    }
}

