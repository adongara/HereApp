package com.example.hereapp.data

import org.junit.Assert.*
import org.junit.Test

class MessageTest {

    @Test
    fun defaultId_isZero() {
        val message = Message(text = "Hello")
        assertEquals(0L, message.id)
    }

    @Test
    fun constructor_setsAllFields() {
        val message = Message(id = 3, text = "I'm here!")
        assertEquals(3L, message.id)
        assertEquals("I'm here!", message.text)
    }

    @Test
    fun equality_sameValues_areEqual() {
        val a = Message(id = 1, text = "Hi")
        val b = Message(id = 1, text = "Hi")
        assertEquals(a, b)
    }

    @Test
    fun equality_differentId_notEqual() {
        val a = Message(id = 1, text = "Hi")
        val b = Message(id = 2, text = "Hi")
        assertNotEquals(a, b)
    }

    @Test
    fun equality_differentText_notEqual() {
        val a = Message(id = 1, text = "Hi")
        val b = Message(id = 1, text = "Bye")
        assertNotEquals(a, b)
    }

    @Test
    fun hashCode_sameValues_areEqual() {
        val a = Message(id = 1, text = "Hi")
        val b = Message(id = 1, text = "Hi")
        assertEquals(a.hashCode(), b.hashCode())
    }

    @Test
    fun copy_changesSpecifiedFields() {
        val original = Message(id = 1, text = "Hi")
        val copied = original.copy(text = "Bye")
        assertEquals("Bye", copied.text)
        assertEquals(original.id, copied.id)
    }

    @Test
    fun copy_noArgs_producesEqual() {
        val original = Message(id = 1, text = "Hi")
        assertEquals(original, original.copy())
    }

    @Test
    fun destructuring_returnsFieldsInOrder() {
        val message = Message(id = 5, text = "Hello world")
        val (id, text) = message
        assertEquals(5L, id)
        assertEquals("Hello world", text)
    }

    @Test
    fun toString_containsAllFields() {
        val message = Message(id = 1, text = "Hello")
        val str = message.toString()
        assertTrue(str.contains("Hello"))
        assertTrue(str.contains("1"))
    }

    @Test
    fun emptyText_isAllowed() {
        val message = Message(text = "")
        assertEquals("", message.text)
    }
}
