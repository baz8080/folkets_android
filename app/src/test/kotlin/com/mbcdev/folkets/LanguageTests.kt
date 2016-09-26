package com.mbcdev.folkets

import org.junit.Test
import com.google.common.truth.Truth.assertThat

/**
 * Tests for [Language]
 *
 * Created by barry on 26/09/2016.
 */
class LanguageTests {

    @Test
    fun englishShouldHaveCorrectDatabaseTable() {
        assertThat(Language.ENGLISH.tableName).isEqualTo("folkets_en_sv")
    }

    @Test
    fun englishShouldHaveCorrectLanguageCode() {
        assertThat(Language.ENGLISH.code).isEqualTo("en")
    }

    @Test
    fun swedishShouldHaveCorrectDatabaseTable() {
        assertThat(Language.SWEDISH.tableName).isEqualTo("folkets_sv_en")
    }

    @Test
    fun swedishShouldHaveCorrectLanguageCode() {
        assertThat(Language.SWEDISH.code).isEqualTo("sv")
    }

    @Test
    fun englishShouldBeFoundByLanguageCode() {
        val language = Language.fromLanguageCode("en")
        assertThat(language).isNotNull()
        assertThat(language).isSameAs(Language.ENGLISH)
    }

    @Test
    fun swedishShouldBeFoundByLanguageCode() {
        val language = Language.fromLanguageCode("sv")
        assertThat(language).isNotNull()
        assertThat(language).isSameAs(Language.SWEDISH)
    }

    @Test
    fun findingEnglishShouldBeCaseInsensitive() {
        val language = Language.fromLanguageCode("EN")
        assertThat(language).isNotNull()
        assertThat(language).isSameAs(Language.ENGLISH)
    }

    @Test
    fun findingSwedishShouldBeCaseInsensitive() {
        val language = Language.fromLanguageCode("SV")
        assertThat(language).isNotNull()
        assertThat(language).isSameAs(Language.SWEDISH)
    }

    @Test
    fun englishShouldBeDefaultForUnknownLanguage() {
        val language = Language.fromLanguageCode("zz")
        assertThat(language).isNotNull()
        assertThat(language).isSameAs(Language.ENGLISH)
    }

    @Test
    fun englishShouldBeDefaultForEmptyLanguage() {
        val language = Language.fromLanguageCode("")
        assertThat(language).isNotNull()
        assertThat(language).isSameAs(Language.ENGLISH)
    }

    @Test
    fun englishShouldBeDefaultForNullLanguage() {
        val language = Language.fromLanguageCode(null)
        assertThat(language).isNotNull()
        assertThat(language).isSameAs(Language.ENGLISH)
    }
}