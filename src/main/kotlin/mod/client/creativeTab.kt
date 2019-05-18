package mod.client

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack
import mod.common.Item.BrownCrustlet

object CrustTab : CreativeTabs("CrustTab"){

    override fun getTabIconItem(): ItemStack {
        return ItemStack(BrownCrustlet)
    }
}
