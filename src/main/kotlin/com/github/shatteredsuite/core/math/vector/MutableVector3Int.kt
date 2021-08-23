package com.github.shatteredsuite.core.math.vector

import com.github.shatteredsuite.core.math.context.RoundingIntContext

class MutableVector3Int(x: Int, y: Int, z: Int) : MutableVector3<Int>(RoundingIntContext, x, y, z)