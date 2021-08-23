package com.github.shatteredsuite.core.message

class MessageProcessorStore {
    val processors = mutableListOf<MessageProcessor>()

    fun addProcessor(processor: MessageProcessor) {
        this.processors.add(processor)
    }

    fun process(message: String): String {
        var current = message
        processors.forEach { current = it.process(current) }
        return current
    }
}