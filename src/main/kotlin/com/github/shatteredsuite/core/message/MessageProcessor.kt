package com.github.shatteredsuite.core.message

import com.github.shatteredsuite.core.data.generic.GenericDataStore
import java.util.*

interface MessageProcessor {
    fun process(message: String, data: GenericDataStore? = null, locale: Locale = Locale.US): String
}