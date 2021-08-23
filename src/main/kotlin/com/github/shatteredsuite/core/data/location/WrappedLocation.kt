package com.github.shatteredsuite.core.data.location

import com.github.shatteredsuite.core.math.vector.Vector3Like
import org.bukkit.Location

@JvmInline
value class WrappedLocation(val location: Location) : Vector3Like {
    override val x: Number get() = location.x
    override val y: Number get() = location.y
    override val z: Number get() = location.z
}