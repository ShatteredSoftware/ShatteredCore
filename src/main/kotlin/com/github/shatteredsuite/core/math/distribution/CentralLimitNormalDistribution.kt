package com.github.shatteredsuite.core.math.distribution

import kotlin.random.Random

object CentralLimitNormalDistribution {
    fun get(count: Int = 12): Double {
        var acc = 0.0
        for (i in 0..count) {
            acc += Random.nextDouble()
        }
        return acc / count.toDouble()
    }
}