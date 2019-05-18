package mod.common.Block

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import mod.client.CrustTab

object YellowCrustBlock : Block(Material.IRON){

    init{
        setUnlocalizedName("YellowCrustBlock")
        setRegistryName("yellowcrustblock")
        setCreativeTab(CrustTab)

        setHardness(1.5F)
    }
}