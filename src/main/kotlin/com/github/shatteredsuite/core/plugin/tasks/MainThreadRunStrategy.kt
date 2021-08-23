package com.github.shatteredsuite.core.plugin.tasks

class MainThreadRunStrategy : RunStrategy() {
    override fun execute(runnable: Runnable) {
        runnable.run()
    }
}