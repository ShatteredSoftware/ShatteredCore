package com.github.shatteredsuite.core.dispatch.argument.impl.primitive

import com.github.shatteredsuite.core.dispatch.argument.ArgumentValidationResult
import com.github.shatteredsuite.core.dispatch.argument.DispatchOptionalArgument
import com.github.shatteredsuite.core.dispatch.context.CommandContext
import org.bukkit.util.StringUtil
import kotlin.math.max
import kotlin.math.min

open class IntArgument(
    override val name: String,
    override val usageId: String,
    private val min: Int = Int.MIN_VALUE,
    private val max: Int = Int.MAX_VALUE,
    private val default: Int = 0,
    private val completeRange: List<String>
) : DispatchOptionalArgument<CommandContext, Int> {
    override val expectedArgs: Int = 1
    private val range = min..max

    constructor(
        name: String,
        usageId: String,
        min: Int = Int.MIN_VALUE,
        max: Int = Int.MAX_VALUE,
        default: Int = 0,
        completeMin: Int = 0,
        completeMax: Int = 10,
        completeIncrement: Int = 1
    ) : this(name, usageId, min, max, default,
        (max(min, completeMin)..min(completeMax, max) step completeIncrement).toList().map { it.toString() }
    )

    constructor(
        name: String,
        usageId: String,
        min: Int = Int.MIN_VALUE,
        max: Int = Int.MAX_VALUE,
        default: Int = 0,
        completeCount: Int = 5,
        completeFn: (index: Int) -> Int
    ) : this(name, usageId, min, max, default,
        (0..completeCount).map { completeFn(it).toString() }
    )

    override fun validate(arguments: List<String>, start: Int, state: CommandContext): ArgumentValidationResult<Int> {
        return try {
            val arg = arguments[start].toInt()
            if (arg in range) {
                ArgumentValidationResult(success = true, result = arg)
            } else {
                val result = ArgumentValidationResult<Int>(faliureMessageId = "invalid-integer-range")
                result.data["offender"] = arguments[start]
                result.data["max"] = max
                result.data["min"] = min
                result
            }
        } catch (ex: NumberFormatException) {
            val result = ArgumentValidationResult<Int>(faliureMessageId = "invalid-integer")
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

    override fun default(state: CommandContext): Int? {
        return default
    }
}