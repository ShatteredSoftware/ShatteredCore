package com.github.shatteredsuite.core.dispatch.argument

import org.bukkit.util.StringUtil

open class ChoiceArgument<T>(
    override val name: String,
    val provider: (arg: String) -> T?,
    val choiceProvider: () -> Collection<String>,
    val default: T? = null,
    val dynamic: Boolean = true
) : Argument<T> {
    override val expectedArgs: Int = 1
    private val choices = if (!dynamic) choiceProvider() else setOf()

    override fun validate(arguments: List<String>, start: Int): ValidationResult<T> {
        val result = provider(arguments[start]) ?: return ValidationResult(faliureMessageId = "invalid-choice")
        return ValidationResult(success = true, result)
    }

    override fun complete(partialArguments: List<String>, start: Int): List<String> {
        val dest = mutableListOf<String>()
        if (dynamic) {
            StringUtil.copyPartialMatches(partialArguments[start], choiceProvider(), dest)
        } else {
            StringUtil.copyPartialMatches(partialArguments[start], choices, dest)
        }
        return dest
    }

    override fun default(): T? {
        return default
    }
}