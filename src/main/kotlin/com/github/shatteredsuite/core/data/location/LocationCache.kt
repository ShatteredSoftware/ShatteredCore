package com.github.shatteredsuite.core.data.location

import com.github.shatteredsuite.core.extension.getSafe
import com.github.shatteredsuite.core.math.vector.Vector3Like

class LocationCache<T> {
    val map: MutableMap<Int, MutableMap<Int, MutableMap<Int, MutableSet<T>>>> = mutableMapOf()

    fun add(x: Int, y: Int, z: Int, data: T) {
        val xMap = map.getSafe(x) { mutableMapOf() }
        val yMap = xMap.getSafe(y) { mutableMapOf() }
        val zMap = yMap.getSafe(z) { mutableSetOf() }
        zMap.add(data)
    }

    fun get(x: Int, y: Int, z: Int): Set<T> {
        return map.getSafe(x) {
            mutableMapOf()
        }.getSafe(y) {
            mutableMapOf()
        }.getSafe(z) {
            mutableSetOf()
        }
    }

    fun add(v: Vector3Like, data: T) = add(v.x.toInt(), v.y.toInt(), v.z.toInt(), data)

    fun get(v: Vector3Like): Set<T> = get(v.x.toInt(), v.y.toInt(), v.z.toInt())

    fun getAllKeys(): Iterable<LocationKey<T>> {
        val dest = mutableListOf<LocationKey<T>>()
        map.entries.forEach { (x, xSet) ->
            xSet.entries.forEach { (y, ySet) ->
                ySet.entries.forEach { (z, zSet) ->
                    zSet.forEach {
                        dest += LocationKey(x, y, z, it)
                    }
                }
            }
        }
        return dest
    }
}