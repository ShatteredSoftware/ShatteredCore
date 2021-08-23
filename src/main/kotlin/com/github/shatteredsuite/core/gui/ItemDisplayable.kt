package com.github.shatteredsuite.core.gui

import org.bukkit.inventory.ItemStack

interface ItemDisplayable {
    fun toItemStack(): ItemStack
}