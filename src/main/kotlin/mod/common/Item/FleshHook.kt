package mod.common.Item

import net.minecraft.item.Item
import mod.client.CrustTab

object FleshHook:Item(){
    init{
        setUnlocalizedName("FleshHook")
        setRegistryName("fleshhook")
        setCreativeTab(CrustTab)
    }
}
