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
import net.minecraftforge.fml.common.network.NetworkRegistry
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
        NetworkRegistry.INSTANCE.registerGuiHandler(this, ModGuiHandler)
    }


}

@Mod.EventBusSubscriber(modid = mod.modid)
object AutoRegistry {
    var items: MutableList<Item> = mutableListOf<Item>()
    var blocks: MutableList<Block> = mutableListOf<Block>()

    @JvmStatic
    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {

        //items
        event.getRegistry().register(BrownCrustlet)
        event.getRegistry().register(YellowCrustlet)
        event.getRegistry().register(CrustyFleshlet)

        //tools
        event.getRegistry().register(FleshHook)

        //tile entities
        event.getRegistry().register(ItemBlock(CrustPole).setRegistryName(CrustPole.getRegistryName()))

        //basic blocks
        event.getRegistry().register(ItemBlock(BrownCrustOre).setRegistryName(BrownCrustOre.getRegistryName()))
        event.getRegistry().register(ItemBlock(BrownCrustBlock).setRegistryName(BrownCrustBlock.getRegistryName()))
        event.getRegistry().register(ItemBlock(YellowCrustBlock).setRegistryName(YellowCrustBlock.getRegistryName()))
        event.getRegistry().register(ItemBlock(DualityPipe).setRegistryName(DualityPipe.getRegistryName()))
        event.getRegistry().register(ItemBlock(NetController).setRegistryName(NetController.getRegistryName()))
    }

    @JvmStatic
    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {

        //tile entitites
        event.getRegistry().register(CrustPole)
        GameRegistry.registerTileEntity(TileEntityCrustPole::class.java, CrustPole.getRegistryName().toString());
        GameRegistry.registerTileEntity(TileEntityNetController::class.java, CrustPole.getRegistryName().toString());

        //basic blocks
        event.getRegistry().register(BrownCrustOre)
        event.getRegistry().register(BrownCrustBlock)
        event.getRegistry().register(YellowCrustBlock)
        event.getRegistry().register(DualityPipe)
        event.getRegistry().register(NetController)
    }

    @JvmStatic
    @SubscribeEvent
    fun registerModels(event: ModelRegistryEvent) {

        //items
        CrustNFleshMod.proxy.registerItemRenderer(BrownCrustlet, 0)
        CrustNFleshMod.proxy.registerItemRenderer(YellowCrustlet, 0)
        CrustNFleshMod.proxy.registerItemRenderer(CrustyFleshlet,0)
        CrustNFleshMod.proxy.registerItemRenderer(FleshHook,0)

        //tile entities
        CrustNFleshMod.proxy.registerItemRenderer(Item.getItemFromBlock(CrustPole), 0)

        //basic blocks
        CrustNFleshMod.proxy.registerItemRenderer(Item.getItemFromBlock(BrownCrustOre), 0)
        CrustNFleshMod.proxy.registerItemRenderer(Item.getItemFromBlock(BrownCrustBlock), 0)
        CrustNFleshMod.proxy.registerItemRenderer(Item.getItemFromBlock(YellowCrustBlock), 0)
    }
}
