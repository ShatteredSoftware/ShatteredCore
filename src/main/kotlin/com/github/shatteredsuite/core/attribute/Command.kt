package com.github.shatteredsuite.core.attribute

interface Command<T> {
    fun apply(state: T)
    fun unapply(state: T)
}