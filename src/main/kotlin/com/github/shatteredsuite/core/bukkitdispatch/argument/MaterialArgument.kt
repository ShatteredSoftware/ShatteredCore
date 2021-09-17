package com.github.shatteredsuite.core.bukkitdispatch.argument

import com.github.shatteredsuite.core.dispatch.argument.impl.primitive.ChoiceArgument
import com.github.shatteredsuite.core.dispatch.context.CommandContext
import org.bukkit.Material

class MaterialArgument(name: String) : ChoiceArgument<CommandContext, Material>(
    name, Material::getMaterial, { Material.values().map { it.name.lowercase() } }, "usage.material", Material.AIR, false
)