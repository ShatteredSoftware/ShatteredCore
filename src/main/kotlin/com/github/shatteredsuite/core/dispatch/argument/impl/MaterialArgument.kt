package com.github.shatteredsuite.core.dispatch.argument.impl

import com.github.shatteredsuite.core.dispatch.argument.impl.primitive.ChoiceArgument
import org.bukkit.Material

class MaterialArgument(name: String) : ChoiceArgument<Material>(
    name, Material::getMaterial, { Material.values().map { it.name.lowercase() } }, "usage.material", Material.AIR, false
)