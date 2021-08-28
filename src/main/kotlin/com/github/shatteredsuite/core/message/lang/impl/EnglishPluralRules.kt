package com.github.shatteredsuite.core.message.lang.impl

import com.github.shatteredsuite.core.message.lang.PluralRules

object EnglishPluralRules : PluralRules() {
    override fun select(count: Double): String {
        val (_, i, v) = splitNumber(count)
        return when {
            (i == 1L) && (v == 0) -> "one"
            else -> "other"
        }
    }

    override fun selectOrdinal(count: Double): String {
        val (n, _, _) = splitNumber(count)
        return when {
            n % 10.0 == 1.0 && n % 100.0 != 11.0 -> "one"
            n % 10.0 == 2.0 && n % 100.0 != 12.0 -> "two"
            n % 10.0 == 3.0 && n % 100.0 != 13.0 -> "few"
            else -> "other"
        }
    }

    override fun selectRange(first: Double, second: Double): String {
        return "${select(first)}+${select(second)}"
    }
}