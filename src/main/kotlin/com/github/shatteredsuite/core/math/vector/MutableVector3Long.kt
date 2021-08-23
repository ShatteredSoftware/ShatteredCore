package com.github.shatteredsuite.core.math.vector

import com.github.shatteredsuite.core.math.context.RoundingLongContext

class MutableVector3Long(x: Long, y: Long, z: Long) : MutableVector3<Long>(RoundingLongContext, x, y, z)