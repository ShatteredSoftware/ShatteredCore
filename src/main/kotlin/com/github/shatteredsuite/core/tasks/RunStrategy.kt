package com.github.shatteredsuite.core.tasks

abstract class RunStrategy {
    abstract fun run(runnable: Runnable)
}