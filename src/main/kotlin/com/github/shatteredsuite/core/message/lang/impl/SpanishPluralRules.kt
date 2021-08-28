package com.github.shatteredsuite.core.message.lang.impl

import com.github.shatteredsuite.core.message.lang.PluralRules

object SpanishPluralRules : PluralRules() {
    override fun select(count: Double): String {
        val (n, _, _) = splitNumber(count)
        return when (n) {
            1.0 -> "one"
            else -> "other"
        }
    }

    override fun selectOrdinal(count: Double): String {
        return "other"
    }

    override fun selectRange(first: Double, second: Double): String {
        return "${select(first)}+${select(second)}"
    }
}