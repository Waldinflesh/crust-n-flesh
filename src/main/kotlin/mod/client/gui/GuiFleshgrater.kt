package mod.client.gui

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container

import mod.common.Container.ContainerFleshgrater
import net.minecraft.client.resources.I18n


class GuiFleshgrater(container: Container, playerInv: InventoryPlayer): GuiContainer(container) {

    var playerInv: InventoryPlayer
    var container: ContainerFleshgrater
    val BG_TEXTURE: ResourceLocation = ResourceLocation(mod.modid, "textures/gui/fleshgrater.png")

    init {
        this.playerInv = playerInv
        this.container = container as ContainerFleshgrater
    }

    override fun initGui() {
        super.initGui()

        //Gui coordinates
        val centerX = width / 2
        val centerY = height / 2
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        this.renderHoveredToolTip(mouseX, mouseY)
    }


    val maxEnergy = this.container.fleshgrater.maxEnergy

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        mc.getTextureManager().bindTexture(BG_TEXTURE)

        //top left
        val x = (width - xSize) / 2
        val y = (height - ySize) / 2
        //drawTexturedModalRect parameters: (x, y, textureTopLeftX, textureTopLeftY, textureWidth, textureHeight)
        //gui background
        drawTexturedModalRect(x, y, 0, 0, 175, 198)

        //energy level
        val currentEnergy = this.container.fleshgrater.currentEnergy
        val barsNumber:Int = currentEnergy*37/maxEnergy
        val eTopLeftX = 180
        val eTopLeftY = 39 - barsNumber
        drawTexturedModalRect(x + 8, y + 62 - barsNumber, eTopLeftX, eTopLeftY, 6, barsNumber)


    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val name = I18n.format(mod.common.Block.Fleshgrater.unlocalizedName + ".name");
        //(string, x, y, color??)
        fontRenderer.drawString(name, 8, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
    }
}
