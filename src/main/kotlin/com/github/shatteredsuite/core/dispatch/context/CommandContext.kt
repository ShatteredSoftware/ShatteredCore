package com.github.shatteredsuite.core.dispatch.context

import com.github.shatteredsuite.core.data.generic.DataStore
import com.github.shatteredsuite.core.message.MessageProcessorStore
import java.util.*

abstract class CommandContext(val messageProcessorStore: MessageProcessorStore, val debug: Boolean = false) {
    protected abstract fun sendMessage(message: String)
    protected open fun sendDebugMessage(message: String) { sendMessage(message) }

    abstract fun getLocale(): Locale

    fun log(message: String, data: DataStore? = null, locale: Locale? = null) {
        sendMessage(messageProcessorStore.process(message, data, locale ?: getLocale()))
    }

    fun debugLog(message: String, data: () -> DataStore? = { null }, locale: Locale? = null) {
        if (debug) {
            sendDebugMessage(messageProcessorStore.process(message, data(), locale ?: getLocale()))
        }
    }
}