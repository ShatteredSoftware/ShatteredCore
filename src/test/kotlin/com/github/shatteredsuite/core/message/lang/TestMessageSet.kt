package com.github.shatteredsuite.core.message.lang

import com.github.shatteredsuite.core.data.generic.GenericDataStore
import com.github.shatteredsuite.core.message.lang.impl.ChinesePluralRules
import com.github.shatteredsuite.core.message.lang.impl.EnglishPluralRules
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.util.*

class TestMessageSet {
    companion object {
        val locale = Locale("TEST")
        val otherLocale = Locale("TEST2")

        @JvmStatic
        @BeforeAll
        fun `set up PluralRules`() {
            PluralRules.add(EnglishPluralRules, locale)
            PluralRules.add(ChinesePluralRules, otherLocale)
        }
    }

    @Test
    fun `should handle a basic message`() {
        val set = MessageSet()
        val store = GenericDataStore()

        set.add(locale, "test-simple", "A simple test message with %variable%.")

        val result1 = set.get("test-simple", store, locale)
        assertThat(result1, equalTo("A simple test message with %variable%."))

        store["variable"] = "content"

        val result2 = set.get("test-simple", store, locale)
        assertThat(result2, equalTo("A simple test message with content."))
    }

    @Test
    fun `should handle a message in multiple locales`() {
        val set = MessageSet()
        val store = GenericDataStore()

        set.add(locale, "test-simple", "A simple test message from the first locale.")
        set.add(otherLocale, "test-simple", "A simple test message from the second locale.")

        val result1 = set.get("test-simple", store, locale)
        assertThat(result1, equalTo("A simple test message from the first locale."))
        val result2 = set.get("test-simple", store, otherLocale)
        assertThat(result2, equalTo("A simple test message from the second locale."))
    }

    @Test
    fun `should handle a counted message in multiple locales`() {
        val set = MessageSet()
        val store = GenericDataStore()

        store["count"] = 1

        set.add(locale, "test-simple.one", "There is %count% item.")
        set.add(otherLocale, "test-simple.one", "There was %count% item.")
        set.add(locale, "test-simple.other", "There are %count% items.")
        set.add(otherLocale, "test-simple.other", "There were %count% items.")

        val result1 = set.get("test-simple", store, locale)
        assertThat(result1, equalTo("There is 1 item."))
        val result2 = set.get("test-simple", store, otherLocale)
        assertThat(result2, equalTo("There were 1 items."))

        store["count"] = 2

        val result3 = set.get("test-simple", store, locale)
        assertThat(result3, equalTo("There are 2 items."))
        val result4 = set.get("test-simple", store, otherLocale)
        assertThat(result4, equalTo("There were 2 items."))
    }

    @Test
    fun `should handle an ordinal message in multiple locales`() {
        val set = MessageSet()
        val store = GenericDataStore()

        store["ordinal"] = true
        store["count"] = 1

        set.add(locale, "test-simple.one", "This is the %count%st item.")
        set.add(locale, "test-simple.two", "This is the %count%nd item.")
        set.add(locale, "test-simple.few", "This is the %count%rd item.")
        set.add(locale, "test-simple.other", "This is the %count%th item.")
        set.add(otherLocale, "test-simple.other", "This is item no. %count%.")

        val result1 = set.get("test-simple", store, locale)
        assertThat(result1, equalTo("This is the 1st item."))
        val result2 = set.get("test-simple", store, otherLocale)
        assertThat(result2, equalTo("This is item no. 1."))

        store["count"] = 2

        val result3 = set.get("test-simple", store, locale)
        assertThat(result3, equalTo("This is the 2nd item."))
        val result4 = set.get("test-simple", store, otherLocale)
        assertThat(result4, equalTo("This is item no. 2."))

        store["count"] = 3

        val result5 = set.get("test-simple", store, locale)
        assertThat(result5, equalTo("This is the 3rd item."))
        val result6 = set.get("test-simple", store, otherLocale)
        assertThat(result6, equalTo("This is item no. 3."))

        store["count"] = 4

        val result7 = set.get("test-simple", store, locale)
        assertThat(result7, equalTo("This is the 4th item."))
        val result8 = set.get("test-simple", store, otherLocale)
        assertThat(result8, equalTo("This is item no. 4."))

        store["count"] = 5

        val result9 = set.get("test-simple", store, locale)
        assertThat(result9, equalTo("This is the 5th item."))
        val result10 = set.get("test-simple", store, otherLocale)
        assertThat(result10, equalTo("This is item no. 5."))
    }

    @Test
    fun `should handle a ranged message`() {
        val set = MessageSet()
        val store = GenericDataStore()

        set.add(locale, "test-simple", "A simple test message with %variable%.")

        val result1 = set.get("test-simple", store, locale)
        assertThat(result1, equalTo("A simple test message with %variable%."))

        store["variable"] = "content"

        val result2 = set.get("test-simple", store, locale)
        assertThat(result2, equalTo("A simple test message with content."))
    }

    @Test
    fun `should handle a recursive message`() {
        val set = MessageSet()
        val store = GenericDataStore()

        set.add(locale, "test-simple", "%message:prefix% A simple test message.")
        set.add(locale, "prefix", "[Prefix]")

        val result1 = set.get("test-simple", store, locale)
        assertThat(result1, equalTo("[Prefix] A simple test message."))
    }

    @Test
    fun `should handle a multi-recursive message`() {
        val set = MessageSet()
        val store = GenericDataStore()

        set.add(locale, "test-simple", "%message:prefix% A simple test message %message:content%!")
        set.add(locale, "prefix", "[Prefix]")
        set.add(locale, "content", "with content")

        val result1 = set.get("test-simple", store, locale)
        assertThat(result1, equalTo("[Prefix] A simple test message with content!"))
    }
}