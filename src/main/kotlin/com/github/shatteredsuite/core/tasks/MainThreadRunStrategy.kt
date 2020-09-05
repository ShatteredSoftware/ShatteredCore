package com.github.shatteredsuite.core.tasks

class MainThreadRunStrategy : RunStrategy() {
    override fun run(runnable: Runnable) {
        runnable.run()
    }
}