package com.github.shatteredsuite.core.message.lang

import com.github.shatteredsuite.core.data.persistence.GenericDataStore
import com.github.shatteredsuite.core.extension.getSafe
import com.ibm.icu.text.PluralRules
import com.ibm.icu.util.ULocale
import org.bukkit.configuration.file.YamlConfiguration

class MessageSet {
    private val messages: MutableMap<ULocale, MutableMap<String, String>> = mutableMapOf()

    fun fromYaml(config: YamlConfiguration, locale: ULocale) {
        val section = config.defaultSection ?: return
        val keys = section.getKeys(true)
        val localeMap = messages.getSafe(locale) { mutableMapOf() }
        for(key in keys) {
            localeMap[key] = section.getString(key) ?: continue
        }
    }

    fun add(locale: ULocale, id: String, message: String) {
        val localeMap = messages.getSafe(locale) { mutableMapOf() }
        localeMap[id] = message
    }

    fun get(message: String, data: GenericDataStore? = null, locale: ULocale = ULocale.US): String? {
        val count = data?.get<Int>("count") ?: data?.get<Double>("count")
        val specifier = if (count != null) {
            ".${PluralRules.forLocale(locale).select(count.toDouble())}"
        } else ""

        val localeMap = messages[locale] ?: return null
        val rawMessage = localeMap["$message$specifier"] ?: return null
        val stringData = data?.stringify()?.asMapOf<String>() ?: return rawMessage

        return stringData.entries.fold(rawMessage) { acc, (key, value) ->
            acc.replace("%$key%", value)
        }
    }
}