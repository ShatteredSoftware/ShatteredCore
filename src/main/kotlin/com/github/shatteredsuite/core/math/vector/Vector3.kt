package com.github.shatteredsuite.core.math.vector

import com.github.shatteredsuite.core.math.context.NumericContext
import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt
import kotlin.math.roundToLong

open class Vector3<T : Number>(
    @Transient protected val context: NumericContext<T>,
    @SerializedName("x") protected var _x: T,
    @SerializedName("y") protected var _y: T,
    @SerializedName("z") protected var _z: T
) : Vector3Like {

    override val x: T get() = _x
    override val y: T get() = _y
    override val z: T get() = _z

    fun squareMagnitude(): T {
        return with(context) {
            _x * _x + _y * _y + _z * _z
        }
    }

    operator fun plus(other: Vector3<*>): Vector3<T> {
        return with(context) {
            Vector3(context, _x + other._x, _y + other._y, _z + other._z)
        }
    }

    operator fun minus(other: Vector3<*>): Vector3<T> {
        return with(context) {
            Vector3(context, x - other.x, y - other.y, z - other.z)
        }
    }

    operator fun times(other: Number): Vector3<T> {
        return with(context) {
            Vector3(context, _x * other, _y * other, _z * other)
        }
    }

    operator fun times(other: Vector3<*>): Vector3<T> {
        return with(context) {
            Vector3(context, _x * other._x, _y * other._y, _z * other._z)
        }
    }

    infix fun dot(other: Vector3<*>): T {
        return with(context) {
            _x * other._x + _y * other._y + _z * other._z
        }
    }

    infix fun distanceTo(other: Vector3<out Number>): T {
        return with(context) {
            squareRoot(power(x - other.x, 2) + power(y - other.y, 2) + power(z - other.z, 2))
        }
    }

    fun magnitude(): T {
        return with(context) {
            squareRoot(squareMagnitude())
        }
    }

    fun unit(): Vector3<T> {
        return with(context) {
            val inverseMagnitude = 1 / (magnitude())
            Vector3(this, _x * inverseMagnitude, _y * inverseMagnitude, _z * inverseMagnitude)
        }
    }

    fun clone(context: NumericContext<T> = this.context, x: T = _x, y: T = _y, z: T = _z): Vector3<T> =
        Vector3(context, x, y, z)

    fun asMutable(): MutableVector3<T> = MutableVector3(context, x, y, z)

    operator fun component1(): T = x
    operator fun component2(): T = y
    operator fun component3(): T = z

    fun negate(): Vector3<T> {
        with(context) {
            return Vector3(context, -x, -y, -z)
        }
    }

    fun toFloat(): Vector3Float = Vector3Float(this.x.toFloat(), this.y.toFloat(), this.z.toFloat())

    fun toInt(): Vector3Int = Vector3Int(this.x.toInt(), this.y.toInt(), this.z.toInt())

    fun toDouble(): Vector3Double = Vector3Double(this.x.toDouble(), this.y.toDouble(), this.z.toDouble())

    fun toLong(): Vector3Long = Vector3Long(this.x.toLong(), this.y.toLong(), this.z.toLong())

    fun roundToInt(): Vector3Int =
        Vector3Int(this.x.toDouble().roundToInt(), this.y.toDouble().roundToInt(), this.z.toDouble().roundToInt())

    fun roundToLong(): Vector3Long =
        Vector3Long(this.x.toDouble().roundToLong(), this.y.toDouble().roundToLong(), this.z.toDouble().roundToLong())

    fun rotateXZ(degrees: Number): Vector3<T> {
        val (newX, newZ) = xz.rotate(degrees)
        return Vector3(context, newX, y, newZ)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Vector3<*>) {
            return false
        }
        return with(context) {
            x == identity(other.x) && y == identity(other.y) && z == identity(other.z)
        }
    }

    override fun toString(): String {
        return "Vector3(x=$x, y=$y, z=$z)"
    }

    override fun hashCode(): Int {
        var result = _x.hashCode()
        result = 31 * result + _y.hashCode()
        result = 31 * result + _z.hashCode()
        return result
    }

    // Swizzling
    val xx: Vector2<T> get() = Vector2(context, x, x)
    val xy: Vector2<T> get() = Vector2(context, x, y)
    val xz: Vector2<T> get() = Vector2(context, x, z)
    val yx: Vector2<T> get() = Vector2(context, y, x)
    val yy: Vector2<T> get() = Vector2(context, y, y)
    val yz: Vector2<T> get() = Vector2(context, y, z)
    val zx: Vector2<T> get() = Vector2(context, z, x)
    val zy: Vector2<T> get() = Vector2(context, z, y)
    val zz: Vector2<T> get() = Vector2(context, z, z)
    val xxx: Vector3<T> get() = Vector3(context, x, x, x)
    val xxy: Vector3<T> get() = Vector3(context, x, x, y)
    val xxz: Vector3<T> get() = Vector3(context, x, x, z)
    val xyx: Vector3<T> get() = Vector3(context, x, y, x)
    val xyy: Vector3<T> get() = Vector3(context, x, y, y)
    val xyz: Vector3<T> get() = Vector3(context, x, y, z)
    val xzx: Vector3<T> get() = Vector3(context, x, z, x)
    val xzy: Vector3<T> get() = Vector3(context, x, z, y)
    val xzz: Vector3<T> get() = Vector3(context, x, z, z)
    val yxx: Vector3<T> get() = Vector3(context, y, x, x)
    val yxy: Vector3<T> get() = Vector3(context, y, x, y)
    val yxz: Vector3<T> get() = Vector3(context, y, x, z)
    val yyx: Vector3<T> get() = Vector3(context, y, y, x)
    val yyy: Vector3<T> get() = Vector3(context, y, y, y)
    val yyz: Vector3<T> get() = Vector3(context, y, y, z)
    val yzx: Vector3<T> get() = Vector3(context, y, z, x)
    val yzy: Vector3<T> get() = Vector3(context, y, z, y)
    val yzz: Vector3<T> get() = Vector3(context, y, z, z)
    val zxx: Vector3<T> get() = Vector3(context, z, x, x)
    val zxy: Vector3<T> get() = Vector3(context, z, x, y)
    val zxz: Vector3<T> get() = Vector3(context, z, x, z)
    val zyx: Vector3<T> get() = Vector3(context, z, y, x)
    val zyy: Vector3<T> get() = Vector3(context, z, y, y)
    val zyz: Vector3<T> get() = Vector3(context, z, y, z)
    val zzx: Vector3<T> get() = Vector3(context, z, z, x)
    val zzy: Vector3<T> get() = Vector3(context, z, z, y)
    val zzz: Vector3<T> get() = Vector3(context, z, z, z)
}