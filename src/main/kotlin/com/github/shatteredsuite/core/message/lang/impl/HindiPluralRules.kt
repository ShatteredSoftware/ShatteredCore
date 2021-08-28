package com.github.shatteredsuite.core.message.lang.impl

import com.github.shatteredsuite.core.message.lang.PluralRules

object HindiPluralRules : PluralRules() {
    override fun select(count: Double): String {
        val (n, i, _) = splitNumber(count)
        return when {
            i == 0L || n == 1.0 -> "one"
            else -> "other"
        }
    }

    override fun selectOrdinal(count: Double): String {
        val (n, _, _) = splitNumber(count)
        return when (n) {
            1.0 -> "one"
            2.0, 3.0 -> "two"
            4.0 -> "few"
            6.0 -> "many"
            else -> "other"
        }
    }

    override fun selectRange(first: Double, second: Double): String {
        return "${select(first)}+${select(second)}"
    }
}