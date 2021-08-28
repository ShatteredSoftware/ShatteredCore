package com.github.shatteredsuite.core.message.lang

import com.github.shatteredsuite.core.message.lang.impl.*
import java.text.NumberFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.floor

abstract class PluralRules {
    companion object {
        private var defaultRules: PluralRules = EnglishPluralRules

        private val rules: MutableMap<Locale, PluralRules> =
            mutableMapOf(
                Locale("en") to EnglishPluralRules,
                Locale("zh") to ChinesePluralRules,
                Locale("hi") to HindiPluralRules,
                Locale("es") to SpanishPluralRules,
                Locale("fr") to FrenchPluralRules
            )

        fun add(rules: PluralRules, locale: Locale) {
            this.rules[Locale(locale.language)] = rules
        }

        fun forLocale(locale: Locale): PluralRules {
            return this.rules[locale] ?: defaultRules
        }

        protected val numberFormat: NumberFormat = NumberFormat.getNumberInstance(Locale.US)

        init {
            numberFormat.isGroupingUsed = false
            numberFormat.maximumFractionDigits = Int.MAX_VALUE
        }
    }

    /**
     * A reimplementation of [Unicode Operands](https://unicode.org/reports/tr35/tr35-numbers.html#Operands).
     */
    protected fun splitNumber(number: Double): Triple<Double, Long, Int> {
        // Absolute Value of the Number
        val n = abs(number)
        // Integral Part
        val i = number.toLong()
        val ai = abs(i)
        // Visible Decimal Digits
        val formatted = numberFormat.format(n - ai)
        val v = if (formatted.length > 2) formatted.length - 2 else 0 // cut off the "0." if it's there

        return Triple(abs(number), floor(number).toLong(), v)
    }

    abstract fun select(count: Double): String
    abstract fun selectOrdinal(count: Double): String
    abstract fun selectRange(first: Double, second: Double): String

}