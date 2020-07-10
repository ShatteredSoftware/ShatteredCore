package com.github.shatteredsuite.core.util

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TestManager {
    private lateinit var manager: Manager<IdentifiedString>
    private val initialValues: List<IdentifiedString> = listOf(IdentifiedString("primary", "First Value"),
        IdentifiedString("secondary", "Second Value"), IdentifiedString("tertiary", "Third Value"))

    @Before
    fun setUp() {
        manager = Manager()
        for (value in initialValues) {
            manager.register(value)
        }
    }

    @Test
    fun `test contains()`() {
        Assert.assertTrue(manager.has("primary"))
        Assert.assertTrue(manager.has("PRIMARY"))
        Assert.assertFalse(manager.has("quaternary"))
    }

    @Test
    fun `test register()`() {
        manager.register(IdentifiedString("quaternary", "Fourth Value"))
        Assert.assertTrue(manager.has("quaternary"))
        Assert.assertTrue(manager.has("QUATERNARY"))
        Assert.assertTrue(manager.has("QuatErnaRy"))
    }

    @Test
    fun `test get()`() {
        Assert.assertEquals("First Value", manager.get("primary")!!.value)
    }

    @Test
    fun `test delete()`() {
        manager.delete("tertiary")
        manager.delete(initialValues[1])
        Assert.assertFalse(manager.has("secondary"))
        Assert.assertFalse(manager.has("tertiary"))
    }

    @Test
    fun `test getAll()`() {
        val values = manager.getAll()
        val valuesArr = values.toList().toTypedArray()
        val expectedArr = initialValues.toTypedArray()
        Assert.assertArrayEquals(valuesArr, expectedArr)
    }

    @Test
    fun `test getIds()`() {
        val values = manager.getIds()
        val valuesArr = values.toList().toTypedArray()
        val expectedArr = arrayOf("primary", "secondary", "tertiary")
        Assert.assertArrayEquals(valuesArr, expectedArr)
    }

    internal class IdentifiedString(override val id: String, val value: String) : Identified {
        override fun equals(other: Any?): Boolean {
            if(other !is IdentifiedString) {
                return false
            }
            return id == other.id && value == other.id
        }

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + value.hashCode()
            return result
        }
    }
}