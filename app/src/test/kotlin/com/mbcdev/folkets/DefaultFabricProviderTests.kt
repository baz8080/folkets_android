package com.mbcdev.folkets

import android.content.Context
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.crashlytics.android.answers.CustomEvent
import com.crashlytics.android.answers.SearchEvent
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Tests for [DefaultFabricProvider]
 *
 * Created by barry on 30/07/2017.
 */
class DefaultFabricProviderTests {

    @Mock private lateinit var answers: Answers
    @Mock private lateinit var populator: EventPopulator

    private lateinit var fabricProvider: FabricProvider

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        fabricProvider = spy(DefaultFabricProvider(answers, populator))
    }

    @Test
    fun `logSearchEvent does not invoke Answers when Fabric is not initialised`() {
        `when`(fabricProvider.isInitialised).thenReturn(false)

        fabricProvider.logSearchEvent("orange")

        verifyZeroInteractions(answers, populator)
    }

    @Test
    fun `logSearchEvent invokes Answers when Fabric is initialised`() {
        `when`(fabricProvider.isInitialised).thenReturn(true)

        fabricProvider.logSearchEvent("banana")

        val queryCaptor = ArgumentCaptor.forClass(String::class.java)
        val populatorEventCaptor = ArgumentCaptor.forClass(SearchEvent::class.java)
        val eventCaptor = ArgumentCaptor.forClass(SearchEvent::class.java)

        verify(populator).searchEvent(queryCaptor.capture(), populatorEventCaptor.capture())
        assertThat(queryCaptor.value).isEqualTo("banana")
        assertThat(populatorEventCaptor.value).isNotNull()

        verify(answers).logSearch(eventCaptor.capture())
        assertThat(eventCaptor.value).isNotNull()

        assertThat(populatorEventCaptor.value).isSameAs(eventCaptor.value)
    }

    @Test
    fun `logTextToSpeechEvent does not invoke Answers when Fabric is not initialised`() {
        `when`(fabricProvider.isInitialised).thenReturn(false)

        fabricProvider.logTextToSpeechEvent("en", "exit")

        verifyZeroInteractions(answers, populator)
    }

    @Test
    fun `logTextToSpeechEvent invokes Answers when Fabric is initialised`() {
        `when`(fabricProvider.isInitialised).thenReturn(true)

        fabricProvider.logTextToSpeechEvent("en", "exit")

        val eventCaptor = ArgumentCaptor.forClass(CustomEvent::class.java)
        verify(answers).logCustom(eventCaptor.capture())
        val eventValue = eventCaptor.value
        assertThat(eventValue).isNotNull()

        val populatorEventCaptor = ArgumentCaptor.forClass(TtsEvent::class.java)
        val languageCodeCaptor = ArgumentCaptor.forClass(String::class.java)
        val phraseCaptor = ArgumentCaptor.forClass(String::class.java)
        verify(populator).ttsEvent(languageCodeCaptor.capture(), phraseCaptor.capture(),
                populatorEventCaptor.capture())

        assertThat(languageCodeCaptor.value).isEqualTo("en")
        assertThat(phraseCaptor.value).isEqualTo("exit")
        assertThat(populatorEventCaptor.value).isNotNull()

        assertThat(populatorEventCaptor.value).isSameAs(eventCaptor.value)
    }

    @Test
    fun `logWordViewedEvent does not invoke Answers when Fabric is not initialised`() {
        `when`(fabricProvider.isInitialised).thenReturn(false)
        val context = mock(Context::class.java)
        val word = mock(Word::class.java)

        fabricProvider.logWordViewedEvent(context, word)

        verifyZeroInteractions(answers, populator)
    }

    @Test
    fun `logWordViewedEvent invokes Answers when Fabric is initialised`() {
        `when`(fabricProvider.isInitialised).thenReturn(true)
        val context = mock(Context::class.java)
        val word = mock(Word::class.java)

        fabricProvider.logWordViewedEvent(context, word)

        val populatorEventCaptor = ArgumentCaptor.forClass(ContentViewEvent::class.java)
        val contextCaptor = ArgumentCaptor.forClass(Context::class.java)
        val wordCaptor = ArgumentCaptor.forClass(Word::class.java)

        verify(populator).contentViewEvent(contextCaptor.capture(), wordCaptor.capture(),
                populatorEventCaptor.capture())
        assertThat(contextCaptor.value).isNotNull()
        assertThat(wordCaptor.value).isNotNull()
        assertThat(populatorEventCaptor.value).isNotNull()

        val eventCaptor = ArgumentCaptor.forClass(ContentViewEvent::class.java)
        verify(answers).logContentView(eventCaptor.capture())
        assertThat(eventCaptor.value).isNotNull()

        assertThat(contextCaptor.value).isSameAs(context)
        assertThat(wordCaptor.value).isSameAs(word)
        assertThat(eventCaptor.value).isSameAs(populatorEventCaptor.value)
    }
}