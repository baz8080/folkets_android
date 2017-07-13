package com.mbcdev.folkets

import android.speech.tts.TextToSpeech
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*
import java.util.*
import kotlin.collections.HashMap

/**
 * Tests for [FolketsTextToSpeech]
 *
 * Created by barry on 09/07/2017.
 */
class FolketsTextToSpeechTests {

    @Captor
    lateinit var paramsCaptor : ArgumentCaptor<HashMap<String, String>>

    @Mock
    lateinit var mockedTts : TextToSpeech

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `speech does not work if the underlying TTS is null`() {
        val folketsTts = FolketsTextToSpeech(null, null)

        val ttsStatus = folketsTts.speak(null, null)

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.ERROR_TTS_NULL)
    }

    @Test
    fun `speech does not work if the underying TTS has no onInit listener`() {
        val folketsTts = FolketsTextToSpeech(mockedTts, null)

        val ttsStatus = folketsTts.speak(null, null)

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.ERROR_LISTENER_NULL)
        verifyZeroInteractions(mockedTts)
    }

    @Test
    fun `speech does not work if the onInit listener is not ready`() {
        val realListener = FolketsTextToSpeechInitListener()
        val folketsTts = FolketsTextToSpeech(mockedTts, realListener)

        val ttsStatus = folketsTts.speak(null, null)

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.ERROR_TTS_NOT_READY)
        verifyZeroInteractions(mockedTts)
    }

    @Test
    fun `speech does not work if the language and phrase are missing`() {
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
        val realListener = FolketsTextToSpeechInitListener()
        realListener.onInit(TextToSpeech.SUCCESS)
        val folketsTts = FolketsTextToSpeech(mockedTts, realListener)

        val ttsStatus = folketsTts.speak(Language.ENGLISH, "")

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.ERROR_LANGUAGE_OR_PHRASE_MISSING)
        verifyZeroInteractions(mockedTts)
    }

    @Test
    fun `speech takes language from input when its current language is null`() {
        `when`(mockedTts.language).thenReturn(null)

        val realListener = FolketsTextToSpeechInitListener()
        realListener.onInit(TextToSpeech.SUCCESS)
        val folketsTts = FolketsTextToSpeech(mockedTts, realListener)

        val ttsStatus = folketsTts.speak(Language.ENGLISH, "Ireland")

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.SUCCESS)

        val localeCaptor = ArgumentCaptor.forClass(Locale::class.java)
        verify(mockedTts, atMost(1)).language = localeCaptor.capture()

        val locale = localeCaptor.value
        assertThat(locale).isEqualTo(Language.ENGLISH.locale)
    }

    @Test
    fun `speech uses previous language when it matches the requested language`() {
        `when`(mockedTts.language).thenReturn(Language.SWEDISH.locale)

        val realListener = FolketsTextToSpeechInitListener()
        realListener.onInit(TextToSpeech.SUCCESS)
        val folketsTts = FolketsTextToSpeech(mockedTts, realListener)

        val ttsStatus = folketsTts.speak(Language.SWEDISH, "Sverige")

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.SUCCESS)

        verify(mockedTts, atMost(0)).language = ArgumentMatchers.any()
    }

    @Test
    fun `speech switches language when it does not match the requested language`() {
        `when`(mockedTts.language).thenReturn(Language.SWEDISH.locale)

        val realListener = FolketsTextToSpeechInitListener()
        realListener.onInit(TextToSpeech.SUCCESS)
        val folketsTts = FolketsTextToSpeech(mockedTts, realListener)

        val ttsStatus = folketsTts.speak(Language.ENGLISH, "Dublin")

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.SUCCESS)

        val localeCaptor = ArgumentCaptor.forClass(Locale::class.java)
        verify(mockedTts, atMost(1)).language = localeCaptor.capture()

        val locale = localeCaptor.value
        assertThat(locale).isEqualTo(Language.ENGLISH.locale)
    }

    @Test
    fun `speak is called with the correct arguments`() {
        `when`(mockedTts.language).thenReturn(Language.SWEDISH.locale)

        val realListener = FolketsTextToSpeechInitListener()
        realListener.onInit(TextToSpeech.SUCCESS)
        val folketsTts = FolketsTextToSpeech(mockedTts, realListener)

        val ttsStatus = folketsTts.speak(Language.SWEDISH, "Göteborg")

        assertThat(ttsStatus).isNotNull()
        assertThat(ttsStatus).isEqualTo(FolketsTextToSpeech.FolketsSpeechStatus.SUCCESS)

        val phraseCaptor = ArgumentCaptor.forClass(String::class.java)
        val queueModeCaptor = ArgumentCaptor.forClass(Int::class.java)

        verify(mockedTts, atMost(1)).speak(
                phraseCaptor.capture(), queueModeCaptor.capture(), paramsCaptor.capture())

        val phrase = phraseCaptor.value
        assertThat(phrase).isEqualTo("Göteborg")

        val queueMode = queueModeCaptor.value
        assertThat(queueMode).isEqualTo(TextToSpeech.QUEUE_FLUSH)

        val params = paramsCaptor.value
        assertThat(params).isNull()
    }
}