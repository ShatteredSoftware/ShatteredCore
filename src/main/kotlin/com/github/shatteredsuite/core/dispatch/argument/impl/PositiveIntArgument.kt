package com.github.shatteredsuite.core.dispatch.argument.impl

import com.github.shatteredsuite.core.dispatch.argument.impl.primitive.IntArgument

open class PositiveIntArgument(
    name: String,
    usageId: String,
    max: Int = Int.MAX_VALUE,
    default: Int = 0,
    completeMin: Int = 0,
    completeMax: Int = 10,
    completeIncrement: Int = 1
) : IntArgument(name, usageId, 0, max, default, completeMin, completeMax, completeIncrement)