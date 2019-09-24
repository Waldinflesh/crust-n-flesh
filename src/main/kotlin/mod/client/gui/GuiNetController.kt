package mod.client.gui

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container

import mod.common.Container.ContainerNetController
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.resources.I18n
import net.minecraft.util.text.TextComponentString


class GuiNetController(container: Container, playerInv: InventoryPlayer): GuiContainer(container){

    var playerInv: InventoryPlayer
    var container: ContainerNetController
    val BG_TEXTURE: ResourceLocation = ResourceLocation(mod.modid, "textures/gui/netcontroller.png")

    //GUI Buttons
    val RESCAN = 0

    init{
        this.playerInv = playerInv
        this.container = container as ContainerNetController
    }

    override fun initGui() {
        super.initGui()

        //Gui coordinates
        val centerX = width/2
        val centerY = height/2

        //default button size
        val buttonWidth = 100
        val buttonHeight = 20

        //GuiButton parameters: (id, x, y, width, height, text)
        // x and y indicate button's top left corner
        val rescanButtonX = centerX - buttonWidth/2
        val rescanButtonY = centerY - 30 - buttonHeight/2
        this.buttonList.add(GuiButton(RESCAN, rescanButtonX, rescanButtonY, buttonWidth, buttonHeight, "Rescan"))
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
        fontRenderer.drawString(name, 8, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
    }

    override fun actionPerformed(button: GuiButton) {
        if(button.id == RESCAN){
            playerInv.player.sendMessage(TextComponentString("Rescanning network..."))
            this.container.netController.rescan()
            playerInv.player.sendMessage(TextComponentString("Elements found: " + container.netController.machineList.size))
        }
    }

}