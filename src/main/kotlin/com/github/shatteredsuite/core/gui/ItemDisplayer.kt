package com.github.shatteredsuite.core.gui

import org.bukkit.inventory.ItemStack

interface ItemDisplayer<T> {
    fun display(value: T): ItemStack
}