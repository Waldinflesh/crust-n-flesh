package mod.common.Item

import mod.modid
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


val brownCrustlet:Item = object:Item(){
    init{
        setUnlocalizedName("BrownCrustlet")
        setRegistryName("BrownCrustlet")
        setCreativeTab(CreativeTabs.MISC)
    }


}
@Mod.EventBusSubscriber(modid= modid)
object RegistryHandler{
    @JvmStatic
    @SubscribeEvent
    fun onItemRegister(event: RegistryEvent.Register<Item>){
        event.getRegistry().register(brownCrustlet)
    }
}
