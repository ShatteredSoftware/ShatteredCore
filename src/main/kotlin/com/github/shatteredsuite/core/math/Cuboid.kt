package com.github.shatteredsuite.core.math

import com.github.shatteredsuite.core.math.vector.MutableVector3
import com.github.shatteredsuite.core.math.vector.Vector3
import software.shattered.corelib.math.context.NumericContext

data class Cuboid<T : Number>(
    val origin: MutableVector3<T>,
    val delta: MutableVector3<T>,
    val context: NumericContext<T>
) : NumericContext<T> by context {
    val x get() = origin.x
    val y get() = origin.y
    val z get() = origin.z
    val l get() = delta.x
    val h get() = delta.y
    val w get() = delta.z

    fun expand(dx: T, dy: T, dz: T) {
        delta.translate(dx, dy, dz)
    }

    fun translate(dx: T, dy: T, dz: T) {
        origin.translate(dx, dy, dz)
    }

    fun contains(point: Vector3<*>): Boolean {
        val (px, py, pz) = point
        val (ox, oy, oz) = origin
        return px > ox && px < getDiagonalX()
                && py > oy && py < getDiagonalY()
                && pz > oz && pz < getDiagonalZ()
    }

    fun getDiagonalX(): T = origin.x + delta.x

    fun getDiagonalY(): T = origin.y + delta.y

    fun getDiagonalZ(): T = origin.z + delta.z

    /**
     * Returns a new cuboid where the delta is guaranteed to be a positive vector by shifting the origin if needed.
     */
    fun normalized(): Cuboid<T> {
        val ox = if (delta.x < 0) {
            origin.x - delta.x
        } else origin.x
        val oy = if (delta.y < 0) {
            origin.y - delta.y
        } else origin.y
        val oz = if (delta.z < 0) {
            origin.z - delta.z
        } else origin.z
        val dx = abs(delta.x)
        val dy = abs(delta.y)
        val dz = abs(delta.z)
        return Cuboid(MutableVector3(this.context, ox, oy, oz), MutableVector3(this.context, dx, dy, dz), this.context)
    }
}