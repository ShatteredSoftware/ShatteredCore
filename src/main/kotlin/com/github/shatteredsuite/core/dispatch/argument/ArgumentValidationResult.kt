package com.github.shatteredsuite.core.dispatch.argument

import com.github.shatteredsuite.core.data.generic.GenericDataStore

data class ArgumentValidationResult<T>(
    val success: Boolean = false,
    val result: T? = null,
    val faliureMessageId: String = "invalid-input",
    val data: GenericDataStore = GenericDataStore()
)