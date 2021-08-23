package com.github.shatteredsuite.core.math

import com.github.shatteredsuite.core.math.context.NumericContext
import com.github.shatteredsuite.core.math.vector.MutableVector2
import com.github.shatteredsuite.core.math.vector.Vector2

class Rectangle<T : Number>(
    val origin: MutableVector2<T>,
    val delta: MutableVector2<T>,
    val context: NumericContext<T>
) {
    val x get() = origin.x
    val y get() = origin.y
    val w get() = delta.x
    val h get() = delta.y

    /**
     * Assumes a normalized vector. Returns a vector where the x and y coordinates are normalized to the rectangle. An x
     * equal to the rectangle's x (in other words, on the left side of the rectangle) will result in an x component of 0,
     * and an x on the right side of the rectangle will result in an x component of 1. Similar rules apply to y
     * coordinates. Points outside of the rectangle will result in components scaled to the size of this rectangle.
     */
    fun toLocal(point: Vector2<out Number>): Vector2<Double> {
        return ((point - origin) * delta.inverse()).toDouble()
    }

    /**
     * Returns a new rectangle that ensures positive width/height; negative width and height will result in a transformed
     * rectangle that matches the original by offsetting the origin.
     */
    fun normalized(): Rectangle<T> {
        with(context) {
            val ox = if (delta.x < 0) {
                origin.x - delta.x
            } else origin.x
            val oy = if (delta.y < 0) {
                origin.y - delta.y
            } else origin.y
            val dx = abs(delta.x)
            val dy = abs(delta.y)
            return Rectangle(MutableVector2(context, ox, oy), MutableVector2(context, dx, dy), context)
        }
    }
}