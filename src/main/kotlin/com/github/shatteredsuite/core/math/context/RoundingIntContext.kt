package com.github.shatteredsuite.core.math.context

import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

object RoundingIntContext : NumericContext<Int> {
    override fun identity(a: Number): Int = a.toDouble().roundToInt()

    override fun add(a: Number, b: Number): Int = (a.toDouble() + b.toDouble()).roundToInt()

    override fun subtract(a: Number, b: Number): Int = (a.toDouble() - b.toDouble()).roundToInt()

    override fun multiply(a: Number, b: Number): Int = (a.toDouble() * b.toDouble()).roundToInt()

    override fun divide(a: Number, b: Number): Int = (a.toDouble() / b.toDouble()).roundToInt()

    override fun negate(a: Number): Int = -a.toInt()

    override fun squareRoot(a: Number): Int = sqrt(a.toDouble()).roundToInt()

    override fun power(a: Number, b: Number): Int = a.toDouble().pow(b.toDouble()).roundToInt()

    override fun remainder(a: Number, b: Number): Int = a.toDouble().rem(b.toDouble()).roundToInt()

    override fun compare(a: Number?, b: Number?): Int = when {
        a == null -> 1
        b == null -> -1
        else -> a.toInt().compareTo(b.toInt())
    }
}