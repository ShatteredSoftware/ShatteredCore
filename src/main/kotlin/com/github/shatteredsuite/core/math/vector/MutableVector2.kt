package com.github.shatteredsuite.core.math.vector

import com.github.shatteredsuite.core.math.context.NumericContext

open class MutableVector2<T : Number>(context: NumericContext<T>, x: T, y: T) : Vector2<T>(context, x, y) {
    fun translate(dx: Number, dy: Number) {
        with(context) {
            _x += dx
            _y += dy
        }
    }

    fun scale(scale: Number) {
        with(context) {
            _x *= scale
            _y *= scale
        }
    }

    fun scale(sx: Number, sy: Number) {
        with(context) {
            _x *= sx
            _y *= sy
        }
    }

    operator fun plus(other: MutableVector2<out Number>): MutableVector2<T> {
        return with(context) {
            MutableVector2(context, x + other.x, y + other.y)
        }
    }

    operator fun timesAssign(other: Number) {
        scale(other)
    }

    operator fun times(other: MutableVector2<out Number>): MutableVector2<T> {
        return with(context) {
            MutableVector2(context, x * other.x, y * other.y)
        }
    }

    operator fun timesAssign(other: MutableVector2<out Number>) {
        this.scale(other.x, other.y)
    }

    infix fun dot(other: MutableVector2<out Number>): T {
        return with(context) {
            x * other.x + y * other.y
        }
    }

    fun normalize() {
        with(context) {
            val inverseMagnitude = 1 / magnitude()
            _x /= inverseMagnitude
            _y /= inverseMagnitude
        }
    }

    fun asImmutable(): Vector2<T> {
        return this
    }
}