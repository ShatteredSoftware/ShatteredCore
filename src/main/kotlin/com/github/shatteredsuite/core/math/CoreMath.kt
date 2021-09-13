package com.github.shatteredsuite.core.math

object CoreMath {
    fun rescale(value: Int, originalMin: Int, originalMax: Int, newMin: Int, newMax: Int) = ((value - originalMin) / (originalMax - originalMin)) * (newMax - newMin) + newMin
    fun rescale(value: Long, originalMin: Long, originalMax: Long, newMin: Long, newMax: Long) = ((value - originalMin) / (originalMax - originalMin)) * (newMax - newMin) + newMin
    fun rescale(value: Float, originalMin: Float, originalMax: Float, newMin: Float, newMax: Float) = ((value - originalMin) / (originalMax - originalMin)) * (newMax - newMin) + newMin
    fun rescale(value: Double, originalMin: Double, originalMax: Double, newMin: Double, newMax: Double) = ((value - originalMin) / (originalMax - originalMin)) * (newMax - newMin) + newMin
}