package com.github.shatteredsuite.core.dispatch.argument.impl.primitive

import com.github.shatteredsuite.core.dispatch.argument.ArgumentValidationResult
import com.github.shatteredsuite.core.dispatch.argument.DispatchOptionalArgument
import org.bukkit.util.StringUtil

open class ChoiceArgument<T>(
    override val name: String,
    val provider: (arg: String) -> T?,
    val choiceProvider: () -> Collection<String>,
    override val usageId: String,
    private val default: T? = null,
    private val dynamic: Boolean = true
) : DispatchOptionalArgument<Any?, T> {
    override val expectedArgs: Int = 1
    private val choices = if (!dynamic) choiceProvider() else setOf()

    override fun validate(arguments: List<String>, start: Int, state: Any?): ArgumentValidationResult<T> {
        val result = provider(arguments[start]) ?: return ArgumentValidationResult(faliureMessageId = "invalid-choice")
        return ArgumentValidationResult(success = true, result)
    }

    override fun complete(partialArguments: List<String>, start: Int, state: Any?): List<String> {
        val dest = mutableListOf<String>()
        val choices = if (dynamic) choiceProvider() else choices
        StringUtil.copyPartialMatches(partialArguments[start], choices, dest)
        return dest
    }

    override fun default(state: Any?): T? {
        return default
    }
}