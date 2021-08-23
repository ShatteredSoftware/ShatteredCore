package com.github.shatteredsuite.core.dispatch.argument

import org.bukkit.util.StringUtil
import kotlin.math.max
import kotlin.math.min

open class IntegralArgument(
    override val name: String,
    private val min: Int = Int.MIN_VALUE,
    private val max: Int = Int.MAX_VALUE,
    val default: Int = 0,
    completeMin: Int = 0,
    completeMax: Int = 10,
    completeIncrement: Int = 1
) : Argument<Int> {
    override val expectedArgs: Int = 1
    private val completeRange =
        listOf(max(min, completeMin)..min(completeMax, max) step completeIncrement).map { it.toString() }

    override fun validate(arguments: List<String>, start: Int): ValidationResult<Int> {
        return try {
            val arg = arguments[start].toInt()
            if (arg in min..max) {
                ValidationResult(success = true, result = arg)
            } else {
                val result = ValidationResult<Int>(faliureMessageId = "invalid-integer-range")
                result.data["offender"] = arguments[start]
                result.data["max"] = max
                result.data["min"] = min
                result
            }
        } catch (ex: NumberFormatException) {
            val result = ValidationResult<Int>(faliureMessageId = "invalid-integer")
            result.data["offender"] = arguments[start]
            result
        }
    }

    override fun complete(partialArguments: List<String>, start: Int): List<String> {
        return StringUtil.copyPartialMatches(partialArguments[start], completeRange, mutableListOf())
    }

    override fun default(): Int? {
        return default
    }
}