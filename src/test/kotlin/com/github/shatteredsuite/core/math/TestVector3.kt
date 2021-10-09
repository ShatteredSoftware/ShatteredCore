package com.github.shatteredsuite.core.math

import com.github.shatteredsuite.core.math.context.DoubleContext
import com.github.shatteredsuite.core.math.vector.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.closeTo
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class TestVector3 {
    private val error = 1.0E-6

    @Test
    fun `should calculate square magnitude properly`() {
        val v = Vector3(DoubleContext, 0.0, 1.0, 0.0)
        assertThat(v.squareMagnitude(), closeTo(1.0, error))
    }

    @Test
    fun `should add properly`() {
        val v1 = Vector3(DoubleContext, 0.0, 1.0, 0.0)
        val v2 = Vector3(DoubleContext, 1.0, 0.0, 1.0)

        val actual = v1 + v2
        val expected = Vector3(DoubleContext, 1.0, 1.0, 1.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should subtract properly`() {
        val v1 = Vector3(DoubleContext, 5.0, 1.0, 1.0)
        val v2 = Vector3(DoubleContext, 1.0, 0.0, 1.0)

        val actual = v1 - v2
        val expected = Vector3(DoubleContext, 4.0, 1.0, 0.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should multiply with scalar properly`() {
        val v1 = Vector3(DoubleContext, 5.0, 1.0, 1.0)
        val s = 3

        val actual = v1 * s
        val expected = Vector3(DoubleContext, 15.0, 3.0, 3.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should multiply with vector properly`() {
        val v1 = Vector3(DoubleContext, 5.0, 1.0, 1.0)
        val v2 = Vector3(DoubleContext, 1.0, 0.0, 1.0)

        val actual = v1 * v2
        val expected = Vector3(DoubleContext, 5.0, 0.0, 1.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should compute dot product properly`() {
        val v1 = Vector3(DoubleContext, 5.0, 1.0, 1.0)
        val v2 = Vector3(DoubleContext, 1.0, 0.0, 1.0)

        val actual = v1 dot v2
        val expected = 6.0

        assertThat(actual, closeTo(expected, error))
    }

    @Test
    fun `should compute distance properly`() {
        val v1 = Vector3(DoubleContext, 4.0, 3.0, 12.0)
        val v2 = Vector3(DoubleContext, 1.0, -1.0, 0.0)

        val actual = v1 distanceTo v2
        val expected = 13.0

        assertThat(actual, closeTo(expected, error))
    }

    @Test
    fun `should calculate magnitude properly`() {
        val v = Vector3(DoubleContext, 0.0, 4.0, 3.0)
        assertThat(v.magnitude(), closeTo(5.0, error))
    }

    @Test
    fun `should calculate unit vector properly`() {
        val v = Vector3(DoubleContext, 3.0, 0.0, 4.0)

        val actual = v.unit()
        val expected = Vector3(DoubleContext, 0.6, 0.0, 0.8)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should clone properly`() {
        val v = Vector3(DoubleContext, 3.0, 4.0, 5.0)

        val actual = v.clone()
        val expected = Vector3(DoubleContext, 3.0, 4.0, 5.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should return mutable version with correct props`() {
        val v = Vector3(DoubleContext, 3.0, 4.0, 5.0)

        val actual = v.asMutable()
        val expected = MutableVector3(DoubleContext, 3.0, 4.0, 5.0)

        assertThat(actual.x, closeTo(expected.x, error))
        assertThat(actual.y, closeTo(expected.y, error))
        assertThat(actual.z, closeTo(expected.z, error))
    }

    @Test
    fun `should destructure properly`() {
        val v = Vector3(DoubleContext, 3.0, 4.0, 5.0)

        val (x, y, z) = v

        assertThat(x, closeTo(v.x, error))
        assertThat(y, closeTo(v.y, error))
        assertThat(z, closeTo(v.z, error))
    }

    @Test
    fun `should negate properly`() {
        val v = Vector3(DoubleContext, 3.0, 4.0, 5.0)

        val actual = v.negate()
        val expected = Vector3(DoubleContext, -3.0, -4.0, -5.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should cast to float properly`() {
        val v = Vector3(DoubleContext, 1.6, 3.4, 4.2)

        val actual = v.toFloat()
        val expected = Vector3Float(1.6F, 3.4F, 4.2F)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should cast to int properly`() {
        val v = Vector3(DoubleContext, 1.6, 3.4, 4.2)

        val actual = v.toInt()
        val expected = Vector3Int(1, 3, 4)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should cast to double properly`() {
        val v = Vector3(DoubleContext, 1.6, 3.4, 4.2)

        val actual = v.toDouble()
        val expected = Vector3Double(1.6, 3.4, 4.2)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should cast to long properly`() {
        val v = Vector3(DoubleContext, 1.6, 3.4, 4.2)

        val actual = v.toLong()
        val expected = Vector3Long(1L, 3L, 4L)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should round to int properly`() {
        val v = Vector3(DoubleContext, 1.6, 3.4, 4.2)

        val actual = v.roundToInt()
        val expected = Vector3Int(2, 3, 4)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should round to long properly`() {
        val v = Vector3(DoubleContext, 1.6, 3.4, 4.2)

        val actual = v.roundToLong()
        val expected = Vector3Long(2L, 3L, 4L)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should rotateXZ properly`() {
        val v = Vector3(DoubleContext, 3.0, 1.0, 4.0)

        val actual = v.rotateXZ(90)
        val expected = Vector3(DoubleContext, -4.0, 1.0, 3.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `toString should be correct`() {
        val v = Vector3(DoubleContext, 3.0, 4.0, 5.0)

        val actual = v.toString()
        val expected = "Vector3(x=3.0, y=4.0, z=5.0)"

        assertThat(actual, equalTo(expected))
    }

    private fun assertVectorCloseTo(actual: Vector3<Double>, expected: Vector3<Double>) {
        assertThat(actual.x, closeTo(expected.x, error))
        assertThat(actual.y, closeTo(expected.y, error))
        assertThat(actual.z, closeTo(expected.z, error))
    }
}