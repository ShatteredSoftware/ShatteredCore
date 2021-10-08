package com.github.shatteredsuite.core.dispatch.argument.impl.primitive

import com.github.shatteredsuite.core.dispatch.argument.ArgumentValidationResult
import com.github.shatteredsuite.core.dispatch.argument.DispatchOptionalArgument
import com.github.shatteredsuite.core.dispatch.context.CommandContext
import org.bukkit.util.StringUtil
import kotlin.math.max
import kotlin.math.min

open class DoubleArgument(
    override val name: String,
    override val usageId: String,
    private val min: Double = Double.MIN_VALUE,
    private val max: Double = Double.MAX_VALUE,
    val default: Double = 0.0,
    private val completeRange: List<String>
) : DispatchOptionalArgument<CommandContext, Double> {
    override val expectedArgs: Int = 1
    private val range = min..max

    constructor(
        name: String,
        usageId: String,
        min: Double = Double.MIN_VALUE,
        max: Double = Double.MAX_VALUE,
        default: Double = 0.0,
        completeMin: Double = 0.0,
        completeMax: Double = 10.0,
        completeIncrement: Double = 1.0
    ) : this(name, usageId, min, max, default,
        generateSequence(max(completeMin, min)) { it + completeIncrement }
            .takeWhile { it <= min(completeMax, max) }
            .map { it.toString() }.toList()
        )

    constructor(
        name: String,
        usageId: String,
        min: Double = Double.MIN_VALUE,
        max: Double = Double.MAX_VALUE,
        default: Double = 0.0,
        completeCount: Int = 5,
        completeFn: (index: Int) -> Double
    ) : this(name, usageId, min, max, default,
        (0..completeCount).map { completeFn(it).toString() }
    )

    override fun validate(arguments: List<String>, start: Int, state: CommandContext): ArgumentValidationResult<Double> {
        return try {
            val arg = arguments[start].toDouble()
            if (arg in range) {
                ArgumentValidationResult(success = true, result = arg)
            } else {
                val result = ArgumentValidationResult<Double>(faliureMessageId = "invalid-integer-range")
                result.data["offender"] = arguments[start]
                result.data["max"] = max
                result.data["min"] = min
                result
            }
        } catch (ex: NumberFormatException) {
            val result = ArgumentValidationResult<Double>(faliureMessageId = "invalid-integer")
            result.data["offender"] = arguments[start]
            result
        }
    }

    override fun complete(partialArguments: List<String>, start: Int, state: CommandContext): List<String> {
        if (partialArguments.size <= start) {
            return StringUtil.copyPartialMatches("", completeRange, mutableListOf())
        }
        return StringUtil.copyPartialMatches(partialArguments[start], completeRange, mutableListOf())
    }

    override fun default(state: CommandContext): Double? {
        return default
    }
}