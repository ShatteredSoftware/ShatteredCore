package com.github.shatteredsuite.core.data.location

import com.github.shatteredsuite.core.math.vector.Vector3Like

data class LocationKey<T>(override val x: Int, override val y: Int, override val z: Int, val data: T) : Vector3Like