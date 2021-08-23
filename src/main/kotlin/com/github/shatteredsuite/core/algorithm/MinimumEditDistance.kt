package com.github.shatteredsuite.core.algorithm

import kotlin.math.min

object MinimumEditDistance {
    fun run(s1: String, s2: String): Int {
        return distance(s1, s2).first
    }

    fun runGetCache(s1: String, s2: String): Pair<Int, List<List<Int>>> {
        return distance(s1, s2)
    }

    private fun distance(s1: String, s2: String): Pair<Int, List<List<Int>>> {
        val s1Length = s1.length
        val s2Length = s2.length

        val cache = listOf(MutableList(s1Length) { 0 }, MutableList(s2Length) { 0 })

        for (i in 0..s1Length) {
            cache[0][i] = i
        }

        for (i in 1..s2Length) {
            for (j in 0..s1Length) {
                cache[i % 2][j] = when {
                    j == 0 -> i
                    s1[j - 1] == s2[i - 1] -> cache[(i - 1) % 2][j - 1]
                    else -> 1 + min(min(cache[(i - 1) % 2][j], cache[i % 2][j - 1]), cache[(i - 1) % 2][j - 1])
                }
            }
        }

        return cache[s2Length % 2][s1Length] to cache
    }
}