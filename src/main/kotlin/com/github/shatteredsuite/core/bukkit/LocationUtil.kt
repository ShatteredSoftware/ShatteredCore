package com.github.shatteredsuite.core.bukkit

import com.github.shatteredsuite.core.math.vector.Vector3
import org.bukkit.Location
import org.bukkit.World

object LocationUtil {
    fun fromVector(v: Vector3<out Number>, world: World? = null, pitch: Float = 0f, yaw: Float = 0f): Location {
        val lv = v.toDouble()
        return Location(world, lv.x, lv.y, lv.z, pitch, yaw)
    }
}