package mod.Block

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World


val BrownCrustOreBlock: Block = object : Block {
    init {
        unlocalizedName = "Brown Crust Ore"
        registryName = ResourceLocation(mod.modid)
    }
}
