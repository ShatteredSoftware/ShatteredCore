package com.github.shatteredsuite.core.data.adapter

import com.google.gson.*
import java.lang.reflect.Type

interface NullSafeAdapter<T> : JsonDeserializer<T>, JsonSerializer<T> {
    fun deserializeSafe(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): T
    fun serializeSafe(src: T, typeOfSrc: Type, context: JsonSerializationContext): JsonElement

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): T {
        require(!(json == null || typeOfT == null || context == null)) { "Trying to deserialize null" }
        return deserializeSafe(json, typeOfT, context)
    }

    override fun serialize(src: T, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        require(!(src == null || typeOfSrc == null || context == null)) { "Cannot serialize null" }
        return serializeSafe(src, typeOfSrc, context)
    }
}