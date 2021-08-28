package com.github.shatteredsuite.core.dispatch.argument

interface Argument<StateType : Any, T> {
    val expectedArgs: Int
    val name: String
    fun validate(arguments: List<String>, start: Int, state: StateType): ArgumentValidationResult<T>
    fun complete(partialArguments: List<String>, start: Int, state: StateType): List<String>
    fun default(state: StateType): T?
}