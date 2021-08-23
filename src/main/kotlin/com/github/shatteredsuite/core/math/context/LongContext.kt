package com.github.shatteredsuite.core.math.context

import kotlin.math.pow
import kotlin.math.roundToLong
import kotlin.math.sqrt

object LongContext : NumericContext<Long> {
    override fun identity(a: Number): Long = a.toLong()

    override fun add(a: Number, b: Number): Long = a.toLong() + b.toLong()

    override fun subtract(a: Number, b: Number): Long = a.toLong() - b.toLong()

    override fun multiply(a: Number, b: Number): Long = a.toLong() * b.toLong()

    override fun divide(a: Number, b: Number): Long = a.toLong() / b.toLong()

    override fun negate(a: Number): Long = -a.toLong()

    override fun squareRoot(a: Number): Long = sqrt(a.toDouble()).roundToLong()

    override fun power(a: Number, b: Number): Long = a.toDouble().pow(b.toDouble()).roundToLong()

    override fun remainder(a: Number, b: Number): Long = a.toDouble().rem(b.toDouble()).toLong()

    override fun compare(a: Number?, b: Number?): Int = when {
        a == null -> 1
        b == null -> -1
        else -> a.toLong().compareTo(b.toLong())
    }
}