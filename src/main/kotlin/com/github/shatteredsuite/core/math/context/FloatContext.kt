package com.github.shatteredsuite.core.math.context

import kotlin.math.pow
import kotlin.math.sqrt

object FloatContext : NumericContext<Float> {
    override fun identity(a: Number): Float = a.toFloat()

    override fun add(a: Number, b: Number): Float = (a.toDouble() + b.toDouble()).toFloat()

    override fun subtract(a: Number, b: Number): Float = (a.toDouble() - b.toDouble()).toFloat()

    override fun multiply(a: Number, b: Number): Float = (a.toDouble() * b.toDouble()).toFloat()

    override fun divide(a: Number, b: Number): Float = (a.toDouble() / b.toDouble()).toFloat()

    override fun negate(a: Number): Float = -a.toFloat()

    override fun squareRoot(a: Number): Float = sqrt(a.toDouble()).toFloat()

    override fun power(a: Number, b: Number): Float = a.toDouble().pow(b.toDouble()).toFloat()

    override fun remainder(a: Number, b: Number): Float = a.toFloat().rem(b.toFloat())

    override fun compare(a: Number?, b: Number?): Int = when {
        a == null -> 1
        b == null -> -1
        else -> a.toFloat().compareTo(b.toFloat())
    }
}