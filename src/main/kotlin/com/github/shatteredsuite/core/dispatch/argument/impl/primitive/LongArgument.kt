package com.github.shatteredsuite.core.dispatch.argument.impl.primitive

import com.github.shatteredsuite.core.dispatch.argument.ArgumentValidationResult
import com.github.shatteredsuite.core.dispatch.argument.DispatchOptionalArgument
import com.github.shatteredsuite.core.dispatch.context.CommandContext
import org.bukkit.util.StringUtil
import kotlin.math.max
import kotlin.math.min

open class LongArgument(
    override val name: String,
    override val usageId: String,
    private val min: Long = Long.MIN_VALUE,
    private val max: Long = Long.MAX_VALUE,
    private val default: Long = 0,
    private val completeRange: List<String>
) : DispatchOptionalArgument<CommandContext, Long> {
    override val expectedArgs: Int = 1
    private val range = min..max

    constructor(
        name: String,
        usageId: String,
        min: Long = Long.MIN_VALUE,
        max: Long = Long.MAX_VALUE,
        default: Long = 0,
        completeMin: Long = 0,
        completeMax: Long = 10,
        completeIncrement: Long = 1
    ) : this(name, usageId, min, max, default,
        (max(min, completeMin)..min(completeMax, max) step completeIncrement).toList().map { it.toString() }
    )

    constructor(
        name: String,
        usageId: String,
        min: Long = Long.MIN_VALUE,
        max: Long = Long.MAX_VALUE,
        default: Long = 0,
        completeCount: Int = 5,
        completeFn: (index: Int) -> Long
    ) : this(name, usageId, min, max, default,
        (0..completeCount).map { completeFn(it).toString() }
    )

    override fun validate(arguments: List<String>, start: Int, state: CommandContext): ArgumentValidationResult<Long> {
        return try {
            val arg = arguments[start].toLong()
            if (arg in range) {
                ArgumentValidationResult(success = true, result = arg)
            } else {
                val result = ArgumentValidationResult<Long>(faliureMessageId = "invalid-integer-range")
                result.data["offender"] = arguments[start]
                result.data["max"] = max
                result.data["min"] = min
                result
            }
        } catch (ex: NumberFormatException) {
            val result = ArgumentValidationResult<Long>(faliureMessageId = "invalid-integer")
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

    override fun default(state: CommandContext): Long? {
        return default
    }
}