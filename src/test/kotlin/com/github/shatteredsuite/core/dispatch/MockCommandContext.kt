package com.github.shatteredsuite.core.dispatch

import com.github.shatteredsuite.core.dispatch.context.CommandContext
import com.github.shatteredsuite.core.message.MessageProcessorStore
import java.util.*

class MockCommandContext : CommandContext(MessageProcessorStore()) {
    var messages: MutableList<String> = mutableListOf()

    override fun sendMessage(message: String) {
        messages.add(message)
    }

    override fun getLocale(): Locale {
        return Locale.US
    }
}