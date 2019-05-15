package mod.common.Block

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import mod.client.CrustTab

object BrownCrustBlock : Block(Material.IRON){

    init{
        setUnlocalizedName("BrownCrustBlock")
        setRegistryName("browncrustblock")
        setCreativeTab(CrustTab)

        setHardness(1.5F)
    }
}