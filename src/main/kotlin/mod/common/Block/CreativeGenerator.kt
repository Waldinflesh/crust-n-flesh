package mod.common.Block

import mod.client.CrustTab
import mod.common.energy.DualityGenerator
import net.minecraft.block.material.Material

object CreativeGenerator: DualityGenerator(100000,0..10000, Material.IRON){

    init{
        setUnlocalizedName("CreativeGenerator")
        setRegistryName("creativegenerator")
        setCreativeTab(CrustTab)

        setHardness(1.5F)
    }

}