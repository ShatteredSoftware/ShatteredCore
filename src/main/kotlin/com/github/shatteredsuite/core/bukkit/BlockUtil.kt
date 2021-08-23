package com.github.shatteredsuite.core.bukkit

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt

object BlockUtil {
    val cartesianFaces: Set<BlockFace> = BlockFace.values().filter { it.isCartesian }.toSet()

    fun getClosestGroundColumn(from: Location, limit: Int = 10, surface: Boolean): Location {
        var top = from.block
        var bot = from.block
        var iter = 0
        while (top.isSolid && bot.isSolid && iter <= limit) {
            iter++
            top = top.getRelative(BlockFace.UP)
            bot = bot.getRelative(BlockFace.DOWN)
        }
        return when {
            bot.isSolid -> bot.location
            top.isSolid -> top.location
            else -> from
        }.add(0.0, if (surface) 1.0 else 0.0, 0.0)
    }

    fun getNearestSafeLocation(
        from: Location,
        radius: Int = 3,
        unsafeSurfaceBlocks: Set<Material> = setOf(Material.LAVA, Material.MAGMA_BLOCK)
    ): Location? {
        val radiusSquared = radius.toDouble().pow(2).roundToInt()
        val queue = LinkedList<Block>()
        queue.push(from.block)
        while (queue.isNotEmpty()) {
            val curr = queue.poll()
            if (!curr.isEmpty) {
                for (face in cartesianFaces) {
                    val block = curr.getRelative(face)
                    val up = block.getRelative(BlockFace.UP)
                    val down = block.getRelative(BlockFace.DOWN)
                    if (block.isEmpty && up.isEmpty && down.isSolid && down.type !in unsafeSurfaceBlocks) {
                        return block.location
                    }
                    if (block.location.distanceSquared(from) <= radiusSquared) {
                        queue.push(block)
                    }
                }
            }
        }
        return null
    }
}