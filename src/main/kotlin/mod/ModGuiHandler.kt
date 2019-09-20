package mod

import mod.client.gui.GuiNetController
import mod.common.Block.TileEntityNetController
import mod.common.Container.ContainerNetController
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

object ModGuiHandler: IGuiHandler{

    val NETCONTROLLER = 0

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Container? {

        when(ID){
            NETCONTROLLER -> return ContainerNetController(player.inventory, world.getTileEntity(BlockPos(x, y, z)) as TileEntityNetController)
            else -> return null
        }
    }

    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {

        when(ID){
            NETCONTROLLER -> return GuiNetController(getServerGuiElement(ID, player, world, x, y, z) as Container, player.inventory)
            else -> return null
        }
    }

}