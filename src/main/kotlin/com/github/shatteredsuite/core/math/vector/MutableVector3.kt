package com.github.shatteredsuite.core.math.vector

import com.github.shatteredsuite.core.math.context.NumericContext

open class MutableVector3<T : Number>(context: NumericContext<T>, x: T, y: T, z: T) : Vector3<T>(context, x, y, z) {

    fun setX(value: T) {
        this._x = value
    }

    fun setY(value: T) {
        this._y = value
    }

    fun setZ(value: T) {
        this._z = value
    }

    fun translate(dx: Number, dy: Number, dz: Number) {
        with(context) {
            _x += dx
            _y += dy
            _z += dz
        }
    }

    fun scale(scale: Number) {
        with(context) {
            _x *= scale
            _y *= scale
            _z *= scale
        }
    }

    fun scale(sx: Number, sy: Number, sz: Number) {
        with(context) {
            _x *= sx
            _y *= sy
            _z *= sz
        }
    }

    operator fun plus(other: MutableVector3<out Number>): MutableVector3<T> {
        return with(context) {
            MutableVector3(context, x + other.x, y + other.y, z + other.z)
        }
    }

    operator fun timesAssign(other: Number) {
        scale(other)
    }

    operator fun times(other: MutableVector3<out Number>): MutableVector3<T> {
        with(context) {
            return MutableVector3(context, x * other.x, y * other.y, z * other.z)
        }
    }

    operator fun timesAssign(other: MutableVector3<out Number>) {
        this.scale(other.x, other.y, other.z)
    }

    infix fun dot(other: MutableVector3<out Number>): T {
        return with(context) {
            x * other.x + y * other.y + z * other.z
        }
    }


    fun normalize() {
        return with(context) {
            val inverseMagnitude = 1 / magnitude()
            _x /= inverseMagnitude
            _y /= inverseMagnitude
            _z /= inverseMagnitude
        }
    }

    fun asImmutable(): Vector3<T> {
        return this
    }
}