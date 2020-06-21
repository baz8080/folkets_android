package com.mbcdev.folkets

import android.database.Cursor
import com.google.common.truth.Truth.assertThat
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 * Tests for [Word]
 *
 * Created by barry on 14/10/2016.
 */
@RunWith(JUnitParamsRunner::class)
class WordTests {

    lateinit var mockCursor: Cursor

    @Before
    fun setUp() {
        mockCursor = mock(Cursor::class.java)
    }

    @Test
    fun wordConstructorShouldBeNullSafe() {
        Word(null)
    }

    @Test
    fun wordShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("word")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("plumbus")

        val word = Word(mockCursor)
        assertThat(word.word).isEqualTo("plumbus")
    }

    @Test
    fun commentShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("comment")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("birdperson")

        val word = Word(mockCursor)
        assertThat(word.comment).isEqualTo("birdperson")
    }

    @Test
    fun wordTypesShouldBeUnknownIfNotSet() {
        `when`(mockCursor.getColumnIndex("types")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn(null)

        val word = Word(mockCursor)
        assertThat(word.wordTypes).hasSize(1)
        assertThat(word.wordTypes[0]).isEqualTo(WordType.UNKNOWN)
    }

    @Test
    fun wordTypesShouldBeUnknownIfEmpty() {
        `when`(mockCursor.getColumnIndex("types")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("")

        val word = Word(mockCursor)
        assertThat(word.wordTypes).hasSize(1)
        assertThat(word.wordTypes[0]).isEqualTo(WordType.UNKNOWN)
    }

    @Test
    fun wordTypesShouldBeUnknownIfWhitespace() {
        `when`(mockCursor.getColumnIndex("types")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn(" \t")

        val word = Word(mockCursor)
        assertThat(word.wordTypes).hasSize(1)
        assertThat(word.wordTypes[0]).isEqualTo(WordType.UNKNOWN)
    }

    @Test
    fun multipleWordTypesShouldBePopulated() {
        `when`(mockCursor.getColumnIndex("types")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("nn, jj,pn ")

        val word = Word(mockCursor)
        assertThat(word.wordTypes).hasSize(3)
        assertThat(word.wordTypes).containsExactly(
            WordType.NOUN, WordType.ADJECTIVE, WordType.PRONOUN)
    }

    @Test
    fun translationsShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("translations")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("word1**word2")

        val word = Word(mockCursor)
        assertThat(word.translations).isNotNull()
        assertThat(word.translations.words).hasSize(2)
        assertThat(word.translations.words).containsExactly("1:\tword1", "2:\tword2")
    }

    @Test
    fun inflectionsShouldBeReadFromCursorWhenNotSet() {
        `when`(mockCursor.getColumnIndex("inflections")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn(null)

        val word = Word(mockCursor)
        assertThat(word.inflections).isEmpty()
    }

    @Test
    fun inflectionsShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("inflections")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("i1** i2** i3 ** ")

        val word = Word(mockCursor)
        assertThat(word.inflections).hasSize(3)
        assertThat(word.inflections).containsExactly("i1", "i2", "i3")
    }

    @Test
    fun examplesShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("examples")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("book||bok")

        val word = Word(mockCursor)
        assertThat(word.examples).isNotNull()
        assertThat(word.examples.valuesWithTranslations).hasSize(1)
        assertThat(word.examples.valuesWithTranslations[0]).isNotNull()
        assertThat(word.examples.valuesWithTranslations[0].value).isEqualTo("book")
        assertThat(word.examples.valuesWithTranslations[0].translation).isEqualTo("bok")
    }

    @Test
    fun definitionShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("definition")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("def1||def2")

        val word = Word(mockCursor)
        assertThat(word.definition).isNotNull()
        assertThat(word.definition.value).isEqualTo("def1")
        assertThat(word.definition.translation).isEqualTo("def2")
    }

    @Test
    fun explanationShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("explanation")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("exp1||exp2")

        val word = Word(mockCursor)
        assertThat(word.explanation).isNotNull()
        assertThat(word.explanation.value).isEqualTo("exp1")
        assertThat(word.explanation.translation).isEqualTo("exp2")
    }

    @Test
    fun phoneticShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("phonetic")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("pronounce")

        val word = Word(mockCursor)
        assertThat(word.phonetic).isEqualTo("pronounce")
    }

    @Test
    fun saldoLinksShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("saldos")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("a||b||c")

        val word = Word(mockCursor)
        assertThat(word.saldoLinks).isNotNull()
        assertThat(word.saldoLinks.links).hasSize(1)
        assertThat(word.saldoLinks.links[0].hasValidLinks()).isTrue()
    }

    @Test
    fun synonymsShouldBeReadFromCursorWhenNotSet() {
        `when`(mockCursor.getColumnIndex("synonyms")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn(null)

        val word = Word(mockCursor)
        assertThat(word.synonyms).isEmpty()
    }

    @Test
    fun synonymsShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("synonyms")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("one**** two** three** ")

        val word = Word(mockCursor)
        assertThat(word.synonyms).hasSize(3)
        assertThat(word.synonyms).containsExactly("one", "two", "three")
    }

    @Test
    fun compareWithShouldBeReadFromCursorWhenNotSet() {
        `when`(mockCursor.getColumnIndex("comparisons")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn(null)

        val word = Word(mockCursor)
        assertThat(word.compareWith).isEmpty()
    }

    @Test
    fun compareWithShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("comparisons")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("c1**c3**c2")

        val word = Word(mockCursor)
        assertThat(word.compareWith).hasSize(3)
        assertThat(word.compareWith).containsExactly("c1", "c3", "c2")
    }

    @Test
    fun antonymsShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("antonyms")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("ant1||ant2")

        val word = Word(mockCursor)
        assertThat(word.antonyms).isNotNull()
        assertThat(word.antonyms.valuesWithTranslations).hasSize(1)
        assertThat(word.antonyms.valuesWithTranslations[0]).isNotNull()
        assertThat(word.antonyms.valuesWithTranslations[0].value).isEqualTo("ant1")
        assertThat(word.antonyms.valuesWithTranslations[0].translation).isEqualTo("ant2")
    }

    @Test
    fun usageShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("use")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("usage...")

        val word = Word(mockCursor)
        assertThat(word.usage).isEqualTo("usage...")
    }

    @Test
    fun variantShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("variant")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("variant...")

        val word = Word(mockCursor)
        assertThat(word.variant).isEqualTo("variant...")
    }

    @Test
    fun idiomsShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("idioms")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("id1||id2")

        val word = Word(mockCursor)
        assertThat(word.idioms).isNotNull()
        assertThat(word.idioms.valuesWithTranslations).hasSize(1)
        assertThat(word.idioms.valuesWithTranslations[0]).isNotNull()
        assertThat(word.idioms.valuesWithTranslations[0].value).isEqualTo("id1")
        assertThat(word.idioms.valuesWithTranslations[0].translation).isEqualTo("id2")
    }

    @Test
    fun derivationsShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("derivations")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("der1||der2")

        val word = Word(mockCursor)
        assertThat(word.derivations).isNotNull()
        assertThat(word.derivations.valuesWithTranslations).hasSize(1)
        assertThat(word.derivations.valuesWithTranslations[0]).isNotNull()
        assertThat(word.derivations.valuesWithTranslations[0].value).isEqualTo("der1")
        assertThat(word.derivations.valuesWithTranslations[0].translation).isEqualTo("der2")
    }

    @Test
    fun compoundsShouldBeReadFromCursor() {
        `when`(mockCursor.getColumnIndex("compounds")).thenReturn(1)
        `when`(mockCursor.getString(1)).thenReturn("com1||com2")

        val word = Word(mockCursor)
        assertThat(word.compounds).isNotNull()
        assertThat(word.compounds.valuesWithTranslations).hasSize(1)
        assertThat(word.compounds.valuesWithTranslations[0]).isNotNull()
        assertThat(word.compounds.valuesWithTranslations[0].value).isEqualTo("com1")
        assertThat(word.compounds.valuesWithTranslations[0].translation).isEqualTo("com2")
    }

    @Test
    fun `flag should be uk when word sourceLanguage is null`() {
        val word = Word.getTestingWord("Eon", null)
        assertThat(word.flag).isEqualTo(R.drawable.flag_uk)
    }

    @Test
    fun `flag should be uk when word sourceLanguage is english`() {
        val word = Word.getTestingWord("Extinguisher", "en")
        assertThat(word.flag).isEqualTo(R.drawable.flag_uk)
    }

    @Test
    fun `flag should be swedish when word sourceLanguage is swedish`() {
        val word = Word.getTestingWord("Tambur", "sv")
        assertThat(word.flag).isEqualTo(R.drawable.flag_sv)
    }

    @Test
    fun `flag should be uk when word sourceLanguage is not english or swedish`() {
        val word = Word.getTestingWord("petaQ", "tlh")
        assertThat(word.flag).isEqualTo(R.drawable.flag_uk)
    }

    @Test
    fun `source language should be en`() {
        val word = Word.getTestingWord("Chicken", "en")
        assertThat(word.sourceLanguage).isEqualTo("en")
    }

    @Test
    fun `source language should be sv`() {
        val word = Word.getTestingWord("Kyckling", "sv")
        assertThat(word.sourceLanguage).isEqualTo("sv")
    }

    @Test
    @Parameters(
            "a,a,0",
            "a,A,0",
            "A,a,0",
            "A,A,0",
            "a,b,-1",
            "a,B,-1",
            "A,b,-1",
            "A,B,-1",
            "b,a,1",
            "b,A,1",
            "B,a,1",
            "B,A,1"
    )
    fun `words should be sorted alphabetically and ignoring case`(
            firstString : String, secondString : String, expectedOrder : Int) {
        val firstWord = Word.getTestingWord(firstString, "en")
        val secondWord = Word.getTestingWord(secondString, "en")

        assertThat(firstWord.compareTo(secondWord)).isEqualTo(expectedOrder)
    }

}