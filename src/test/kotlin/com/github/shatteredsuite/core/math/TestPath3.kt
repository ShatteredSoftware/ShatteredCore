package com.github.shatteredsuite.core.math

import com.github.shatteredsuite.core.math.context.DoubleContext
import com.github.shatteredsuite.core.math.path.Path3
import com.github.shatteredsuite.core.math.vector.Vector3Double
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.closeTo
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

class TestPath3 {
    val error = 1.0E-6

    @Test
    fun `toString shouldn't break anything`() {
        val emptyPath = Path3(mutableListOf(), DoubleContext)
        assertThat(emptyPath.toString(), equalTo("Path3(points=[])"))
    }

    @Test
    fun `should calculate distance correctly`() {
        val emptyPath = Path3(mutableListOf(), DoubleContext)
        assertThat("paths with no items yields zero", emptyPath.distance, closeTo(0.0, error))

        val singletonPath = Path3(mutableListOf(Vector3Double(0.0, 0.0, 0.0)), DoubleContext)
        assertThat("paths with no items yields zero", singletonPath.distance, closeTo(0.0, error))

        val path = Path3(mutableListOf(Vector3Double(0.0, 0.0, 0.0), Vector3Double(1.0, 0.0, 0.0)), DoubleContext)
        assertThat("paths with two items yields the distance between points", path.distance, closeTo(1.0, error))
        val path2 = Path3(mutableListOf(Vector3Double(0.0, 0.0, 0.0), Vector3Double(1.0, 1.0, 0.0)), DoubleContext)
        assertThat(
            "paths with two items yields the distance between points",
            path2.distance,
            closeTo(sqrt(2.0), error)
        )

        val path3 = Path3(
            mutableListOf(
                Vector3Double(0.0, 0.0, 0.0),
                Vector3Double(1.0, 0.0, 0.0),
                Vector3Double(1.0, 1.0, 0.0)
            ), DoubleContext
        )
        assertThat(
            "paths with more than two items are the distance between each pair of points",
            path3.distance,
            closeTo(2.0, error)
        )
    }

    @Test
    fun `should optimize paths correctly`() {

        val delta = 1.0E-4
        val optimizeSize = 1.0E-3

        val path = Path3(
            mutableListOf(
                Vector3Double(0.0, 0.0, 0.0),
                Vector3Double(1.0, 0.0, 0.0),
                Vector3Double(1.0 + delta, 0.0, 0.0),
                Vector3Double(1.0 + delta, delta, 0.0),
                Vector3Double(1.0, 1.0, 0.0),
            ), DoubleContext
        )
        path.optimize(optimizeSize)
        assertThat("two points should have been removed", path.points.size, equalTo(3))
        assertThat("the path distance is close to the same", path.distance, closeTo(2.0, error))
        assertThat("the proper points were removed", path.points[0], equalTo(Vector3Double(0.0, 0.0, 0.0)))
        assertThat("the proper points were removed", path.points[1], equalTo(Vector3Double(1.0, 0.0, 0.0)))
        assertThat("the proper points were removed", path.points[2], equalTo(Vector3Double(1.0, 1.0, 0.0)))
    }


    @Test
    fun `should find closest point properly`() {

        val path = Path3(
            mutableListOf(
                Vector3Double(0.0, 0.0, 0.0),
                Vector3Double(2.0, 0.0, 0.0),
                Vector3Double(2.0, 4.0, 0.0),
                Vector3Double(2.0, 4.0, 6.0),
                Vector3Double(2.0, 8.0, 6.0),
            ), DoubleContext
        )

        val point1 = Vector3Double(0.0, -1.0, 0.0)
        assertThat("should find the closest point", path.pointNearestTo(point1), equalTo(Vector3Double(0.0, 0.0, 0.0)))
        val point2 = Vector3Double(0.0, 8.0, 0.0)
        assertThat("should find the closest point", path.pointNearestTo(point2), equalTo(Vector3Double(2.0, 4.0, 0.0)))
        val point3 = Vector3Double(1.0, 4.0, 0.0)
        assertThat("should find the closest point", path.pointNearestTo(point3), equalTo(Vector3Double(2.0, 4.0, 0.0)))
    }
}