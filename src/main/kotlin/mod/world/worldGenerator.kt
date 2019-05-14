package mod.world

import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.IChunkGenerator
import net.minecraft.world.gen.feature.WorldGenMinable
import net.minecraftforge.fml.common.IWorldGenerator
import mod.common.Block.*

import java.util.Random

object OreWorldGen:IWorldGenerator{

    override fun generate(random:Random, chunkX:Int, chunkZ:Int, world:World, chunkGenerator:IChunkGenerator, chunkProvider:IChunkProvider) {
        if(world.provider.getDimension() == 0){ // 0 = overworld
            generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider)
        }
    }

    fun generateOverworld(random:Random, chunkX:Int, chunkZ:Int, world:World, chunkGenerator:IChunkGenerator, chunkProvider:IChunkProvider){
        generateOre(BrownCrustOreBlock.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 3, 16, 4 + random.nextInt(4), 25)
    }

    fun generateOre(ore:IBlockState, world:World, random:Random, x:Int, z:Int, minY:Int, maxY:Int, size:Int, chancesPerChunk:Int){
        val deltaY = maxY - minY
        for(i in 0..chancesPerChunk){
            val position:BlockPos = BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16))
            val generator:WorldGenMinable = WorldGenMinable(ore, size)
            generator.generate(world, random, position)
        }
    }
}