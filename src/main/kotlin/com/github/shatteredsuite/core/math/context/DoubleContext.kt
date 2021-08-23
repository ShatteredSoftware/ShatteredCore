package com.github.shatteredsuite.core.math.context

import kotlin.math.pow
import kotlin.math.sqrt

object DoubleContext : NumericContext<Double> {
    override fun identity(a: Number): Double = a.toDouble()

    override fun add(a: Number, b: Number): Double = a.toDouble() + b.toDouble()

    override fun subtract(a: Number, b: Number): Double = a.toDouble() - b.toDouble()

    override fun multiply(a: Number, b: Number): Double = a.toDouble() * b.toDouble()

    override fun divide(a: Number, b: Number): Double = a.toDouble() / b.toDouble()

    override fun negate(a: Number): Double = -a.toDouble()

    override fun squareRoot(a: Number): Double = sqrt(a.toDouble())

    override fun power(a: Number, b: Number): Double = a.toDouble().pow(b.toDouble())

    override fun remainder(a: Number, b: Number): Double = a.toDouble().rem(b.toDouble())

    override fun compare(a: Number?, b: Number?): Int = when {
        a == null -> 1
        b == null -> -1
        else -> a.toDouble().compareTo(b.toDouble())
    }
}