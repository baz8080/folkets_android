package com.mbcdev.folkets

import android.content.Context
import com.google.common.truth.Truth.assertThat
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import java.util.*

/**
 * Test for [SaldoLink]
 *
 * Created by barry on 01/10/2016.
 */
@RunWith(JUnitParamsRunner::class)
class SaldoLinkTests {

    lateinit var context: Context

    @Before
    fun setUp() {
        context = mock(Context::class.java)
        `when`(context.getString(R.string.link_word)).thenReturn("WORD")
        `when`(context.getString(R.string.link_word_format)).thenReturn("word %s %s")

        `when`(context.getString(R.string.link_inflections)).thenReturn("INFLECTIONS")
        `when`(context.getString(R.string.link_inflections_format)).thenReturn("inflections %s %s")

        `when`(context.getString(R.string.link_associations)).thenReturn("ASSOCIATIONS")
        `when`(context.getString(R.string.link_associations_format)).thenReturn("associations %s %s")
    }

    @Test
    fun constructorShouldBeNullSafe() {
        val rawType: String? = null
        SaldoLink(rawType)
    }

    @Test
    fun hasValidLinksShouldBeFalseForNullRawValue() {
        val rawType: String? = null
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.hasValidLinks()).isFalse()
    }

    @Test
    fun wordLinkShouldBeEmptyForNullRawValue() {
        val rawType: String? = null
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getWordLink(context)).isEmpty()
    }

    @Test
    fun inflectionLinkShouldBeEmptyForNullRawValue() {
        val rawType: String? = null
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getInflectionsLink(context)).isEmpty()
    }

    @Test
    fun associationLinkShouldBeEmptyForNullRawValue() {
        val rawType: String? = null
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getAssociationsLink(context)).isEmpty()
    }

    @Test
    fun constructorShouldBeEmptySafe() {
        val rawType: String = ""
        SaldoLink(rawType)
    }

    @Test
    fun hasValidLinksShouldBeFalseForEmptyRawValue() {
        val rawType: String = ""
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.hasValidLinks()).isFalse()
    }

    @Test
    fun wordLinkShouldBeEmptyForEmptyRawValue() {
        val rawType: String = ""
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getWordLink(context)).isEmpty()
    }

    @Test
    fun inflectionLinkShouldBeEmptyForEmptyRawValue() {
        val rawType: String = ""
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getInflectionsLink(context)).isEmpty()
    }

    @Test
    fun associationLinkShouldBeEmptyForEmptyRawValue() {
        val rawType: String = ""
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getAssociationsLink(context)).isEmpty()
    }
    
    @Test
    fun hasValidLinksShouldBeFalseForNonPipedRawValue() {
        val rawType: String = "plumbus"
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.hasValidLinks()).isFalse()
    }

    @Test
    fun wordLinkShouldBeEmptyForNonPipedRawValue() {
        val rawType: String = "plumbus"
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getWordLink(context)).isEmpty()
    }

    @Test
    fun inflectionLinkShouldBeEmptyForNonPipedRawValue() {
        val rawType: String = "plumbus"
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getInflectionsLink(context)).isEmpty()
    }

    @Test
    fun associationLinkShouldBeEmptyForNonPipedRawValue() {
        val rawType: String = "plumbus"
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getAssociationsLink(context)).isEmpty()
    }

    @Test
    fun hasValidLinksShouldBeFalseForSinglePipedRawValue() {
        val rawType: String = "plumbus||"
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.hasValidLinks()).isFalse()
    }

    @Test
    fun wordLinkShouldBeEmptyForSinglePipedRawValue() {
        val rawType: String = "plumbus||"
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getWordLink(context)).isEmpty()
    }

    @Test
    fun inflectionLinkShouldBeEmptyForSinglePipedRawValue() {
        val rawType: String = "plumbus||"
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getInflectionsLink(context)).isEmpty()
    }

    @Test
    fun associationLinkShouldBeEmptyForSinglePipedRawValue() {
        val rawType: String = "plumbus||"
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getAssociationsLink(context)).isEmpty()
    }

    @Test
    fun hasValidLinksShouldBeFalseForDoublePipedRawValue() {
        val rawType: String = "plumbus||plumbus2||"
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.hasValidLinks()).isFalse()
    }

    @Test
    fun wordLinkShouldBeEmptyForDoublePipedRawValue() {
        val rawType: String = "plumbus||plumbus2||"
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getWordLink(context)).isEmpty()
    }

    @Test
    fun inflectionLinkShouldBeEmptyForDoublePipedRawValue() {
        val rawType: String = "plumbus||plumbus2||"
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getInflectionsLink(context)).isEmpty()
    }

    @Test
    fun associationLinkShouldBeEmptyForDoublePipedRawValue() {
        val rawType: String = "plumbus||plumbus2||"
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getAssociationsLink(context)).isEmpty()
    }

    @Test
    fun hasValidLinksShouldBeFalseForDoublePipedWithEmptyThirdRawValue() {
        val rawType: String = "plumbus||plumbus2|| "
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.hasValidLinks()).isFalse()
    }

    @Test
    fun wordLinkShouldBeEmptyForDoublePipedWithEmptyThirdComponentRawValue() {
        val rawType: String = "plumbus||plumbus2|| "
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getWordLink(context)).isEmpty()
    }

    @Test
    fun inflectionLinkShouldBeEmptyForDoublePipedWithEmptyThirdComponentRawValue() {
        val rawType: String = "plumbus||plumbus2|| "
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getInflectionsLink(context)).isEmpty()
    }

    @Test
    fun associationLinkShouldBeEmptyForDoublePipedWithEmptyThirdComponentRawValue() {
        val rawType: String = "plumbus||plumbus2|| "
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.getAssociationsLink(context)).isEmpty()
    }

    @Test
    @Parameters(method = "incompleteValues")
    fun linksShouldBeInvalidIfAnyComponentsAreMissing(word: String?, association: String?, inflection: String?) {
        val rawType = String.format(Locale.US, "%s||%s||%s",
                word ?: "",
                association ?: "",
                inflection ?: ""
        )

        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.hasValidLinks()).isFalse()
    }

    @Test
    fun linksShouldBeGeneratedCorrectly() {

        `when`(context.getString(anyInt(), any())).thenAnswer({

            val resId = it.arguments[0] as Int
            val rawLinkValue = it.arguments[1]
            val rawLinkLabel = it.arguments[2]

            String.format(Locale.US, context.getString(resId), rawLinkValue, rawLinkLabel)
        })

        val rawType = "rick||morty||gerry"
        val saldoLink = SaldoLink(rawType)
        assertThat(saldoLink.hasValidLinks()).isTrue()

        assertThat(saldoLink.getWordLink(context)).isEqualTo("word rick WORD")
        assertThat(saldoLink.getAssociationsLink(context)).isEqualTo("associations morty ASSOCIATIONS")
        assertThat(saldoLink.getInflectionsLink(context)).isEqualTo("inflections gerry INFLECTIONS")
    }

    private fun incompleteValues(): Array<Any> {
        return arrayOf(
                arrayOf<Any?>(null, null, null),
                arrayOf<Any?>(null, null, "gerry"),
                arrayOf<Any?>(null, "morty", null),
                arrayOf<Any?>(null, "morty", "gerry"),
                arrayOf<Any?>("rick", null, null),
                arrayOf<Any?>("rick", null, "gerry"),
                arrayOf<Any?>("rick", "morty", null)
        )
    }
}