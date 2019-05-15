package mod.common.Item

import net.minecraft.item.Item
import mod.client.CrustTab

object YellowCrustlet:Item(){
    init{
        setUnlocalizedName("YellowCrustlet")
        setRegistryName("yellowcrustlet")
        setCreativeTab(CrustTab)
    }
}
