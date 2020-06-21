package com.mbcdev.folkets

import android.content.Context
import com.crashlytics.android.answers.ContentViewEvent
import com.crashlytics.android.answers.SearchEvent
import com.google.common.truth.Truth.assertThat
import com.zendesk.test.ReflectionTestUtils.getField
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

/**
 * Tests for [EventPopulator]
 *
 * Created by barry on 31/07/2017.
 */
class FolketsAnswersEventPopulatorTests {

    private lateinit var populator : EventPopulator

    @Before
    fun setup() {
        populator = EventPopulator()
    }

    @Test
    fun `content view event is populated correctly`() {
        val event = mock(ContentViewEvent::class.java)

        val word = mock(Word::class.java)
        `when`(word.wordTypes).thenReturn(listOf(WordType.NOUN))
        `when`(word.word).thenReturn("Fake word")
        `when`(word.sourceLanguage).thenReturn("xx")

        val context = mock(Context::class.java)
        `when`(context.getString(anyInt())).thenReturn("Fake word type")

        populator.contentViewEvent(context, word, event)

        val nameCaptor = ArgumentCaptor.forClass(String::class.java)
        val typeCaptor = ArgumentCaptor.forClass(String::class.java)
        val customAttributeKeyCaptor = ArgumentCaptor.forClass(String::class.java)
        val customAttributeValueCaptor = ArgumentCaptor.forClass(String::class.java)

        verify(event).putContentName(nameCaptor.capture())
        verify(event).putContentType(typeCaptor.capture())
        verify(event).putCustomAttribute(
                customAttributeKeyCaptor.capture(),
                customAttributeValueCaptor.capture())

        assertThat(nameCaptor.value).isEqualTo("Fake word")
        assertThat(typeCaptor.value).isEqualTo("xx word")
        assertThat(customAttributeKeyCaptor.value).isEqualTo("Types")
        assertThat(customAttributeValueCaptor.value).isEqualTo("Fake word type")
    }

    @Test
    fun `tts event has correct name`() {
        val event = TtsEvent()
        val eventName = getField<String>("eventName", event)
        assertThat(eventName).isEqualTo("TTS")
    }

    @Test
    fun `tts event is populated correctly`() {
        val event = mock(TtsEvent::class.java)

        populator.ttsEvent("zz", "fake phrase", event)

        val languageCaptor = ArgumentCaptor.forClass(String::class.java)
        val phraseCaptor = ArgumentCaptor.forClass(String::class.java)

        verify(event).putCustomAttribute(eq("Language"), languageCaptor.capture())
        verify(event).putCustomAttribute(eq("Phrase"), phraseCaptor.capture())

        val language = languageCaptor.value
        val phrase = phraseCaptor.value

        assertThat(language).isEqualTo("zz")
        assertThat(phrase).isEqualTo("fake phrase")
    }

    @Test
    fun `search event is populated correctly`() {
        val event = mock(SearchEvent::class.java)

        populator.searchEvent("fake query", event)

        val queryCaptor = ArgumentCaptor.forClass(String::class.java)
        verify(event).putQuery(queryCaptor.capture())
        val query = queryCaptor.value

        assertThat(query).isEqualTo("fake query")
    }
}