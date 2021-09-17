package com.github.shatteredsuite.core.persistence

import com.github.shatteredsuite.core.data.persistence.Persistence
import com.github.shatteredsuite.core.plugin.config.CoreConfig
import com.google.gson.Gson
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import java.io.File

class TestPersistence {

    @Test
    fun `test loading config yml`() {
        val coreConfig = Persistence.loadYamlFileAs<CoreConfig>(File(this.javaClass.classLoader.getResource("config.yml")!!.file), Gson())!!
        assertThat(coreConfig.defaultLocale, equalTo("test"))
        assertThat(coreConfig.storageType, equalTo(CoreConfig.StorageType.FLATFILE))
        assertThat(coreConfig.storageSettings?.host, equalTo("localhost"))
        assertThat(coreConfig.storageSettings?.driver, equalTo("mysql"))
        assertThat(coreConfig.storageSettings?.password, equalTo("changeme"))
        assertThat(coreConfig.storageSettings?.port, equalTo(3306))
    }
}