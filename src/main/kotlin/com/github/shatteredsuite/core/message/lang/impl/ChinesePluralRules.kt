package com.github.shatteredsuite.core.message.lang.impl

import com.github.shatteredsuite.core.message.lang.PluralRules

object ChinesePluralRules : PluralRules() {
    override fun select(count: Double): String {
        return "other"
    }

    override fun selectOrdinal(count: Double): String {
        return "other"
    }

    override fun selectRange(first: Double, second: Double): String {
        return "other+other"
    }
}