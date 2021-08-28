package com.github.shatteredsuite.core.dispatch.argument.impl

open class PositiveIntegralArgument(
    name: String,
    max: Int = Int.MAX_VALUE,
    default: Int = 0,
    completeMin: Int = 0,
    completeMax: Int = 10,
    completeIncrement: Int = 1
) : IntegralArgument(name, 0, default, max, completeMin, completeMax, completeIncrement)