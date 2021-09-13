package com.github.shatteredsuite.core.dispatch.argument.impl

import com.github.shatteredsuite.core.dispatch.argument.impl.primitive.IntegralArgument

open class PositiveIntegralArgument(
    name: String,
    usageId: String,
    max: Int = Int.MAX_VALUE,
    default: Int = 0,
    completeMin: Int = 0,
    completeMax: Int = 10,
    completeIncrement: Int = 1
) : IntegralArgument(name, usageId, 0, default, max, completeMin, completeMax, completeIncrement)