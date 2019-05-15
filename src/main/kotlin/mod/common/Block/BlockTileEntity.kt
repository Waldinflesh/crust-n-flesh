package mod.common.Block
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.block.Block

abstract class BlockTileEntity<T: TileEntity>(material: Material) : Block(material) {

    fun getTileEntity(world: IBlockAccess, pos: BlockPos): T {
        return world.getTileEntity(pos) as T
    }

    override fun hasTileEntity(state: IBlockState): Boolean {
        return true
    }

    override abstract fun createTileEntity(world: World, state: IBlockState): T

}