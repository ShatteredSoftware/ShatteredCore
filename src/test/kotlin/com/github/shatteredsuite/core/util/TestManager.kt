package com.github.shatteredsuite.core.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestManager {
    private lateinit var manager: Manager<IdentifiedString>
    private val initialValues: List<IdentifiedString> = listOf(IdentifiedString("primary", "First Value"),
        IdentifiedString("secondary", "Second Value"), IdentifiedString("tertiary", "Third Value"))

    @BeforeEach
    fun setUp() {
        manager = Manager()
        for (value in initialValues) {
            manager.register(value)
        }
    }

    @Test
    fun `test contains`() {
        Assertions.assertTrue(manager.has("primary"))
        Assertions.assertFalse(manager.has("quaternary"))
    }

    @Test
    fun `test contains() has case insensitivity`() {
        Assertions.assertTrue(manager.has("PRIMARY"))
        Assertions.assertTrue(manager.has("PriMaRy"))
        Assertions.assertFalse(manager.has("QUATERNARY"))
    }

    @Test
    fun `test register`() {
        manager.register(IdentifiedString("quaternary", "Fourth Value"))
        Assertions.assertTrue(manager.has("quaternary"))
        Assertions.assertTrue(manager.has("QUATERNARY"))
        Assertions.assertTrue(manager.has("QuatErnaRy"))
    }

    @Test
    fun `test get`() {
        Assertions.assertEquals("First Value", manager.get("primary")!!.value)
    }

    @Test
    fun `test delete`() {
        manager.delete("tertiary")
        manager.delete(initialValues[1])
        Assertions.assertFalse(manager.has("secondary"))
        Assertions.assertFalse(manager.has("tertiary"))
    }

    @Test
    fun `test getAll`() {
        val values = manager.getAll()
        val valuesArr = values.toList().toTypedArray()
        val expectedArr = initialValues.toTypedArray()
        Assertions.assertArrayEquals(valuesArr, expectedArr)
    }

    @Test
    fun `test getIds`() {
        val values = manager.getIds()
        val valuesArr = values.toList().toTypedArray()
        val expectedArr = arrayOf("primary", "secondary", "tertiary")
        Assertions.assertArrayEquals(valuesArr, expectedArr)
    }
}