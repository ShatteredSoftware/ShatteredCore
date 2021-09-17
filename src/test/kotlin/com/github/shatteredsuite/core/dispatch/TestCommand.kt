package com.github.shatteredsuite.core.dispatch

import com.github.shatteredsuite.core.data.generic.get
import com.github.shatteredsuite.core.dispatch.action.DispatchAction
import com.github.shatteredsuite.core.dispatch.argument.impl.primitive.IntegralArgument
import com.github.shatteredsuite.core.dispatch.command.DispatchCommand
import com.github.shatteredsuite.core.dispatch.predicate.DispatchPredicate
import com.github.shatteredsuite.core.dispatch.predicate.PredicateResult
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class TestCommand {
    @Test
    fun `should parse arguments in their correct order`() {
        count = -1
        val command = DispatchCommand("arg_command_1", countAction, listOf(alwaysPassPredicate), listOf(IntegralArgument("count", "count", 0, 10)))
        command.run(MockCommandContext(), listOf("5"))
        assertThat(count, equalTo(5))
    }

    @Test
    fun `should handle failing arguments`() {
        count = -1
        val command = DispatchCommand("arg_command_2", action, listOf(alwaysPassPredicate), listOf(IntegralArgument("count", "count", 0, 10)))
        command.run(MockCommandContext(), listOf("a"))
        assertThat(count, equalTo(-1))
    }

    @Test
    fun `should complete arguments correctly`() {
        val command = DispatchCommand("arg_command_3", action, listOf(alwaysPassPredicate), listOf(IntegralArgument("count", "count", 0, 10), IntegralArgument("times", "times", -5, 5, 0, -5, 5, 1)))
        val completions = command.complete(MockCommandContext(), listOf())
        assertThat(completions.size, equalTo(11))
        assertThat(completions, hasItems("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"))

        val nextCompletions = command.complete(MockCommandContext(), listOf("3"))
        assertThat(nextCompletions.size, equalTo(11))
        assertThat(nextCompletions, hasItems("-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4", "5"))

        val finalCompletions = command.complete(MockCommandContext(), listOf("3", "-1"))
        assertThat(finalCompletions.size, equalTo(0))
    }

    @Test
    fun `should handle predicates`() {
        ran = false
        val command = DispatchCommand.build<MockCommandContext>("predicate_command_1") {
            check(alwaysPassPredicate)
            run(this@TestCommand.action)
        }
        command.run(MockCommandContext(), listOf())
        assertThat(ran, equalTo(true))
    }

    @Test
    fun `should handle failed predicates`() {
        ran = false
        val command = DispatchCommand.build<MockCommandContext>("predicate_command_2") {
            check(alwaysFailPredicate)
            run(this@TestCommand.action)
        }
        command.run(MockCommandContext(), listOf())
        assertThat(ran, equalTo(false))
    }

    private val alwaysFailPredicate: DispatchPredicate<MockCommandContext> = object : DispatchPredicate<MockCommandContext> {
        override val failureMessageId: String = "always-fail"

        override fun check(state: MockCommandContext, debug: Boolean): PredicateResult {
            return PredicateResult(false)
        }
    }

    private val alwaysPassPredicate: DispatchPredicate<MockCommandContext> = object : DispatchPredicate<MockCommandContext> {
        override val failureMessageId: String = "always-pass"

        override fun check(state: MockCommandContext, debug: Boolean): PredicateResult {
            return PredicateResult(true)
        }
    }

    private var ran: Boolean = false

    private val action: DispatchAction<MockCommandContext> = object: DispatchAction<MockCommandContext> {
        override val id: String = "test-action"

        override fun run(state: MockCommandContext, debug: Boolean) {
            ran = true
        }
    }

    private var count: Int = -1

    private val countAction: DispatchAction<MockCommandContext> = object : DispatchAction<MockCommandContext> {
        override val id: String = "test-count-action"

        override fun run(state: MockCommandContext, debug: Boolean) {
            count = state.data.get("count") ?: return
        }
    }

}