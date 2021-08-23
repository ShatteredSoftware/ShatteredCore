package com.github.shatteredsuite.core.message

import com.ibm.icu.util.ULocale

interface MessageProcessor {
    fun process(message: String, locale: ULocale): String
}