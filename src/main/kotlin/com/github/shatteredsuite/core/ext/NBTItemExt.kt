package com.github.shatteredsuite.core.ext

import de.tr7zw.changeme.nbtapi.NBTItem
import org.bukkit.Bukkit
import org.bukkit.Location

fun NBTItem.getLocation(offset: String = "") : Location {
    val x: Double = this.getDouble(offset + "x")
    val y: Double = this.getDouble(offset + "y")
    val z: Double = this.getDouble(offset + "z")
    val yaw: Float = this.getFloat(offset + "yaw")
    val pitch: Float = this.getFloat(offset + "pitch")
    val worldName: String = this.getString(offset + "world")
    val world = Bukkit.getWorld(worldName)
    return Location(world, x, y, z, yaw, pitch)
}