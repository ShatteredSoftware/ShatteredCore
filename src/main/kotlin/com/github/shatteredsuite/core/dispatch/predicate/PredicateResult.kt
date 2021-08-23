package com.github.shatteredsuite.core.dispatch.predicate

import com.github.shatteredsuite.core.data.persistence.GenericDataStore

data class PredicateResult(val passed: Boolean = true, val data: GenericDataStore = GenericDataStore())