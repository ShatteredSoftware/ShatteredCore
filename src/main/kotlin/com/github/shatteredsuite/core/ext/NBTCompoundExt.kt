package com.github.shatteredsuite.core.ext

import de.tr7zw.changeme.nbtapi.NBTCompound
import org.bukkit.Bukkit
import org.bukkit.Location

fun NBTCompound.toLocation(): Location {
    val x: Double = this.getDouble("x")
    val y: Double = this.getDouble("y")
    val z: Double = this.getDouble("z")
    val yaw: Float = this.getFloat("yaw")
    val pitch: Float = this.getFloat("pitch")
    val worldName: String = this.getString("world")
    val world = Bukkit.getWorld(worldName)
    return Location(world, x, y, z, yaw, pitch)
}