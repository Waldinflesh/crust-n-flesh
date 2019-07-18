package mod.common.Block

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.Block

fun findBlockAdjacent(world: World, pos: BlockPos, blockClass: Class<out Block>): MutableList<BlockPos> {
    var result = mutableListOf<BlockPos>()
    var getBlock = {blockPos: BlockPos -> world.getBlockState(blockPos).getBlock()}
    var x = pos.getX()
    var y = pos.getY()
    var z = pos.getZ()
    if(blockClass.isInstance(getBlock(BlockPos(x + 1, y, z)))) {
        result.add(BlockPos(x + 1, y, z))
    }
    if(blockClass.isInstance(getBlock(BlockPos(x - 1, y, z)))) {
        result.add(BlockPos(x - 1, y, z))
    }
    if(blockClass.isInstance(getBlock(BlockPos(x, y + 1, z)))) {
        result.add(BlockPos(x, y + 1, z))
    }
    if(blockClass.isInstance(getBlock(BlockPos(x, y - 1, z)))) {
        result.add(BlockPos(x, y - 1, z))
    }
    if(blockClass.isInstance(getBlock(BlockPos(x, y, z + 1)))) {
        result.add(BlockPos(x, y, z + 1))
    }
    if(blockClass.isInstance(getBlock(BlockPos(x, y, z - 1)))) {
        result.add(BlockPos(x, y, z - 1))
    }
    return result
}