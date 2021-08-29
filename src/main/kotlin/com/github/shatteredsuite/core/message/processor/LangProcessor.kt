package com.github.shatteredsuite.core.message.processor

import com.github.shatteredsuite.core.data.generic.DataStore
import com.github.shatteredsuite.core.data.generic.GenericDataStore
import com.github.shatteredsuite.core.message.MessageProcessor
import com.github.shatteredsuite.core.message.lang.MessageSet
import java.util.*

class LangProcessor(var messageSet: MessageSet) : MessageProcessor {
    override fun process(message: String, data: DataStore?, locale: Locale): String {
        return messageSet.get(message, data, locale) ?: throw Exception("Missing message $message.")
    }
}