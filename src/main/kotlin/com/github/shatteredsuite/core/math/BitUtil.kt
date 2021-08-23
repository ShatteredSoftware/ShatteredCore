package com.github.shatteredsuite.core.math

import kotlin.math.pow

object BitUtil {
    fun nthBit(number: Int, bit: Int): Byte {
        return ((number and 2.0.pow(bit).toInt()) shl bit).toByte()
    }

    fun nthBit(number: Long, bit: Int): Byte {
        return ((number and 2.0.pow(bit).toLong()) shl bit).toByte()
    }
}