package com.mbcdev.folkets

import android.content.Context
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 * Tests for [WordType]
 *
 * Created by barry on 28/09/2016.
 */
class WordTypeTests {

    // Null, junk, and empty value lookup tests

    @Test
    fun lookupEmptyRawTypeShouldReturnUnknown() {
        val wordType = WordType.lookup("")
        assertThat(wordType).isSameAs(WordType.UNKNOWN)
    }

    @Test
    fun lookupWhitespaceRawTypeShouldReturnUnknown() {
        val wordType = WordType.lookup(" ")
        assertThat(wordType).isSameAs(WordType.UNKNOWN)
    }

    @Test
    fun lookupNullRawTypeShouldReturnUnknown() {
        val wordType = WordType.lookup(null)
        assertThat(wordType).isSameAs(WordType.UNKNOWN)
    }

    @Test
    fun lookupJunkRawTypeShouldReturnUnknown() {
        val wordType = WordType.lookup("Gazorpazorpfield")
        assertThat(wordType).isSameAs(WordType.UNKNOWN)
    }

    @Test
    fun lookupPaddedRawTypeShouldReturnNoun() {
        val wordType = WordType.lookup(" nn\t ")
        assertThat(wordType).isSameAs(WordType.NOUN)
    }

    // Happy path tests

    @Test
    fun lookupRawTypeShouldReturnNoun() {
        val wordType = WordType.lookup("nn")
        assertThat(wordType).isSameAs(WordType.NOUN)
    }

    @Test
    fun lookupRawTypeShouldReturnAdjective() {
        val wordType = WordType.lookup("jj")
        assertThat(wordType).isSameAs(WordType.ADJECTIVE)
    }

    @Test
    fun lookupRawTypeShouldReturnPronoun() {
        val wordType = WordType.lookup("pn")
        assertThat(wordType).isSameAs(WordType.PRONOUN)
    }

    @Test
    fun lookupRawTypeShouldReturnPronounDeterminer() {
        val wordType = WordType.lookup("hp")
        assertThat(wordType).isSameAs(WordType.PRONOUN_DETERMINER)
    }

    @Test
    fun lookupRawTypeShouldReturnPronounPossessive() {
        val wordType = WordType.lookup("ps")
        assertThat(wordType).isSameAs(WordType.PRONOUN_POSSESSIVE)
    }

    @Test
    fun lookupRawTypeShouldReturnProperNoun() {
        val wordType = WordType.lookup("pm")
        assertThat(wordType).isSameAs(WordType.PROPER_NOUN)
    }

    @Test
    fun lookupRawTypeShouldReturnVerb() {
        val wordType = WordType.lookup("vb")
        assertThat(wordType).isSameAs(WordType.VERB)
    }

    @Test
    fun lookupRawTypeShouldReturnAdverb() {
        val wordType = WordType.lookup("ab")
        assertThat(wordType).isSameAs(WordType.ADVERB)
    }

    @Test
    fun lookupRawTypeShouldReturnPrefix() {
        val wordType = WordType.lookup("prefix")
        assertThat(wordType).isSameAs(WordType.PREFIX)
    }

    @Test
    fun lookupRawTypeShouldReturnSuffix() {
        val wordType = WordType.lookup("suffix")
        assertThat(wordType).isSameAs(WordType.SUFFIX)
    }

    @Test
    fun lookupRawTypeShouldReturnArticle() {
        val wordType = WordType.lookup("article")
        assertThat(wordType).isSameAs(WordType.ARTICLE)
    }

    @Test
    fun lookupRawTypeShouldReturnAbbreviation() {
        val wordType = WordType.lookup("abbrev")
        assertThat(wordType).isSameAs(WordType.ABBREVIATION)
    }

    @Test
    fun lookupRawTypeShouldReturnPreposition() {
        val wordType = WordType.lookup("pp")
        assertThat(wordType).isSameAs(WordType.PREPOSITION)
    }

    @Test
    fun lookupRawTypeShouldReturnInterjection() {
        val wordType = WordType.lookup("in")
        assertThat(wordType).isSameAs(WordType.INTERJECTION)
    }

    @Test
    fun lookupRawTypeShouldReturnCardinalNumber() {
        val wordType = WordType.lookup("rg")
        assertThat(wordType).isSameAs(WordType.CARDINAL_NUMBER)
    }

    @Test
    fun lookupRawTypeShouldReturnConjunction() {
        val wordType = WordType.lookup("kn")
        assertThat(wordType).isSameAs(WordType.CONJUNCTION)
    }

    @Test
    fun lookupRawTypeShouldReturnInfinitivalMarker() {
        val wordType = WordType.lookup("ie")
        assertThat(wordType).isSameAs(WordType.INFINITIVAL_MARKER)
    }

    @Test
    fun lookupRawTypeShouldReturnSubordinatingConjunction() {
        val wordType = WordType.lookup("sn")
        assertThat(wordType).isSameAs(WordType.SUBORDINATING_CONJUNCTION)
    }

    @Test
    fun lookupRawTypeShouldReturnAuxiliaryVerb() {
        val wordType = WordType.lookup("hjälpverb")
        assertThat(wordType).isSameAs(WordType.AUXILIARY_VERB)
    }

    @Test
    fun lookupRawTypeShouldReturnOrdinalNumber() {
        val wordType = WordType.lookup("ro")
        assertThat(wordType).isSameAs(WordType.ORDINAL_NUMBER)
    }

    @Test
    fun lookupRawTypeShouldReturnLatin() {
        val wordType = WordType.lookup("latin")
        assertThat(wordType).isSameAs(WordType.LATIN)
    }

    @Test
    fun lookupRawTypeShouldReturnParticiple() {
        val wordType = WordType.lookup("pc")
        assertThat(wordType).isSameAs(WordType.PARTICIPLE)
    }

    @Test
    fun lookupRawTypeShouldReturnUnknown() {
        val wordType = WordType.lookup("")
        assertThat(wordType).isSameAs(WordType.UNKNOWN)
    }

    // String resource tests

    @Test
    fun nounShouldHaveCorrectStringResourceId() {
        assertThat(WordType.NOUN.textResourceId).isEqualTo(R.string.word_type_noun)
    }

    @Test
    fun adjectiveShouldHaveCorrectStringResourceId() {
        assertThat(WordType.ADJECTIVE.textResourceId).isEqualTo(R.string.word_type_adjective)
    }

    @Test
    fun pronounShouldHaveCorrectStringResourceId() {
        assertThat(WordType.PRONOUN.textResourceId).isEqualTo(R.string.word_type_pronoun)
    }

    @Test
    fun pronounDeterminerShouldHaveCorrectStringResourceId() {
        assertThat(WordType.PRONOUN_DETERMINER.textResourceId).isEqualTo(R.string.word_type_pronoun_determiner)
    }

    @Test
    fun pronounPossessiveShouldHaveCorrectStringResourceId() {
        assertThat(WordType.PRONOUN_POSSESSIVE.textResourceId).isEqualTo(R.string.word_type_pronoun_possessive)
    }

    @Test
    fun properNounShouldHaveCorrectStringResourceId() {
        assertThat(WordType.PROPER_NOUN.textResourceId).isEqualTo(R.string.word_type_proper_noun)
    }

    @Test
    fun verbShouldHaveCorrectStringResourceId() {
        assertThat(WordType.VERB.textResourceId).isEqualTo(R.string.word_type_verb)
    }

    @Test
    fun adverbShouldHaveCorrectStringResourceId() {
        assertThat(WordType.ADVERB.textResourceId).isEqualTo(R.string.word_type_adverb)
    }

    @Test
    fun prefixShouldHaveCorrectStringResourceId() {
        assertThat(WordType.PREFIX.textResourceId).isEqualTo(R.string.word_type_prefix)
    }

    @Test
    fun suffixShouldHaveCorrectStringResourceId() {
        assertThat(WordType.SUFFIX.textResourceId).isEqualTo(R.string.word_type_suffix)
    }

    @Test
    fun articleShouldHaveCorrectStringResourceId() {
        assertThat(WordType.ARTICLE.textResourceId).isEqualTo(R.string.word_type_article)
    }

    @Test
    fun abbreviationShouldHaveCorrectStringResourceId() {
        assertThat(WordType.ABBREVIATION.textResourceId).isEqualTo(R.string.word_type_abbreviation)
    }

    @Test
    fun prepositionShouldHaveCorrectStringResourceId() {
        assertThat(WordType.PREPOSITION.textResourceId).isEqualTo(R.string.word_type_preposition)
    }

    @Test
    fun interjectionShouldHaveCorrectStringResourceId() {
        assertThat(WordType.INTERJECTION.textResourceId).isEqualTo(R.string.word_type_interjection)
    }

    @Test
    fun cardinalNumberShouldHaveCorrectStringResourceId() {
        assertThat(WordType.CARDINAL_NUMBER.textResourceId).isEqualTo(R.string.word_type_cardinal_number)
    }

    @Test
    fun conjunctionShouldHaveCorrectStringResourceId() {
        assertThat(WordType.CONJUNCTION.textResourceId).isEqualTo(R.string.word_type_conjunction)
    }

    @Test
    fun infinitivalMarkerShouldHaveCorrectStringResourceId() {
        assertThat(WordType.INFINITIVAL_MARKER.textResourceId).isEqualTo(R.string.word_type_infinitivial)
    }

    @Test
    fun subordinatingConjunctionShouldHaveCorrectStringResourceId() {
        assertThat(WordType.SUBORDINATING_CONJUNCTION.textResourceId).isEqualTo(R.string.word_type_subordinating_conjunction)
    }

    @Test
    fun auxiliaryVerbShouldHaveCorrectStringResourceId() {
        assertThat(WordType.AUXILIARY_VERB.textResourceId).isEqualTo(R.string.word_type_auxiliary_verb)
    }

    @Test
    fun ordinalNumberShouldHaveCorrectStringResourceId() {
        assertThat(WordType.ORDINAL_NUMBER.textResourceId).isEqualTo(R.string.word_type_ordinal)
    }

    @Test
    fun latinShouldHaveCorrectStringResourceId() {
        assertThat(WordType.LATIN.textResourceId).isEqualTo(R.string.word_type_latin)
    }

    @Test
    fun participleShouldHaveCorrectStringResourceId() {
        assertThat(WordType.PARTICIPLE.textResourceId).isEqualTo(R.string.word_type_participle)
    }

    @Test
    fun unknownShouldHaveCorrectStringResourceId() {
        assertThat(WordType.UNKNOWN.textResourceId).isEqualTo(R.string.word_type_unknown)
    }

    // formatting tests

    @Test
    fun formatWordTypesShouldBeNullSafeForContext() {
        val formattedTypes = WordType.formatWordTypesForDisplay(null, listOf(WordType.NOUN))
        assertThat(formattedTypes).isNotNull()
        assertThat(formattedTypes).isEmpty()
    }

    @Test
    fun formatWordTypesShouldBeNullSafeForWordTypes() {
        val formattedTypes = WordType.formatWordTypesForDisplay(mockedContext(), null)
        assertThat(formattedTypes).isNotNull()
        assertThat(formattedTypes).isEmpty()
    }

    @Test
    fun formatWordTypesShouldBeNullSafeWhenAllArgumentsAreNull() {
        val formattedTypes = WordType.formatWordTypesForDisplay(null, null)
        assertThat(formattedTypes).isNotNull()
        assertThat(formattedTypes).isEmpty()
    }

    @Test
    fun oneWordShouldBeFormattedCorrectly() {
        val formattedTypes =
                WordType.formatWordTypesForDisplay(mockedContext(), listOf(WordType.NOUN))

        assertThat(formattedTypes).isNotNull()
        assertThat(formattedTypes).isEqualTo("Noun")
    }

    @Test
    fun twoWordsShouldBeFormattedCorrectly() {
        val formattedTypes =
                WordType.formatWordTypesForDisplay(
                        mockedContext(), listOf(WordType.NOUN, WordType.PRONOUN))

        assertThat(formattedTypes).isNotNull()
        assertThat(formattedTypes).isEqualTo("Noun, Pronoun")
    }

    @Test
    fun threeWordsShouldBeFormattedCorrectly() {
        val formattedTypes =
                WordType.formatWordTypesForDisplay(
                        mockedContext(), listOf(WordType.NOUN, WordType.PRONOUN, WordType.ADVERB))

        assertThat(formattedTypes).isNotNull()
        assertThat(formattedTypes).isEqualTo("Noun, Pronoun, Adverb")
    }

    fun mockedContext(): Context {
        val mockedContext = mock(Context::class.java)

        `when`(mockedContext.getString(R.string.word_type_noun)).thenReturn("Noun")
        `when`(mockedContext.getString(R.string.word_type_pronoun)).thenReturn("Pronoun")
        `when`(mockedContext.getString(R.string.word_type_adverb)).thenReturn("Adverb")

        return mockedContext
    }

}