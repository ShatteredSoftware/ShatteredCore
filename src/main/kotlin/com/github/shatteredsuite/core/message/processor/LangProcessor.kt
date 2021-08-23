package com.github.shatteredsuite.core.message.processor

import com.github.shatteredsuite.core.message.MessageProcessor
import com.github.shatteredsuite.core.message.lang.MessageSet
import com.ibm.icu.util.ULocale

class LangProcessor(var messageSet: MessageSet): MessageProcessor {
    override fun process(message: String, locale: ULocale): String {
        return messageSet.get(message, locale = locale) ?: throw Exception("Missing message $message.")
    }
}