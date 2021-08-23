package com.github.shatteredsuite.core.plugin.tasks

abstract class RunStrategy {
    abstract fun execute(runnable: Runnable)
}