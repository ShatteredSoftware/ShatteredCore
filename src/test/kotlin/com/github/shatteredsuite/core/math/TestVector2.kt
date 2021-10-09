package com.github.shatteredsuite.core.math

import com.github.shatteredsuite.core.math.context.DoubleContext
import com.github.shatteredsuite.core.math.vector.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.closeTo
import org.hamcrest.Matchers.equalTo
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

        val actual = v1 distanceTo v2
        val expected = 5.0

        assertThat(actual, closeTo(expected, error))
    }

    @Test
    fun `should calculate magnitude properly`() {
        val v = Vector2(DoubleContext, 0.0, 4.0)
        assertThat(v.magnitude(), closeTo(4.0, error))
    }

    @Test
    fun `should calculate unit vector properly`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.unit()
        val expected = Vector2(DoubleContext, 0.6, 0.8)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should clone properly`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.clone()
        val expected = Vector2(DoubleContext, 3.0, 4.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `should return mutable version with correct props`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.asMutable()
        val expected = MutableVector2(DoubleContext, 3.0, 4.0)

        assertThat(actual.x, closeTo(expected.x, error))
        assertThat(actual.y, closeTo(expected.y, error))
    }

    @Test
    fun `should destructure properly`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val (x, y) = v

        assertThat(x, closeTo(v.x, error))
        assertThat(y, closeTo(v.y, error))
    }

    @Test
    fun `should cast to float properly`() {
        val v = Vector2(DoubleContext, 1.6, 3.4)

        val actual = v.toFloat()
        val expected = Vector2Float(1.6F, 3.4F)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should cast to int properly`() {
        val v = Vector2(DoubleContext, 1.6, 3.4)

        val actual = v.toInt()
        val expected = Vector2Int(1, 3)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should cast to double properly`() {
        val v = Vector2(DoubleContext, 1.6, 3.4)

        val actual = v.toDouble()
        val expected = Vector2Double(1.6, 3.4)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should cast to long properly`() {
        val v = Vector2(DoubleContext, 1.6, 3.4)

        val actual = v.toLong()
        val expected = Vector2Long(1L, 3L)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should round to int properly`() {
        val v = Vector2(DoubleContext, 1.6, 3.4)

        val actual = v.roundToInt()
        val expected = Vector2Int(2, 3)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should round to long properly`() {
        val v = Vector2(DoubleContext, 1.6, 3.4)

        val actual = v.roundToLong()
        val expected = Vector2Long(2L, 3L)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should up to Vector3 properly`() {
        val v = Vector2(DoubleContext, 1.6, 3.4)

        val actual = v.up(5.0)
        val expected = Vector3(DoubleContext, 1.6, 3.4, 5.0)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `should rotate properly`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.rotate(90)
        val expected = Vector2(DoubleContext, -4.0, 3.0)

        assertVectorCloseTo(actual, expected)
    }

    @Test
    fun `toString should be correct`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.toString()
        val expected = "Vector2(x=3.0, y=4.0)"

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `test xx Swizzling`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.xx
        val expected = Vector2(DoubleContext, 3.0, 3.0)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `test xy Swizzling`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.xy
        val expected = Vector2(DoubleContext, 3.0, 4.0)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `test yx Swizzling`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.yx
        val expected = Vector2(DoubleContext, 4.0, 3.0)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `test yy Swizzling`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.yy
        val expected = Vector2(DoubleContext, 4.0, 4.0)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `test xxx Swizzling`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.xxx
        val expected = Vector3(DoubleContext, 3.0, 3.0, 3.0)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `test xxy Swizzling`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.xxy
        val expected = Vector3(DoubleContext, 3.0, 3.0, 4.0)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `test xyx Swizzling`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.xyx
        val expected = Vector3(DoubleContext, 3.0, 4.0, 3.0)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `test xyy Swizzling`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.xyy
        val expected = Vector3(DoubleContext, 3.0, 4.0, 4.0)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `test yxx Swizzling`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.yxx
        val expected = Vector3(DoubleContext, 4.0, 3.0, 3.0)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `test yxy Swizzling`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.yxy
        val expected = Vector3(DoubleContext, 4.0, 3.0, 4.0)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `test yyx Swizzling`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.yyx
        val expected = Vector3(DoubleContext, 4.0, 4.0, 3.0)

        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `test yyy Swizzling`() {
        val v = Vector2(DoubleContext, 3.0, 4.0)

        val actual = v.yyy
        val expected = Vector3(DoubleContext, 4.0, 4.0, 4.0)

        assertThat(actual, equalTo(expected))
    }

    private fun assertVectorCloseTo(actual: Vector2<Double>, expected: Vector2<Double>) {
        assertThat(actual.x, closeTo(expected.x, error))
        assertThat(actual.y, closeTo(expected.y, error))
    }
}