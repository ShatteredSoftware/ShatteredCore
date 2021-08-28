package com.github.shatteredsuite.core.extension

fun <T> T.tee(f: (T) -> Unit): T {
    f(this)
    return this
}