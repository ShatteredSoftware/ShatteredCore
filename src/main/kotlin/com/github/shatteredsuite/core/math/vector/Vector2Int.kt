package com.github.shatteredsuite.core.math.vector

import com.github.shatteredsuite.core.math.context.RoundingIntContext

class Vector2Int(x: Int, y: Int) : Vector2<Int>(RoundingIntContext, x, y)