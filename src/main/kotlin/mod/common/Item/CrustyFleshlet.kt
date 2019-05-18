package mod.common.Item

import net.minecraft.item.Item
import mod.client.CrustTab

object CrustyFleshlet:Item(){
    init{
        setUnlocalizedName("CrustyFleshlet")
        setRegistryName("crustyfleshlet")
        setCreativeTab(CrustTab)
    }
}
