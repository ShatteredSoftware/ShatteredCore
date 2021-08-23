package com.github.shatteredsuite.core.math.vector

import com.github.shatteredsuite.core.math.context.RoundingLongContext

class Vector2Long(x: Long, y: Long) : Vector2<Long>(RoundingLongContext, x, y)