package com.github.shatteredsuite.core.dispatch.argument

import org.bukkit.Material

class MaterialArgument(name: String) : ChoiceArgument<Material>(
    name, Material::getMaterial, { Material.values().map { it.name.lowercase() } }, Material.AIR, false
)