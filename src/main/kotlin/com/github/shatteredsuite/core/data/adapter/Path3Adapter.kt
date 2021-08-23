package com.github.shatteredsuite.core.data.adapter

import com.github.shatteredsuite.core.math.context.DoubleContext
import com.github.shatteredsuite.core.math.path.IdentifiedPath3
import com.github.shatteredsuite.core.math.vector.Vector3
import com.github.shatteredsuite.core.math.vector.Vector3Double
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class Path3Adapter : JsonDeserializer<IdentifiedPath3<*>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): IdentifiedPath3<*> {
        require(!(json == null || typeOfT == null || context == null)) { "Trying to deserialize null" }
        val obj = json.asJsonObject
        val id = obj.get("id").asString
        val arr = obj.get("points").asJsonArray
        val res = mutableListOf<Vector3Double>()
        for (item in arr) {
            res.add(context.deserialize(item, Vector3::class.java))
        }
        return IdentifiedPath3(id, res, DoubleContext)
    }
}