package com.github.shatteredsuite.core.extension

import com.github.shatteredsuite.core.data.location.WrappedLocation
import de.tr7zw.changeme.nbtapi.NBTContainer
import org.bukkit.Location

fun Location.toNBT(): NBTContainer {
    val container = NBTContainer()
    container.setDouble("x", this.x)
    container.setDouble("y", this.y)
    container.setDouble("z", this.z)
    container.setFloat("yaw", this.yaw)
    container.setFloat("pitch", this.pitch)
    container.setString("world", this.world?.name)
    return container
}

fun Location.wrapped(): WrappedLocation = WrappedLocation(this)