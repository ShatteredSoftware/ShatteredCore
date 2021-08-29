package com.github.shatteredsuite.core.message.lang.meta

import com.github.shatteredsuite.core.data.generic.DataStore
import com.github.shatteredsuite.core.message.lang.MessageSet
import java.util.*

class RecurseMetaParser(val set: MessageSet): MessageMetaParser() {
    override val id = "message"

    override fun parse(currentMessage: String, arg: String, data: DataStore, locale: Locale): String {
        return set.get(arg, data, locale) ?: ""
    }
}