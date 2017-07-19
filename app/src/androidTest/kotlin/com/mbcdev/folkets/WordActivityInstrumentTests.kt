package com.mbcdev.folkets

import android.content.Context
import android.content.Intent
import android.support.test.runner.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

/**
 * Instrument tests for [WordActivity]
 *
 * Created by barry on 18/07/2017.
 */
@RunWith(AndroidJUnit4::class)
class WordActivityInstrumentTests {

    @Test
    fun intentShouldBePopulatedCorrectly() {
        val mockContext = mock(Context::class.java)
        val word = Word.getTestingWord("bridge", "en")

        WordActivity.startWithWord(mockContext, word)

        val intentCaptor = ArgumentCaptor.forClass(Intent::class.java)
        verify(mockContext).startActivity(intentCaptor.capture())
        val intent = intentCaptor.value
        assertThat(intent).isNotNull()

        assertThat(intent.hasExtra("extra_word")).isTrue()
        val wordFromExtra = intent.getSerializableExtra("extra_word");
        assertThat(wordFromExtra).isInstanceOf(Word::class.java)
        assertThat(wordFromExtra).isEqualTo(word)

        assertThat(intent.component).isNotNull()
        val component = intent.component
        assertThat(component.className).isEqualTo("com.mbcdev.folkets.WordActivity")
    }
}