package mod.common.Item

import mod.modid
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent




val brownCrustlet:Item = object:Item(){
    init {
        setUnlocalizedName("BrownCrustlet")
        setRegistryName("browncrustlet")
        setCreativeTab(CreativeTabs.MISC)
    }
}
