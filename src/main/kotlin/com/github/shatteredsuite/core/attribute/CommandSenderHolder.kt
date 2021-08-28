package com.github.shatteredsuite.core.attribute

import org.bukkit.command.CommandSender

interface CommandSenderHolder {
    fun getSender(): CommandSender
}