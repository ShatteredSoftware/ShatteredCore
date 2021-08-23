package com.github.shatteredsuite.core.math.vector

import com.github.shatteredsuite.core.math.context.RoundingLongContext

class MutableVector2Long(x: Long, y: Long) : MutableVector2<Long>(RoundingLongContext, x, y)