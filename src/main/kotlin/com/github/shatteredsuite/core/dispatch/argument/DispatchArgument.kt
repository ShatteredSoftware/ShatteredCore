package com.github.shatteredsuite.core.dispatch.argument

interface DispatchArgument<in StateType, out T> {
    val expectedArgs: Int
    val name: String
    val usageId: String
    fun validate(arguments: List<String>, start: Int, state: StateType): ArgumentValidationResult<out T>
    fun complete(partialArguments: List<String>, start: Int, state: StateType): List<String>
}