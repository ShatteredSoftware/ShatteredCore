package com.github.shatteredsuite.core.math.context

interface NumericContext<T : Number> : Comparator<Number> {
    val zero: T
        get() = identity(0)

    fun identity(a: Number): T
    fun add(a: Number, b: Number): T
    fun subtract(a: Number, b: Number): T
    fun multiply(a: Number, b: Number): T
    fun divide(a: Number, b: Number): T
    fun remainder(a: Number, b: Number): T
    fun negate(a: Number): T
    fun squareRoot(a: Number): T
    fun power(a: Number, b: Number): T

    fun abs(a: Number): T {
        return if (a < identity(0)) negate(a) else identity(a)
    }

    fun sign(a: Number): T {
        val n = identity(a)
        return if (n == 0) {
            n
        } else identity(a) / abs(a)
    }

    fun mod(a: Number, b: Number): T {
        val r = remainder(a, b)
        return if (sign(r) == -1) r + b else r
    }

    operator fun Number.plus(other: Number): T {
        return this@NumericContext.add(this, other)
    }

    operator fun Number.minus(other: Number): T {
        return this@NumericContext.subtract(this, other)
    }

    operator fun Number.times(other: Number): T {
        return this@NumericContext.multiply(this, other)
    }

    operator fun Number.rem(other: Number): T {
        return this@NumericContext.remainder(this, other)
    }

    operator fun Number.div(other: Number): T {
        return this@NumericContext.divide(this, other)
    }

    operator fun Number.unaryMinus(): T {
        return this@NumericContext.negate(this)
    }

    operator fun Number.compareTo(other: Number): Int {
        return this@NumericContext.compare(this, other)
    }

    infix fun Number.toPower(other: Number): T {
        return this@NumericContext.power(this, other)
    }
}

