package com.github.shatteredsuite.core.math.vector

import com.github.shatteredsuite.core.math.context.RoundingIntContext

class Vector3Int(x: Int, y: Int, z: Int) : Vector3<Int>(RoundingIntContext, x, y, z)