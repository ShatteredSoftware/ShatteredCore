package com.github.shatteredsuite.core.dispatch.argument

import com.github.shatteredsuite.core.data.generic.GenericDataStore
import com.github.shatteredsuite.core.data.generic.MutableDataStore

data class ArgumentValidationResult<T>(
    val success: Boolean = false,
    val result: T? = null,
    val faliureMessageId: String = "invalid-input",
    val data: MutableDataStore = GenericDataStore()
)