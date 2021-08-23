package com.github.shatteredsuite.core.message

import com.github.shatteredsuite.core.data.persistence.GenericDataStore
import com.ibm.icu.util.ULocale

interface MessageProcessor {
    fun process(message: String, data: GenericDataStore? = null, locale: ULocale = ULocale.US): String
}