package com.github.shatteredsuite.core.dispatch

import com.github.shatteredsuite.core.dispatch.context.CommandContext
import com.github.shatteredsuite.core.message.MessageProcessorStore
import java.util.*

class MockCommandContext(debug: Boolean = false) : CommandContext(MessageProcessorStore(), debug=debug) {
    var messages: MutableList<String> = mutableListOf()

    override fun sendMessage(message: String) {
        messages.add(message)
    }

    override fun sendDebugMessage(message: String) {
        println(message)
    }

    override fun getLocale(): Locale {
        return Locale.US
    }
}