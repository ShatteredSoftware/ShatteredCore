package com.github.shatteredsuite.core.data.generic

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class TestEmptyDataStore {
    @Test
    fun `should not be able to get anything`() {
        val store = EmptyDataStore
        assertThat(store.keys.size, equalTo(0))
        assertThat(store.get<Any>("anything"), equalTo(null))
        assertThat(store.get("anything", Int::class.java), equalTo(null))
        assertThat(store.getOrDef("anything", 5), equalTo(5))
        assertThat(store.getUnsafe("anything"), equalTo(null))
    }
}