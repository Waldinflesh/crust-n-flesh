package mod.common.Item

import net.minecraft.item.Item
import mod.client.CrustTab

object BrownCrustlet:Item(){
    init {
        setUnlocalizedName("BrownCrustlet")
        setRegistryName("browncrustlet")
        setCreativeTab(CrustTab)
    }
}
