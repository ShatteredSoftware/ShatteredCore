package com.github.shatteredsuite.core.data.item

import com.github.shatteredsuite.core.attribute.Identified

class ItemType(override val id: String, val name: String, val lore: List<String>) : Identified {
}