package com.github.shatteredsuite.core.message.lang.meta

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.generic.GenericDataStore
import java.util.*

abstract class MessageMetaParser : Identified {
    abstract fun parse(currentMessage: String, arg: String, data: GenericDataStore, locale: Locale): String
}