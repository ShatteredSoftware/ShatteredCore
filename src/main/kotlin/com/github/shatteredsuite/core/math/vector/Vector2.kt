package com.github.shatteredsuite.core.math.vector

import com.github.shatteredsuite.core.math.context.DoubleContext
import com.github.shatteredsuite.core.math.context.NumericContext
import com.google.gson.annotations.SerializedName
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.math.sin
import kotlin.random.Random

open class Vector2<T : Number>(
    @Transient protected val context: NumericContext<T>,
    @SerializedName("x") protected var _x: T,
    @SerializedName("y") protected var _y: T
) : Vector2Like {
    companion object {
        fun fromDegrees(degrees: Double, scale: Double = 1.0): Vector2<Double> {
            val radians = degrees * Math.PI / 180
            return Vector2(DoubleContext, cos(radians) * scale, sin(radians) * scale)
        }

        fun randomUnit(): Vector2<Double> {
            val deg = Random.nextDouble(0.0, 360.0)
            return fromDegrees(deg)
        }
    }

    override val x: T get() = _x
    override val y: T get() = _y

    fun squareMagnitude(): T {
        with(context) {
            return _x * _x + _y * _y
        }
    }

    operator fun plus(other: Vector2<out Number>): Vector2<T> {
        return with(context) {
            Vector2(context, _x + other._x, _y + other._y)
        }
    }

    operator fun times(other: Number): Vector2<T> {
        return with(context) {
            Vector2(context, _x * other, _y * other)
        }
    }

    operator fun times(other: Vector2<out Number>): Vector2<T> {
        return with(context) {
            Vector2(context, _x * other._x, _y * other._y)
        }
    }

    operator fun unaryMinus(): Vector2<T> {
        return negate()
    }

    fun negate(): Vector2<T> {
        return with(context) {
            Vector2(context, -_x, -_y)
        }
    }

    fun inverse(): Vector2<T> {
        return with(context) {
            Vector2(context, 1 / x, 1 / y)
        }
    }

    operator fun minus(other: Vector2<out Number>): Vector2<T> {
        return with(context) {
            Vector2(context, _x - other.x, _y - other.y)
        }
    }

    infix fun dot(other: Vector2<out Number>): T {
        return with(context) {
            _x * other.x + _y * other.y
        }
    }

    infix fun distanceTo(other: Vector2<out Number>): T {
        return with(context) {
            squareRoot(power(x - other.x, 2) + power(y - other.y, 2))
        }
    }

    fun magnitude(): T {
        return with(context) {
            squareRoot(squareMagnitude())
        }
    }

    fun unit(): Vector2<T> {
        return with(context) {
            val inverseMagnitude = 1 / (magnitude())
            Vector2(this, _x / inverseMagnitude, _y / inverseMagnitude)
        }
    }

    open fun clone(context: NumericContext<T> = this.context, x: T = _x, y: T = _y): Vector2<T> {
        return Vector2(context, x, y)
    }

    fun asMutable(): MutableVector2<T> {
        return MutableVector2(context, x, y)
    }

    operator fun component1(): T {
        return x
    }

    operator fun component2(): T {
        return y
    }

    fun toFloat(): Vector2Float {
        return Vector2Float(this.x.toFloat(), this.y.toFloat())
    }

    fun toInt(): Vector2Int {
        return Vector2Int(this.x.toInt(), this.y.toInt())
    }

    fun toDouble(): Vector2Double {
        return Vector2Double(this.x.toDouble(), this.y.toDouble())
    }

    fun toLong(): Vector2Long {
        return Vector2Long(this.x.toLong(), this.y.toLong())
    }

    fun roundToInt(): Vector2Int {
        return Vector2Int(this.x.toDouble().roundToInt(), this.y.toDouble().roundToInt())
    }

    fun roundToLong(): Vector2Long {
        return Vector2Long(this.x.toDouble().roundToLong(), this.y.toDouble().roundToLong())
    }


    fun up(z: Number = 0): Vector3<T> {
        return with(context) {
            Vector3(context, x, y, identity(z))
        }
    }

    fun rotate(degrees: Number): Vector2<T> {
        val rad = degrees.toDouble() * Math.PI / 180.0
        return with(context) {
            Vector2(
                context,
                identity(x.toDouble() * cos(rad) - y.toDouble() * sin(rad)),
                identity(x.toDouble() * sin(rad) + y.toDouble() * cos(rad))
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vector2<*>) {
            return false
        }
        return with(context) {
            x == identity(other.x) && y == identity(other.y)
        }
    }

    override fun toString(): String {
        return "Vector2(x=$x, y=$y)"
    }

    override fun hashCode(): Int {
        var result = _x.hashCode()
        result = 31 * result + _y.hashCode()
        return result
    }

    // Swizzling
    val xx: Vector2<T> get() = Vector2(context, x, x)
    val xy: Vector2<T> get() = Vector2(context, x, y)
    val yx: Vector2<T> get() = Vector2(context, y, x)
    val yy: Vector2<T> get() = Vector2(context, y, y)
    val xxx: Vector3<T> get() = Vector3(context, x, x, x)
    val xxy: Vector3<T> get() = Vector3(context, x, x, y)
    val xyx: Vector3<T> get() = Vector3(context, x, y, x)
    val xyy: Vector3<T> get() = Vector3(context, x, y, y)
    val yxx: Vector3<T> get() = Vector3(context, y, x, x)
    val yxy: Vector3<T> get() = Vector3(context, y, x, y)
    val yyx: Vector3<T> get() = Vector3(context, y, y, x)
    val yyy: Vector3<T> get() = Vector3(context, y, y, y)
}