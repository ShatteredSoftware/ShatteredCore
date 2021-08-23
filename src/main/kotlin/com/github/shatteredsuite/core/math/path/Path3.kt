package com.github.shatteredsuite.core.math.path

import com.github.shatteredsuite.core.math.context.NumericContext
import com.github.shatteredsuite.core.math.vector.Vector3

class Path3<T : Number>(val points: MutableList<out Vector3<T>>, @Transient val context: NumericContext<T>) {

    val distance: T get() = points.zipWithNext().fold(context.zero) { a, (v1, v2) -> context.add(a, v1 distanceTo v2) }

    /**
     * Optimizes this path to only contain points that are at least threshold units apart.
     */
    fun optimize(threshold: T) {
        val toRemove = mutableSetOf<Vector3<T>>()
        with(context) {
            for (i in 0 until points.lastIndex) {
                var curr = points[i]
                var di = 1
                while (curr in toRemove) {
                    curr = points[i - di]
                    di++
                }
                val next = points[i + 1]

                if (curr distanceTo next < threshold) {
                    toRemove += next
                }
            }
        }
        points.removeAll(toRemove)
    }

    fun pointNearestTo(check: Vector3<T>): Vector3<T>? {
        return points.minByOrNull { (check distanceTo it).toDouble() }
    }

    override fun toString(): String {
        return "Path3(points=$points)"
    }
}