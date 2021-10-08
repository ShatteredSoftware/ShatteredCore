package com.github.shatteredsuite.core.math

import com.github.shatteredsuite.core.math.context.DoubleContext
import com.github.shatteredsuite.core.math.vector.Vector3
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.closeTo
import org.junit.jupiter.api.Test

class TestVector3 {
    val error = 1.0E-6

    @Test
    fun `should calculate distance properly`() {
        val v1 = Vector3(DoubleContext, 0.0, 0.0, 0.0)
        val v2 = Vector3(DoubleContext, 1.0, 0.0, 0.0)
        assertThat(v1 distanceTo v2, closeTo(1.0, error))
    }
}