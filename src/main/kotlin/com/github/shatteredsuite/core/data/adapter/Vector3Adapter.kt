package com.github.shatteredsuite.core.data.adapter

import com.github.shatteredsuite.core.math.vector.Vector3
import com.github.shatteredsuite.core.math.vector.Vector3Double
import com.google.gson.*
import java.lang.reflect.Type

class Vector3Adapter : JsonDeserializer<Vector3<*>>, JsonSerializer<Vector3<*>> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Vector3<*> {
        require(!(json == null || typeOfT == null || context == null)) { "Trying to deserialize null" }
        val obj = json.asJsonObject
        val x = obj.get("x").asDouble
        val y = obj.get("y").asDouble
        val z = obj.get("z").asDouble
        return Vector3Double(x, y, z)
    }

    override fun serialize(src: Vector3<*>?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (src == null || typeOfSrc == null || context == null) {
            throw IllegalArgumentException("Cannot serialize null")
        }
        val vec = src.toDouble()
        return context.serialize(vec)
    }
}