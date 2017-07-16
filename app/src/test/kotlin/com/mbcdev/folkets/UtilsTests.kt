package com.mbcdev.folkets

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

/**
 * Tests for [Utils]
 *
 * Created by barry on 16/07/2017.
 */
class UtilsTests {

    @Test
    fun `listToString should be empty string when list is null`() {
        val stringOfList = Utils.listToString(null)
        assertThat(stringOfList).isEqualTo("")
    }

    @Test
    fun `listToString should be empty string when list is empty`() {
        val stringOfList = Utils.listToString(listOf<String>())
        assertThat(stringOfList).isEqualTo("")
    }

    @Test
    fun `listToString should be single line string when list has one element`() {
        val stringOfList = Utils.listToString(listOf("Hej"))
        assertThat(stringOfList).isEqualTo("Hej")
    }

    @Test
    fun `listToString invokes toString() on objects`() {
        val objectMock = mock(Object::class.java)
        Mockito.`when`(objectMock.toString()).thenReturn("A cromulent string")

        val list = listOf<Any>(objectMock)
        val stringOfList = Utils.listToString(list)

        assertThat(stringOfList).isEqualTo("A cromulent string")
    }

    @Test
    fun `listToString does not have trailing newline character`() {

        val stringOfList = Utils.listToString(listOf("Jag", "har", "flera", "ankor"))

        assertThat(stringOfList).isEqualTo(
                "Jag" + "\n" +
                "har" + "\n" +
                "flera" + "\n" +
                "ankor"
        )

    }
}