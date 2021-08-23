package com.github.shatteredsuite.core.data.adapter

import com.google.gson.*
import org.bukkit.Bukkit
import org.bukkit.Location
import java.lang.reflect.Type

class LocationAdapter : JsonSerializer<Location>, JsonDeserializer<Location> {
    override fun serialize(location: Location?, p1: Type?, p2: JsonSerializationContext?): JsonElement {
        val element = JsonObject()
        if (location == null) {
            return element
        }
        element.addProperty("x", location.x)
        element.addProperty("y", location.y)
        element.addProperty("z", location.z)
        element.addProperty("pitch", location.pitch)
        element.addProperty("yaw", location.yaw)
        element.addProperty("world", location.world?.name)
        return element
    }

    override fun deserialize(element: JsonElement, type: Type?, p2: JsonDeserializationContext?): Location {
        val obj = element.asJsonObject
        val x = obj.get("x").asDouble
        val y = obj.get("y").asDouble
        val z = obj.get("z").asDouble
        val pitch = obj.get("pitch").asFloat
        val yaw = obj.get("yaw").asFloat
        val worldName = obj.get("world")
        val world = if (worldName == null) {
            null
        } else Bukkit.getWorld(worldName.asString)
        return Location(world, x, y, z, yaw, pitch)
    }
}