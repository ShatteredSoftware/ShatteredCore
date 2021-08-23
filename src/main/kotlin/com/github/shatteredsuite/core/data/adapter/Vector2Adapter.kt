package com.github.shatteredsuite.core.data.adapter

import com.github.shatteredsuite.core.math.vector.Vector2
import com.github.shatteredsuite.core.math.vector.Vector2Double
import com.google.gson.*
import java.lang.reflect.Type

class Vector2Adapter : JsonDeserializer<Vector2<*>>, JsonSerializer<Vector2<*>> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Vector2<*> {
        require(!(json == null || typeOfT == null || context == null)) { "Trying to deserialize null" }
        val obj = json.asJsonObject
        val x = obj.get("x").asDouble
        val y = obj.get("y").asDouble
        return Vector2Double(x, y)
    }

    override fun serialize(src: Vector2<*>?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        require(!(src == null || typeOfSrc == null || context == null)) { "Cannot serialize null" }
        val vec = src.toDouble()
        return context.serialize(vec)
    }
}
