package mod.client.gui

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container

import mod.common.Container.ContainerNetController
import net.minecraft.client.resources.I18n


class GuiNetController(container: Container, playerInv: InventoryPlayer): GuiContainer(container){

    val playerInv: InventoryPlayer
    val BG_TEXTURE: ResourceLocation = ResourceLocation(mod.modid, "textures/gui/netcontroller.png")

    init{
        this.playerInv = playerInv
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        GlStateManager.color(1.0f,1.0f,1.0f,1.0f)
        mc.getTextureManager().bindTexture(BG_TEXTURE)
        val x = (width - xSize)/2
        val y = (height - ySize)/2
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val name = I18n.format(mod.common.Block.NetController.getUnlocalizedName() + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
    }

}