package mod.common.Block

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import mod.common.Item.BrownCrustlet
import mod.client.CrustTab

val BrownCrustOreBlock: Block = object : Block(Material.ROCK) {

    //Drop values
    val minDropAmount = 4
    val maxDropAmount = 8
    val toDrop = BrownCrustlet

    init {
        setUnlocalizedName("BrownCrustOre")
        setRegistryName("browncrustore")
        setCreativeTab(CrustTab)

        setHardness(1.5F)
    }

    override fun getItemDropped(state: IBlockState, random: java.util.Random, fortune : Int): Item {
        return toDrop
    }

    override fun quantityDropped(state: IBlockState, fortune: Int, random: java.util.Random): Int {

        if(fortune == 0){
            return (minDropAmount..maxDropAmount).random()
        }else{
            return (minDropAmount*fortune..maxDropAmount*fortune).random()
        }
    }

}
