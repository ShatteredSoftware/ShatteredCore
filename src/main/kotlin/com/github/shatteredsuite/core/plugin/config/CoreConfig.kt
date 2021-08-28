package com.github.shatteredsuite.core.plugin.config

data class CoreConfig(val defaultLocale: String, val listFeatures: Boolean, val storageType: StorageType, val storageSettings: StorageSettings?) {
    enum class StorageType {
        FLATFILE,
        MYSQL
    }
}