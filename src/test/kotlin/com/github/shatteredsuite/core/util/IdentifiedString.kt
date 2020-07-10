package com.github.shatteredsuite.core.util

internal class IdentifiedString(override val id: String, val value: String) :
    Identified {
    override fun equals(other: Any?): Boolean {
        if(other !is IdentifiedString) {
            return false
        }
        return id == other.id && value == other.value
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

    override fun toString(): String {
        return "IdentifiedString(id='$id', value='$value')"
    }
}