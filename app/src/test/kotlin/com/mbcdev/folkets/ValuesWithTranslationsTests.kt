package com.mbcdev.folkets

import org.junit.Test
import com.google.common.truth.Truth.assertThat

/**
 * Tests for [ValuesWithTranslations]
 *
 * Created by barry on 09/10/2016.
 */
class ValuesWithTranslationsTests {

    @Test
    fun constructorShouldBeNullSafe() {
        ValuesWithTranslations(null)
    }

    @Test
    fun valuesShouldBeEmptyWhenRawValueIsNull() {
        val valuesWithTranslations = ValuesWithTranslations(null)
        assertThat(valuesWithTranslations.valuesWithTranslations).isEmpty()
    }

    @Test
    fun valuesShouldBeEmptyWhenRawValueIsEmpty() {
        val valuesWithTranslations = ValuesWithTranslations("")
        assertThat(valuesWithTranslations.valuesWithTranslations).isEmpty()
    }

    @Test
    fun valuesShouldBeEmptyWhenRawValueIsWhitespace() {
        val valuesWithTranslations = ValuesWithTranslations("  \t ")
        assertThat(valuesWithTranslations.valuesWithTranslations).isEmpty()
    }

    @Test
    fun singleValueShouldBeParsed() {
        val valuesWithTranslations = ValuesWithTranslations("value")
        assertThat(valuesWithTranslations.valuesWithTranslations).hasSize(1)
    }

    @Test
    fun twoValuesShouldBeParsed() {
        val valuesWithTranslations = ValuesWithTranslations("value1**value2")
        assertThat(valuesWithTranslations.valuesWithTranslations).hasSize(2)
    }

    @Test
    fun twoValuesShouldBeParsedIgnoringWhitespace() {
        val valuesWithTranslations = ValuesWithTranslations("value1**value2** ")
        assertThat(valuesWithTranslations.valuesWithTranslations).hasSize(2)
    }
}