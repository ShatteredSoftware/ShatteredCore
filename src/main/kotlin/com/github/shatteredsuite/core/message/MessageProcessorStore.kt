package com.github.shatteredsuite.core.message

import com.github.shatteredsuite.core.data.generic.GenericDataStore
import com.github.shatteredsuite.core.plugin.ShatteredCore
import java.util.*

class MessageProcessorStore {
    val processors = mutableListOf<MessageProcessor>()

    fun addProcessor(processor: MessageProcessor) {
        this.processors.add(processor)
    }

    fun process(message: String, data: GenericDataStore? = null, locale: Locale = ShatteredCore.defaultLocale): String {
        return processors.fold(message) { acc, it -> it.process(acc) }
    }
}