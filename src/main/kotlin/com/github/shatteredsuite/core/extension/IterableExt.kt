package com.github.shatteredsuite.core.extension

fun <T> Iterable<T>.forEachExit(fn: (item: T, index: Int, exit: () -> Unit) -> Unit) {
    var shouldExit = false
    val exit = { shouldExit = true }

    for ((index, item) in this.withIndex()) {
        fn(item, index, exit)
        if (shouldExit) {
            break
        }
    }
}