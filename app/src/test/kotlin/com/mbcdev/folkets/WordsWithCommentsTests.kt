package com.mbcdev.folkets

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Tests for [WordsWithComments]
 *
 * Created by barry on 08/10/2016.
 */

class WordsWithCommentsTests {

    @Test
    fun constructorShouldBeNullSafe() {
        WordsWithComments(null)
    }

    @Test
    fun wordsShouldBeEmptyWhenRawValueIsNull() {
        val wordsWithComments = WordsWithComments(null)
        assertThat(wordsWithComments.words).isNotNull()
        assertThat(wordsWithComments.words).isEmpty()
    }

    @Test
    fun wordsShouldBeEmptyWhenRawValueIsEmpty() {
        val wordsWithComments = WordsWithComments("")
        assertThat(wordsWithComments.words).isNotNull()
        assertThat(wordsWithComments.words).isEmpty()
    }

    @Test
    fun wordsShouldBeEmptyWhenRawValueIsWhitespace() {
        val wordsWithComments = WordsWithComments(" \t")
        assertThat(wordsWithComments.words).isNotNull()
        assertThat(wordsWithComments.words).isEmpty()
    }

    @Test
    fun oneWordWithoutCommentShouldBeParsedCorrectly() {
        val wordsWithComments = WordsWithComments("word")
        assertThat(wordsWithComments.words).isNotNull()
        assertThat(wordsWithComments.words).hasSize(1)

        val word = wordsWithComments.words[0]
        assertThat(word).isNotNull()
        assertThat(word).isEqualTo("1:\tword")
    }

    @Test
    fun twoWordsWithoutCommentShouldBeParsedCorrectly() {
        val wordsWithComments = WordsWithComments("word**another")
        assertThat(wordsWithComments.words).isNotNull()
        assertThat(wordsWithComments.words).hasSize(2)

        val first = wordsWithComments.words[0]
        assertThat(first).isNotNull()
        assertThat(first).isEqualTo("1:\tword")

        val second = wordsWithComments.words[1]
        assertThat(second).isNotNull()
        assertThat(second).isEqualTo("2:\tanother")
    }

    @Test
    fun oneWordWithWhitespaceWithoutCommentShouldBeParsedCorrectly() {
        val wordsWithComments = WordsWithComments(" word  ")
        assertThat(wordsWithComments.words).isNotNull()
        assertThat(wordsWithComments.words).hasSize(1)

        val word = wordsWithComments.words[0]
        assertThat(word).isNotNull()
        assertThat(word).isEqualTo("1:\tword")
    }

    @Test
    fun oneWordWithCommentShouldBeParsedCorrectly() {
        val wordsWithComments = WordsWithComments("word||comment")
        assertThat(wordsWithComments.words).isNotNull()
        assertThat(wordsWithComments.words).hasSize(1)

        val word = wordsWithComments.words[0]
        assertThat(word).isNotNull()
        assertThat(word).isEqualTo("1:\tword (comment)")
    }

    @Test
    fun oneWordWithWhitespaceWithCommentShouldBeParsedCorrectly() {
        val wordsWithComments = WordsWithComments(" word   || comment  ")
        assertThat(wordsWithComments.words).isNotNull()
        assertThat(wordsWithComments.words).hasSize(1)

        val word = wordsWithComments.words[0]
        assertThat(word).isNotNull()
        assertThat(word).isEqualTo("1:\tword (comment)")
    }

    @Test
    fun emptyWordShouldBeSkipped() {
        val emptyWord = WordsWithComments("||comment")
        assertThat(emptyWord.words).isNotNull()
        assertThat(emptyWord.words).hasSize(0)
    }

    @Test
    fun commentShouldBeIgnoredIfEmpty() {
        val withBlankComment = WordsWithComments("word||")
        assertThat(withBlankComment.words).isNotNull()
        assertThat(withBlankComment.words).hasSize(1)
        assertThat(withBlankComment.words[0]).isEqualTo("1:\tword")
    }

    @Test
    fun emptyWordAndCommentShouldBeSkipped() {
        val emptyWordAndComment = WordsWithComments("||")
        assertThat(emptyWordAndComment.words).isNotNull()
        assertThat(emptyWordAndComment.words).hasSize(0)
    }

    @Test
    fun `formattedForDisplay should create string from list`() {
        val multipleWords = WordsWithComments("one**two")
        val formatted = multipleWords.wordsFormattedForDisplay
        assertThat(formatted).isEqualTo("1:\tone\n2:\ttwo")
    }
}