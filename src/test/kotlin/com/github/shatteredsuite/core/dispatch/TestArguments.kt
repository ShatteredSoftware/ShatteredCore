package com.github.shatteredsuite.core.dispatch

import com.github.shatteredsuite.core.dispatch.argument.impl.primitive.IntArgument
import com.github.shatteredsuite.core.data.generic.get
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test

class TestArguments {
    val quarterRange = 1_000_000

    @Test
    fun `test integral argument`() {
        val argument = IntArgument("test_argument", "test_argument", -quarterRange, quarterRange, 0, -quarterRange, quarterRange, 1)

        // Low test
        for (i in -2 * quarterRange until -quarterRange) {
            val result = argument.validate(listOf(i.toString()), 0, MockCommandContext())
            val data = result.data

            assertThat("$i outside of range [${-quarterRange}, $quarterRange] should not work", result.success, equalTo(false))
            assertThat(result.result, nullValue())
            assertThat(data.get<String>("offender"), equalTo("$i"))
        }

        // In range test
        for (i in -quarterRange..quarterRange) {
            val result = argument.validate(listOf(i.toString()), 0, MockCommandContext())

            assertThat("$i within range [${-quarterRange}, $quarterRange] should work", result.success, equalTo(true))
            assertThat(result.result, equalTo(i))
        }

        // High test
        for (i in quarterRange + 1 until quarterRange * 2) {
            val result = argument.validate(listOf(i.toString()), 0, MockCommandContext())
            val data = result.data

            assertThat("$i outside of range [${-quarterRange}, $quarterRange] should not work", result.success, equalTo(false))
            assertThat(result.result, nullValue())
            assertThat(data.get<String>("offender"), equalTo("$i"))
        }

        // Completion

    }
}