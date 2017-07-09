package com.mbcdev.folkets

import android.speech.tts.TextToSpeech
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.Mockito.mock

/**
 * Tests for [FolketsTextToSpeech]
 *
 * Created by barry on 09/07/2017.
 */
class FolketsTextToSpeechTests {

    @Test
    fun `speech does not work if the underlying TTS is null`() {
        val folketsTts = FolketsTextToSpeech(null, null)

        val ttsStatus = folketsTts.speak(null, null)

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.ERROR_TTS_NULL)
    }

    @Test
    fun `speech does not work if the underying TTS has no onInit listener`() {
        val mockedTts = mock(TextToSpeech::class.java)
        val folketsTts = FolketsTextToSpeech(mockedTts, null)

        val ttsStatus = folketsTts.speak(null, null)

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.ERROR_LISTENER_NULL)
        verifyZeroInteractions(mockedTts)
    }

    @Test
    fun `speech does not work if the onInit listener is not ready`() {
        val mockedTts = mock(TextToSpeech::class.java)
        val realListener = FolketsTextToSpeechInitListener()
        val folketsTts = FolketsTextToSpeech(mockedTts, realListener)

        val ttsStatus = folketsTts.speak(null, null)

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.ERROR_TTS_NOT_READY)
        verifyZeroInteractions(mockedTts)
    }

    @Test
    fun `speech does not work if the language and phrase are missing`() {
        val mockedTts = mock(TextToSpeech::class.java)
        val realListener = FolketsTextToSpeechInitListener()
        realListener.onInit(TextToSpeech.SUCCESS)
        val folketsTts = FolketsTextToSpeech(mockedTts, realListener)

        val ttsStatus = folketsTts.speak(null, null)

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.ERROR_LANGUAGE_OR_PHRASE_MISSING)
        verifyZeroInteractions(mockedTts)
    }

    @Test
    fun `speech does not work if the language is missing`() {
        val mockedTts = mock(TextToSpeech::class.java)
        val realListener = FolketsTextToSpeechInitListener()
        realListener.onInit(TextToSpeech.SUCCESS)
        val folketsTts = FolketsTextToSpeech(mockedTts, realListener)

        val ttsStatus = folketsTts.speak(null, "foto")

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.ERROR_LANGUAGE_OR_PHRASE_MISSING)
        verifyZeroInteractions(mockedTts)
    }

    @Test
    fun `speech does not work if the phrase is missing`() {
        val mockedTts = mock(TextToSpeech::class.java)
        val realListener = FolketsTextToSpeechInitListener()
        realListener.onInit(TextToSpeech.SUCCESS)
        val folketsTts = FolketsTextToSpeech(mockedTts, realListener)

        val ttsStatus = folketsTts.speak(Language.ENGLISH, "")

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.ERROR_LANGUAGE_OR_PHRASE_MISSING)
        verifyZeroInteractions(mockedTts)
    }
}