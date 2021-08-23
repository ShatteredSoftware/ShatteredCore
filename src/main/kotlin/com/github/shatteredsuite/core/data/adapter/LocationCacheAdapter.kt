package com.github.shatteredsuite.core.data.adapter

import com.github.shatteredsuite.core.data.location.LocationCache
import com.github.shatteredsuite.core.data.location.LocationKey
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class LocationCacheAdapter : NullSafeAdapter<LocationCache<*>> {
    private val locationKeyType: TypeToken<LocationKey<String>> = object : TypeToken<LocationKey<String>>() {}

    override fun deserializeSafe(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): LocationCache<String> {
        val arr = json.asJsonArray
        val cache = LocationCache<String>()
        arr.forEach {
            val key = context.deserialize<LocationKey<String>>(it, locationKeyType.type)
            cache.add(key.x, key.y, key.z, key.data)
        }
        return cache
    }

    override fun serializeSafe(src: LocationCache<*>, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return context.serialize(src.getAllKeys())
    }
}