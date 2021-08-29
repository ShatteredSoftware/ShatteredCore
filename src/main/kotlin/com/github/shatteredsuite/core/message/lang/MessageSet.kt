package com.github.shatteredsuite.core.message.lang

import com.github.shatteredsuite.core.data.generic.DataStore
import com.github.shatteredsuite.core.data.generic.GenericDataStore
import com.github.shatteredsuite.core.data.generic.get
import com.github.shatteredsuite.core.data.plugin.PluginTypeKey
import com.github.shatteredsuite.core.extension.getSafe
import com.github.shatteredsuite.core.message.lang.meta.MessageMetaParser
import com.github.shatteredsuite.core.message.lang.meta.RecurseMetaParser
import com.github.shatteredsuite.core.plugin.CoreServer
import com.github.shatteredsuite.core.plugin.ShatteredCore
import org.bukkit.configuration.file.YamlConfiguration
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class MessageSet {
    companion object {
        val metaRegex: Pattern = Pattern.compile("%(.*?):(.*?)%")
    }

    private val messages: MutableMap<Locale, MutableMap<String, String>> = mutableMapOf()
    private val metaParsers: MutableMap<String, MessageMetaParser> = mutableMapOf(
        "message" to RecurseMetaParser(this)
    )

    fun fromYaml(config: YamlConfiguration, locale: Locale) {
        val section = config.defaultSection ?: return
        val keys = section.getKeys(true)
        val localeMap = messages.getSafe(locale) { mutableMapOf() }
        for (key in keys) {
            localeMap[key] = section.getString(key) ?: continue
        }
    }

    fun add(locale: Locale, id: String, message: String) {
        val localeMap = messages.getSafe(locale) { mutableMapOf() }
        localeMap[id] = message
    }

    fun addMetaParser(parser: MessageMetaParser) {
        this.metaParsers[parser.id] = parser
    }

    fun get(message: String, data: DataStore? = null, locale: Locale = Locale.US): String? {

        val count = data?.get<Number>("count")
        val ordinal = data?.get("ordinal") ?: false
        val countSpecifier = if (count != null) {
            if (ordinal) {
                ".${PluralRules.forLocale(locale).selectOrdinal(count.toDouble())}"
            } else ".${PluralRules.forLocale(locale).select(count.toDouble())}"
        } else ""

        val rangeMax = data?.get<Number>("range_min")
        val rangeMin = data?.get<Number>("range_max")
        val specifier = if (rangeMax != null && rangeMin != null) {
            ".${PluralRules.forLocale(locale).selectRange(rangeMin.toDouble(), rangeMax.toDouble())}"
        } else countSpecifier

        val localeMap = messages[locale] ?: return null
        var rawMessage = localeMap["$message$specifier"] ?: return null
        val stringData = DataStore.stringify(data ?: return rawMessage)

        var matcher = metaRegex.matcher(rawMessage)
        while (matcher.find()) {
            val meta = matcher.group(1)
            val result = matcher.group(2)
            val parser = metaParsers[meta]
            val replace = parser?.parse(rawMessage, result, data, locale) ?: ""
            rawMessage = matcher.replaceFirst(replace)
            matcher = metaRegex.matcher(rawMessage)
        }

        return stringData.entries.fold(rawMessage) { acc, (key, value) ->
            acc.replace("%$key%", value)
        }
    }

    fun get(message: String, data: Map<String, String> = mapOf(), locale: Locale = Locale.US): String? {
        val store = GenericDataStore()
        data.forEach { (key, value) ->
            store.put(key, value)
        }
        return get(message, store, locale)
    }

    fun get(
        message: String,
        data: GenericDataStore? = null,
        locale: Locale = Locale.US,
        vararg extra: Pair<String, String>
    ): String? {
        val store = data ?: GenericDataStore()
        extra.forEach { (key, value) ->
            store.put(key, value)
        }
        return get(message, store, locale)
    }
}