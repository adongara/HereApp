package com.example.hereapp.data

import org.junit.Assert.*
import org.junit.Test

class ContactTest {

    @Test
    fun defaultId_isZero() {
        val contact = Contact(name = "Alice", phone = "555-1234")
        assertEquals(0L, contact.id)
    }

    @Test
    fun constructor_setsAllFields() {
        val contact = Contact(id = 5, name = "Bob", phone = "555-5678")
        assertEquals(5L, contact.id)
        assertEquals("Bob", contact.name)
        assertEquals("555-5678", contact.phone)
    }

    @Test
    fun equality_sameValues_areEqual() {
        val a = Contact(id = 1, name = "Alice", phone = "555-1234")
        val b = Contact(id = 1, name = "Alice", phone = "555-1234")
        assertEquals(a, b)
    }

    @Test
    fun equality_differentId_notEqual() {
        val a = Contact(id = 1, name = "Alice", phone = "555-1234")
        val b = Contact(id = 2, name = "Alice", phone = "555-1234")
        assertNotEquals(a, b)
    }

    @Test
    fun equality_differentName_notEqual() {
        val a = Contact(id = 1, name = "Alice", phone = "555-1234")
        val b = Contact(id = 1, name = "Bob", phone = "555-1234")
        assertNotEquals(a, b)
    }

    @Test
    fun equality_differentPhone_notEqual() {
        val a = Contact(id = 1, name = "Alice", phone = "555-1234")
        val b = Contact(id = 1, name = "Alice", phone = "555-9999")
        assertNotEquals(a, b)
    }

    @Test
    fun hashCode_sameValues_areEqual() {
        val a = Contact(id = 1, name = "Alice", phone = "555-1234")
        val b = Contact(id = 1, name = "Alice", phone = "555-1234")
        assertEquals(a.hashCode(), b.hashCode())
    }

    @Test
    fun copy_changesSpecifiedFields() {
        val original = Contact(id = 1, name = "Alice", phone = "555-1234")
        val copied = original.copy(name = "Bob")
        assertEquals("Bob", copied.name)
        assertEquals(original.id, copied.id)
        assertEquals(original.phone, copied.phone)
    }

    @Test
    fun copy_noArgs_producesEqual() {
        val original = Contact(id = 1, name = "Alice", phone = "555-1234")
        assertEquals(original, original.copy())
    }

    @Test
    fun destructuring_returnsFieldsInOrder() {
        val contact = Contact(id = 7, name = "Charlie", phone = "555-0000")
        val (id, name, phone) = contact
        assertEquals(7L, id)
        assertEquals("Charlie", name)
        assertEquals("555-0000", phone)
    }

    @Test
    fun toString_containsAllFields() {
        val contact = Contact(id = 1, name = "Alice", phone = "555-1234")
        val str = contact.toString()
        assertTrue(str.contains("Alice"))
        assertTrue(str.contains("555-1234"))
        assertTrue(str.contains("1"))
    }
}
