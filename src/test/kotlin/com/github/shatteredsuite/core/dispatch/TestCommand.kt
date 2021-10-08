package com.github.shatteredsuite.core.dispatch

import com.github.shatteredsuite.core.data.generic.get
import com.github.shatteredsuite.core.dispatch.action.DispatchAction
import com.github.shatteredsuite.core.dispatch.argument.impl.PositiveIntArgument
import com.github.shatteredsuite.core.dispatch.argument.impl.primitive.IntArgument
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
        val command = DispatchCommand("arg_command_1", countAction, listOf(alwaysPassPredicate), listOf(IntArgument("count", "count", 0, 10)))
        command.execute(MockCommandContext(), listOf("5"))
        assertThat(count, equalTo(5))
    }

    @Test
    fun `should handle failing arguments`() {
        count = -1
        val command = DispatchCommand("arg_command_2", action, listOf(alwaysPassPredicate), listOf(IntArgument("count", "count", 0, 10)))
        command.execute(MockCommandContext(), listOf("a"))
        assertThat(count, equalTo(-1))
    }

    @Test
    fun `should complete arguments correctly`() {
        val command = DispatchCommand("arg_command_3", action, listOf(alwaysPassPredicate), listOf(IntArgument("count", "count", 0, 10), IntArgument("times", "times", -5, 5, 0, -5, 5, 1)))
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
    fun `should work with child commands`() {
        var childRun = false
        var failChildRun = false
        var parentRun = false
        var nestedRun = false

        val command = DispatchCommand.build<MockCommandContext>("parent_command_1") {
            check(alwaysPassPredicate)
            exec {
                parentRun = true
            }

            withChild("check") {
                check(alwaysPassPredicate)
                withArg(PositiveIntArgument("count", "count", 10, 0, 0, 10))
                exec {
                    childRun = true
                }

                withChild("nested") {
                    exec {
                        nestedRun = true
                    }
                }
            }

            withChild("fail") {
                check(alwaysFailPredicate)
                exec {
                    failChildRun = true
                }

                withChild("nested") {
                    exec {
                        nestedRun = true
                    }
                }
            }
        }

        assertThat("pre: Parent has not run", parentRun, equalTo(false))
        assertThat("pre: Child has not run", childRun, equalTo(false))
        assertThat("pre: Nested has not run", nestedRun, equalTo(false))
        assertThat("pre: Fail has not run", failChildRun, equalTo(false))

        // Standard child execution
        command.execute(MockCommandContext(), listOf("check", "5"))
        assertThat("Parent should not run when a child command is run", parentRun, equalTo(false))
        assertThat("Child command should run when it is run", childRun, equalTo(true))
        assertThat("Further nested commands should not run when a child is run", nestedRun, equalTo(false))
        assertThat("Other children should not run when a child is run", failChildRun, equalTo(false))
        childRun = false

        // No child to run
        command.execute(MockCommandContext(), listOf())
        assertThat("Parent should run normally when no child is run", parentRun, equalTo(true))
        assertThat("Child should not run when the parent is run", childRun, equalTo(false))
        assertThat("Further nested commands should not run when the parent is run", nestedRun, equalTo(false))
        assertThat("Other children should not run when a child is run", failChildRun, equalTo(false))
        parentRun = false

        // Child with invalid arguments
        command.execute(MockCommandContext(), listOf("check"))
        assertThat("Parent should not run when child is given invalid arguments", parentRun, equalTo(false))
        assertThat("Child should not run when given invalid arguments", childRun, equalTo(false))
        assertThat("Further nested commands should not run when the child fails to run", nestedRun, equalTo(false))
        assertThat("Other children should not run when a child fails to run", failChildRun, equalTo(false))

        // Child predicate failure
        command.execute(MockCommandContext(), listOf("fail"))
        assertThat("Parent should not run when child predicates fail", parentRun, equalTo(false))
        assertThat("Other children should not run when child predicates fail", childRun, equalTo(false))
        assertThat("Further nested commands should not run when child predicates fail", nestedRun, equalTo(false))
        assertThat("Child should not run when its predicates fail", failChildRun, equalTo(false))

        // Child nested predicate failure
        command.execute(MockCommandContext(), listOf("fail", "nested"))
        assertThat("Parent should not run when child predicates fail", parentRun, equalTo(false))
        assertThat("Other children should not run when child predicates fail", childRun, equalTo(false))
        assertThat("Further nested commands should not run when child predicates fail", nestedRun, equalTo(false))
        assertThat("Child should not run when its predicates fail", failChildRun, equalTo(false))

        // Multiple nesting
        command.execute(MockCommandContext(), listOf("check", "5", "nested"))
        assertThat("Parent should not run when child runs", parentRun, equalTo(false))
        assertThat("Child should not run when deeper children run", childRun, equalTo(false))
        assertThat("Nested children should run when called", nestedRun, equalTo(true))
        assertThat("Other children should not run when deeper children are run", failChildRun, equalTo(false))
    }

    @Test
    fun `should handle predicates`() {
        ran = false
        val command = DispatchCommand.build<MockCommandContext>("predicate_command_1") {
            check(alwaysPassPredicate)
            exec(this@TestCommand.action)
        }
        command.execute(MockCommandContext(), listOf())
        assertThat(ran, equalTo(true))
    }

    @Test
    fun `should handle failed predicates`() {
        ran = false
        val command = DispatchCommand.build<MockCommandContext>("predicate_command_2") {
            check(alwaysFailPredicate)
            exec(this@TestCommand.action)
        }
        command.execute(MockCommandContext(), listOf())
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