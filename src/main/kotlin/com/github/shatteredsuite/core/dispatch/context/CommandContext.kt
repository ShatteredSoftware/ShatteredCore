package com.github.shatteredsuite.core.dispatch.context

import com.github.shatteredsuite.core.data.generic.DataStore
import com.github.shatteredsuite.core.message.MessageProcessorStore
import java.util.*

abstract class CommandContext(val messageProcessorStore: MessageProcessorStore) {
    protected abstract fun sendFailureMessage(message: String)

    abstract fun getLocale(): Locale

    fun logFailure(message: String, data: DataStore? = null, locale: Locale? = null) {
        messageProcessorStore.process(message, data, locale ?: getLocale())
    }
}