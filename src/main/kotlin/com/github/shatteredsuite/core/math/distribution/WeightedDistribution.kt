package com.github.shatteredsuite.core.math.distribution

import kotlin.random.Random

object WeightedDistribution {
    fun <T> get(weights: List<ValueWeight<T>>): T {
        val total = weights.sumOf { it.weight }
        return computeWithTotal(weights, total)
    }

    fun <T> of(weights: List<ValueWeight<T>>): WeightedGenerator<T> {
        return WeightedGenerator(weights)
    }

    private fun <T> computeWithTotal(weights: List<ValueWeight<T>>, total: Int): T {
        var max = Random.nextInt(total)
        var idx = 0
        var curr = weights[idx]
        while (max > 0) {
            max -= curr.weight
            curr = weights[++idx]
        }
        return curr.value
    }

    class WeightedGenerator<T>(val weights: List<ValueWeight<T>>) {
        val total = weights.sumOf { it.weight }

        fun next(): T {
            return computeWithTotal(weights, total)
        }
    }
}