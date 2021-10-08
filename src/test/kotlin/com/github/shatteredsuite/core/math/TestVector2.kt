package com.github.shatteredsuite.core.math

import com.github.shatteredsuite.core.math.context.DoubleContext
import com.github.shatteredsuite.core.math.vector.Vector2
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.closeTo
import org.junit.jupiter.api.Test

class TestVector2 {
    private val error = 1.0E-6

    @Test
    fun `should calculate square magnitude properly`() {
        val v = Vector2(DoubleContext, 0.0, 1.0)
        assertThat(v.squareMagnitude(), closeTo(1.0, error))
    }

    @Test
    fun `should add properly`() {
        val v1 = Vector2(DoubleContext, 0.0, 1.0)
        val v2 = Vector2(DoubleContext, 1.0, 0.0)

        val actual = v1 + v2
        val expected = Vector2(DoubleContext, 1.0, 1.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should multiply with scalar properly`() {
        val v1 = Vector2(DoubleContext, 0.0, 1.0)
        val s = 5.0

        val actual = v1 * s
        val expected = Vector2(DoubleContext, 0.0, 5.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should multiply with vector properly`() {
        val v1 = Vector2(DoubleContext, 0.0, 1.0)
        val v2 = Vector2(DoubleContext, 1.0, 0.0)

        val actual = v1 * v2
        val expected = Vector2(DoubleContext, 0.0, 0.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should negate properly`() {
        val v = Vector2(DoubleContext, 1.0, 1.0)

        val actual = -v
        val expected = Vector2(DoubleContext, -1.0, -1.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should inverse properly`() {
        val v = Vector2(DoubleContext, 10.0, 10.0)

        val actual = v.inverse()
        val expected = Vector2(DoubleContext, 0.1, 0.1)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should subtract properly`() {
        val v1 = Vector2(DoubleContext, 0.0, 1.0)
        val v2 = Vector2(DoubleContext, 1.0, 2.0)

        val actual = v1 - v2
        val expected = Vector2(DoubleContext, -1.0, -1.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should compute dot product properly`() {
        val v1 = Vector2(DoubleContext, 1.0, 5.0)
        val v2 = Vector2(DoubleContext, 2.0, 3.0)

        val actual = v1 dot v2
        val expected = 17.0

        assertThat(actual, closeTo(expected, error))
    }

    @Test
    fun `should compute distance properly`() {
        val v1 = Vector2(DoubleContext, 1.0, 2.0)
        val v2 = Vector2(DoubleContext, 4.0, 6.0)

        val actual = v1 distanceTo  v2
        val expected = 5.0

        assertThat(actual, closeTo(expected, error))
    }

    @Test
    fun `should calculate magnitude properly`() {
        val v  = Vector2(DoubleContext, 0.0, 4.0)
        assertThat(v.magnitude(), closeTo(4.0, error))
    }

    @Test
    fun `should calculate unit vector properly`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.unit()
        val expected = Vector2(DoubleContext, 0.6, 0.8)

        assertVectorCloseTo(actual, expected)
    }

    private fun assertVectorCloseTo(actual: Vector2<Double>, expected: Vector2<Double>) {
        assertThat(actual.x, closeTo(expected.x, error))
        assertThat(actual.y, closeTo(expected.y, error))
    }
}