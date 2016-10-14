package com.mbcdev.folkets

import org.junit.Test
import com.google.common.truth.Truth.assertThat

/**
 * Tests for [ValueWithTranslation]
 *
 * Created by barry on 09/10/2016.
 */
class ValueWithTranslationTests {

    @Test
    fun constructorShouldBeNullSafe() {
        ValueWithTranslation(null)
    }

    @Test
    fun valueShouldBeEmptyWhenRawValueIsNull() {
        val valueWithTranslation = ValueWithTranslation(null)
        assertThat(valueWithTranslation.value).isEmpty()
    }

    @Test
    fun translationShouldBeEmptyWhenRawValueIsNull() {
        val valueWithTranslation = ValueWithTranslation(null)
        assertThat(valueWithTranslation.translation).isEmpty()
    }

    @Test
    fun valueShouldBeEmptyWhenRawValueIsEmpty() {
        val valueWithTranslation = ValueWithTranslation("")
        assertThat(valueWithTranslation.value).isEmpty()
    }

    @Test
    fun translationShouldBeEmptyWhenRawValueIsEmpty() {
        val valueWithTranslation = ValueWithTranslation("")
        assertThat(valueWithTranslation.translation).isEmpty()
    }

    @Test
    fun valueShouldBeEmptyWhenRawValueIsWhitespace() {
        val valueWithTranslation = ValueWithTranslation(" \t ")
        assertThat(valueWithTranslation.value).isEmpty()
    }

    @Test
    fun translationShouldBeEmptyWhenRawValueIsWhitespace() {
        val valueWithTranslation = ValueWithTranslation("\t ")
        assertThat(valueWithTranslation.translation).isEmpty()
    }

    @Test
    fun valueShouldBeParsed() {
        val valueWithTranslation = ValueWithTranslation("book")
        assertThat(valueWithTranslation.value).isEqualTo("book")
    }

    @Test
    fun valueShouldBeParsedIgnoringWhitespace() {
        val valueWithTranslation = ValueWithTranslation("book \t")
        assertThat(valueWithTranslation.value).isEqualTo("book")
    }

    @Test
    fun translationShouldBeParsed() {
        val valueWithTranslation = ValueWithTranslation("book||bok")
        assertThat(valueWithTranslation.translation).isEqualTo("bok")
    }

    @Test
    fun translationShouldBeParsedIgnoringWhitespace() {
        val valueWithTranslation = ValueWithTranslation(" book ||  bok\t ")
        assertThat(valueWithTranslation.translation).isEqualTo("bok")
    }
}