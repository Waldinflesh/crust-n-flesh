package mod.client.gui

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container

import mod.common.Container.ContainerNetController
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.resources.I18n
import net.minecraft.util.text.TextComponentString


class GuiNetController(container: Container, playerInv: InventoryPlayer): GuiContainer(container){

    val playerInv: InventoryPlayer = playerInv
    val container: ContainerNetController = container as ContainerNetController
    val netController = this.container.netController
    val BG_TEXTURE: ResourceLocation = ResourceLocation(mod.modid, "textures/gui/netcontroller.png")

    init{
        this.netController.getColorZone()
    }

    //GUI Buttons
    val RESCAN = 0

    init {
        this.playerInv = playerInv
        this.container = container as ContainerNetController
    }

    override fun initGui() {
        super.initGui()

        //Gui coordinates
        val centerX = width/2
        val centerY = height/2

        //default button size
        val buttonWidth = 80
        val buttonHeight = 20

        //GuiButton parameters: (id, x, y, width, height, text)
        // x and y indicate button's top left corner
        val rescanButtonX = centerX - buttonWidth/2 + 40
        val rescanButtonY = centerY - buttonHeight/2
        this.buttonList.add(GuiButton(RESCAN, rescanButtonX, rescanButtonY, buttonWidth, buttonHeight, "Rescan"))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        this.drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        this.renderHoveredToolTip(mouseX, mouseY)
    }

    //wave movement
    var frameCounter = 0    //move the wave every 5 frames
    var cursor = 0

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        mc.getTextureManager().bindTexture(BG_TEXTURE)

        //top left corner
        val guiCornerX = (width-xSize)/2
        val guiCornerY = (height-ySize)/2

        //drawTexturedModalRect parameters: (x, y, textureTopLeftX, textureTopLeftY, textureWidth, textureHeight)
        //gui background
        drawTexturedModalRect(guiCornerX, guiCornerY, 0, 0, 175, 198)

        this.netController.getColorZone()

        when(this.netController.colorZone) {
            1 -> drawTexturedModalRect(guiCornerX + 8, guiCornerY + 79, 1 + cursor, 203, 81, 13)
            2 -> drawTexturedModalRect(guiCornerX + 8, guiCornerY + 79, cursor - 1, 218, 81, 13)
        }

        //circle pointer
        drawTexturedModalRect(guiCornerX + 37 + this.netController.ptrMoveX, guiCornerY + 41 + this.netController.ptrMoveY, 179, 2, 5, 5)

        //wave progression
        if(this.frameCounter == 2) {
            this.frameCounter = 0
            if (this.cursor == 20) {
                this.cursor = 0
            } else {
                this.cursor++
            }
        }else{
            this.frameCounter++
        }
    }


    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val name = I18n.format(mod.common.Block.NetController.getUnlocalizedName() + ".name");
        //(string, x, y, color??)
        fontRenderer.drawString(name, 8, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, 106, 0x404040);
    }

    override fun actionPerformed(button: GuiButton) {
        if (button.id == RESCAN) {
            playerInv.player.sendMessage(TextComponentString("Rescanning network..."))
            this.netController.rescan()
            playerInv.player.sendMessage(TextComponentString("Elements found: " + container.netController.machineList.size))
        }
    }

}