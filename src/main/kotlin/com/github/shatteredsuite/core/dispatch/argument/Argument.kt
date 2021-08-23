package com.github.shatteredsuite.core.dispatch.argument

interface Argument<T> {
    val expectedArgs: Int
    val name: String
    fun validate(arguments: List<String>, start: Int): ValidationResult<T>
    fun complete(partialArguments: List<String>, start: Int): List<String>
    fun default(): T?
}