package com.github.shatteredsuite.core.dispatch.predicate

import com.github.shatteredsuite.core.data.generic.GenericDataStore

data class PredicateResult(val passed: Boolean = true, val data: GenericDataStore = GenericDataStore())