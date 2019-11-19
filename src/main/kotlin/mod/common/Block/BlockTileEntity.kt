package mod.common.Block
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.block.Block
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EnumFacing

abstract class BlockTileEntity<T: TileEntity>(material: Material) : Block(material) {

    fun getTileEntity(world: IBlockAccess, pos: BlockPos): T {
        return world.getTileEntity(pos) as T
    }

    override fun hasTileEntity(state: IBlockState): Boolean {
        return true
    }

    override fun getStateForPlacement(worldIn: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState {
        return this.defaultState.withProperty(BlockHorizontal.FACING, placer.horizontalFacing.opposite)
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, BlockHorizontal.FACING)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return this.defaultState.withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(meta))
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return state.getValue(BlockHorizontal.FACING).horizontalIndex and 1
    }

    override abstract fun createTileEntity(world: World, state: IBlockState): T

}