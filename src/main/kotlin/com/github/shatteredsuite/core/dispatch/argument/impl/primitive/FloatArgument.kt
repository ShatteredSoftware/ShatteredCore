package com.github.shatteredsuite.core.dispatch.argument.impl.primitive

import com.github.shatteredsuite.core.dispatch.argument.ArgumentValidationResult
import com.github.shatteredsuite.core.dispatch.argument.DispatchOptionalArgument
import com.github.shatteredsuite.core.dispatch.context.CommandContext
import org.bukkit.util.StringUtil
import kotlin.math.max
import kotlin.math.min

open class FloatArgument(
    override val name: String,
    override val usageId: String,
    private val min: Float = Float.MIN_VALUE,
    private val max: Float = Float.MAX_VALUE,
    val default: Float = 0f,
    private val completeRange: List<String>
) : DispatchOptionalArgument<CommandContext, Float> {
    override val expectedArgs: Int = 1
    private val range = min..max

    constructor(
        name: String,
        usageId: String,
        min: Float = Float.MIN_VALUE,
        max: Float = Float.MAX_VALUE,
        default: Float = 0f,
        completeMin: Float = 0f,
        completeMax: Float = 10f,
        completeIncrement: Float = 1f
    ) : this(name, usageId, min, max, default,
        generateSequence(max(completeMin, min)) { it + completeIncrement }
            .takeWhile { it <= min(completeMax, max) }
            .map { it.toString() }.toList()
        )

    constructor(
        name: String,
        usageId: String,
        min: Float = Float.MIN_VALUE,
        max: Float = Float.MAX_VALUE,
        default: Float = 0f,
        completeCount: Int = 5,
        completeFn: (index: Int) -> Float
    ) : this(name, usageId, min, max, default,
        (0..completeCount).map { completeFn(it).toString() }
    )

    override fun validate(arguments: List<String>, start: Int, state: CommandContext): ArgumentValidationResult<Float> {
        return try {
            val arg = arguments[start].toFloat()
            if (arg in range) {
                ArgumentValidationResult(success = true, result = arg)
            } else {
                val result = ArgumentValidationResult<Float>(faliureMessageId = "invalid-integer-range")
                result.data["offender"] = arguments[start]
                result.data["max"] = max
                result.data["min"] = min
                result
            }
        } catch (ex: NumberFormatException) {
            val result = ArgumentValidationResult<Float>(faliureMessageId = "invalid-integer")
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

    override fun default(state: CommandContext): Float? {
        return default
    }
}