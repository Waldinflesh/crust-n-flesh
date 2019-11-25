package mod.common.Block

import mod.CrustNFleshMod
import mod.ModGuiHandler
import net.minecraft.block.Block
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.material.Material
import mod.client.CrustTab
import mod.client.gui.GuiNetController
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
import mod.common.energy.DualityGenerator
import mod.modid
import net.minecraft.block.BlockHorizontal
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler
import java.awt.image.BufferedImage
import java.io.InputStream
import java.nio.ByteBuffer
import javax.imageio.ImageIO
import kotlin.math.cos
import kotlin.math.sin

val NetController: Block = object : BlockTileEntity<TileEntityNetController>(Material.IRON) {

    init{
        setUnlocalizedName("NetController")
        setRegistryName("netcontroller")
        setCreativeTab(CrustTab)
        setHardness(1.5F)

        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHorizontal.FACING,EnumFacing.NORTH))
    }

    override fun hasTileEntity(state: IBlockState): Boolean {
        return true
    }

    override fun createTileEntity(world: World, state: IBlockState): TileEntityNetController {
        return TileEntityNetController()
    }

    override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if(!world.isRemote){

            if(!player.isSneaking){
                player.openGui(CrustNFleshMod, ModGuiHandler.NETCONTROLLER, world, pos.x, pos.y, pos.z)
            }
        }

        return true
    }

}


class TileEntityNetController : TileEntity() {

    var machineList: MutableList<BlockPos> = arrayListOf()

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        var x = 0
        for (machine in machineList) {
            compound.setLong("machine" + x, machine.toLong())
            x++
        }
        return super.writeToNBT(compound)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        var x = 0
        while(true) {
            if(compound.hasKey("machine" + x)) {
                var pos = BlockPos.fromLong(compound.getLong("machine" + x))
                machineList.add(pos)
                x++
            } else {
                break
            }
        }
        super.readFromNBT(compound)
    }

    fun addMachine(pos: BlockPos) {
        machineList.add(pos)
        markDirty()
    }


    fun rescan() {
        machineList = arrayListOf()
        var pos = getPos()
        var worldIn = getWorld()
        var x = pos.getX()
        var y = pos.getY()
        var z = pos.getZ()
        var block = worldIn.getBlockState(pos).getBlock()
        /*
         * find generators
         */
        var adjGenerators = findBlockAdjacent(worldIn, pos, DualityGenerator::class.java)
        if (!adjGenerators.isEmpty()) {
            for (gen in adjGenerators) {
                machineList.add(gen)
            }
        }
        /*
         * look for generators connected to pipes
         */
        var pipes = mutableListOf<BlockPos>(pos)
        var len = pipes.size
        var i = 0
        loop@ while (i < len) {
            var pipe = pipes.get(i)
            var adjPipes = findBlockAdjacent(worldIn, pipe, DualityPipe::class.java)
            if (!adjPipes.isEmpty()) {
                for (pipe2 in adjPipes) {
                    var adjGenerators = findBlockAdjacent(worldIn, pipe2!!, DualityGenerator::class.java)
                    if (!adjGenerators.isEmpty()) {
                        for (gen in adjGenerators) {
                            if (machineList.find { a -> gen == a } == null) {
                                machineList.add(gen)
                            }
                        }
                        if (pipes.find { a -> pipe2 == a } == null) {
                            pipes.add(pipe2)
                            len++
                        }
                    } else {
                        if (pipes.find { a -> pipe2 == a } == null) {
                            pipes.add(pipe2)
                            len++
                        }
                    }
                }
            }
            i++
        }
    }

    val BG_TEXTURE: ResourceLocation = ResourceLocation(modid, "textures/gui/netcontroller.png")
    var colorZone = 0
    var phase:Double = 0.0
    var module:Double = 0.0

    //circle zone pointer
    var ptrMoveX = 0
    var ptrMoveY = 0

    fun getColorZone(){

        this.ptrMoveX = (this.module * cos(this.phase)).toInt()
        this.ptrMoveY = (this.module * sin(this.phase)).toInt()

        //relative to top left corner of the texture
        val circleCenterX = 37
        val circleCenterY = 41

        val iStream: InputStream = Minecraft.getMinecraft().resourceManager.getResource(BG_TEXTURE).inputStream
        val image: BufferedImage = ImageIO.read(iStream)
        val texture: IntArray = image.raster.getPixel(circleCenterX + this.ptrMoveX, circleCenterY + this.ptrMoveY, IntArray(4))
        val currentZone = arrayOf(texture[0], texture[1], texture[2])

        //reference
        val blueZone = arrayOf(113, 151, 220)
        val redZone = arrayOf(197, 0, 37)

        if(currentZone.contentEquals(blueZone)) {
            //blue wave
            this.colorZone = 1
        }else if(currentZone.contentEquals(redZone)) {
            //red wave
            this.colorZone = 2
        }else{
            //grey colorZone
            this.colorZone = 0
        }

    }


}
