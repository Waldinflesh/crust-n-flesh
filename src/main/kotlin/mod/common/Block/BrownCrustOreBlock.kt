package mod.common.Block

import net.minecraft.block.Block
import net.minecraft.util.ResourceLocation


val BrownCrustOreBlock: Block = object : Block {
    init {
        unlocalizedName = "Brown Crust Ore"
        registryName = ResourceLocation(mod.modid)
    }
}
