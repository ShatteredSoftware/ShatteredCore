package com.github.shatteredsuite.core.math.vector

import com.github.shatteredsuite.core.math.context.RoundingLongContext

class Vector3Long(x: Long, y: Long, z: Long) : Vector3<Long>(RoundingLongContext, x, y, z)