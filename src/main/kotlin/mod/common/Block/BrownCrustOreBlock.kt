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

val BrownCrustOreBlock: Block = object : Block(Material.ROCK) {

    init {
        setUnlocalizedName("BrownCrustOre")
        setRegistryName("browncrustore")
        setCreativeTab(CreativeTabs.MISC)
    }
}


@Mod.EventBusSubscriber(modid = mod.modid)
object RegistryHandler {
    @JvmStatic
    @SubscribeEvent
    fun onBlockRegister(event: RegistryEvent.Register<Block>) {
        event.registry.registerAll(BrownCrustOreBlock)
    }

    @JvmStatic
    @SubscribeEvent
    fun onItemRegister(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(ItemBlock(BrownCrustOreBlock).setRegistryName(BrownCrustOreBlock.getRegistryName()));
    }

    @JvmStatic
    @SubscribeEvent
    fun registerModels(event: ModelRegistryEvent) {
        ModelLoader.setCustomModelResourceLocation(ItemBlock(BrownCrustOreBlock).setRegistryName("browncrustore"), 0, ModelResourceLocation("crustnflesh:browncrustore", "inventory"))
    }
}