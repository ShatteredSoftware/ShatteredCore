package com.github.shatteredsuite.core.math.vector

import com.github.shatteredsuite.core.math.context.RoundingIntContext

class MutableVector2Int(x: Int, y: Int) : MutableVector2<Int>(RoundingIntContext, x, y)