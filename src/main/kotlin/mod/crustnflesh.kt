package mod

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy;
import mod.proxy.*
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.block.Block
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

import mod.common.Item.*
import mod.common.Block.*
import mod.world.*
import net.minecraftforge.fml.common.registry.GameRegistry


const val modid = "crustnflesh"
const val name = "Crust n' Flesh"
const val version = "0.02F"

@Mod(modid = modid, name = name, version = version, modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter", acceptedMinecraftVersions = "1.12.2")
object CrustNFleshMod {
    @SidedProxy(serverSide = "mod.proxy.CommonProxy", clientSide="mod.proxy.ClientProxy")
    lateinit var proxy:CommonProxy
    @Mod.EventHandler
    fun preInit(event:FMLPreInitializationEvent) {
        GameRegistry.registerWorldGenerator(OreWorldGen, 3)
    }


}

@Mod.EventBusSubscriber(modid = mod.modid)
object AutoRegistry {
    var items: MutableList<Item> = mutableListOf<Item>()
    var blocks: MutableList<Block> = mutableListOf<Block>()

    @JvmStatic
    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        event.getRegistry().register(BrownCrustlet)
        event.getRegistry().register(YellowCrustlet)
        event.getRegistry().register(CrustyFleshlet)
        event.getRegistry().register(FleshHook)
        event.getRegistry().register(ItemBlock(BrownCrustOreBlock).setRegistryName(BrownCrustOreBlock.getRegistryName()))
        event.getRegistry().register(ItemBlock(BrownCrustBlock).setRegistryName(BrownCrustBlock.getRegistryName()))
        event.getRegistry().register(ItemBlock(YellowCrustBlock).setRegistryName(YellowCrustBlock.getRegistryName()))
    }

    @JvmStatic
    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        event.getRegistry().register(BrownCrustOreBlock)
        event.getRegistry().register(BrownCrustBlock)
        event.getRegistry().register(YellowCrustBlock)
    }

    @JvmStatic
    @SubscribeEvent
    fun registerModels(event: ModelRegistryEvent) {
        CrustNFleshMod.proxy.registerItemRenderer(BrownCrustlet, 0)
        CrustNFleshMod.proxy.registerItemRenderer(YellowCrustlet, 0)
        CrustNFleshMod.proxy.registerItemRenderer(CrustyFleshlet,0)
        CrustNFleshMod.proxy.registerItemRenderer(FleshHook,0)
        CrustNFleshMod.proxy.registerItemRenderer(Item.getItemFromBlock(BrownCrustOreBlock), 0)
        CrustNFleshMod.proxy.registerItemRenderer(Item.getItemFromBlock(BrownCrustBlock), 0)
        CrustNFleshMod.proxy.registerItemRenderer(Item.getItemFromBlock(YellowCrustBlock), 0)
    }
}
