package com.mbcdev.folkets

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.MockitoAnnotations.initMocks


/**
 * Unit tests for [MainPresenter]
 *
 * Created by barry on 15/07/2017.
 */
class MainPresenterUnitTests {

    @Mock private lateinit var model : MainMvp.Model
    @Mock private lateinit var view : MainMvp.View

    lateinit private var presenter : MainPresenter

    @Before fun setup() {
        initMocks(this)
        presenter = MainPresenter()
        presenter.init(view, model)
    }

    @Test
    fun `init should cause no interactions on the view or the model`() {
        verifyZeroInteractions(view, model)
    }

    @Test
    fun `detach should set view to null`() {
        presenter.detach()

        assertThat(presenter.view).isNull()
    }

    @Test
    fun `detach should set model to null`() {
        presenter.detach()

        assertThat(presenter.model).isNull()
    }

    @Test
    fun `helpRequested calls showHelp`() {
        presenter.helpRequested()

        verify(view).showHelp()
    }

    @Test
    fun `search should invoke model search`() {
        presenter.search("")

        val modelSearchCaptor = ArgumentCaptor.forClass(String::class.java)
        verify(model).search(modelSearchCaptor.capture(), ArgumentMatchers.any())
        assertThat(modelSearchCaptor.value).isEqualTo("")
    }

    @Test
    fun `onWordSelected calls showWordDetail`() {
        val word = Word.getTestingWord("Skärp", "sv")
        presenter.onWordSelected(word)

        val wordCaptor = ArgumentCaptor.forClass(Word::class.java)
        verify(view).showWordDetail(wordCaptor.capture())

        assertThat(word.word).isEqualTo("Skärp")
    }

    @Test
    fun `onTtsRequested calls speak`() {
        val word = Word.getTestingWord("Hjo", "sv")
        presenter.onTtsRequested(word)

        val wordCaptor = ArgumentCaptor.forClass(Word::class.java)
        verify(view).speak(wordCaptor.capture())

        assertThat(word.word).isEqualTo("Hjo")
    }

    @Test
    fun `onTtsResult calls view is null safe`() {
        presenter.onTtsResult(null)
    }

    @Test
    fun `onTtsResult calls view on low volume error`() {
        presenter.onTtsResult(FolketsTextToSpeech.SpeechStatus.ERROR_VOLUME_TOO_LOW)
        verify(view).showLowVolumeError()
    }

    @Test
    fun `onTtsResult calls view language not supported error`() {
        presenter.onTtsResult(FolketsTextToSpeech.SpeechStatus.ERROR_LANGUAGE_NOT_SUPPORTED)
        verify(view).showLanguageNotSupportedError()
    }

    @Test
    fun `onTtsResult calls generic tts error when status is ERROR_TTS_NULL`() {
        presenter.onTtsResult(FolketsTextToSpeech.SpeechStatus.ERROR_TTS_NULL)
        val statusCaptor = ArgumentCaptor.forClass(FolketsTextToSpeech.SpeechStatus::class.java)
        verify(view).showGenericTTsError(statusCaptor.capture())
        assertThat(statusCaptor.value).isEqualTo(FolketsTextToSpeech.SpeechStatus.ERROR_TTS_NULL)
    }

    @Test
    fun `onTtsResult calls generic tts error when status is ERROR_LISTENER_NULL`() {
        presenter.onTtsResult(FolketsTextToSpeech.SpeechStatus.ERROR_LISTENER_NULL)
        val statusCaptor = ArgumentCaptor.forClass(FolketsTextToSpeech.SpeechStatus::class.java)
        verify(view).showGenericTTsError(statusCaptor.capture())
        assertThat(statusCaptor.value).isEqualTo(FolketsTextToSpeech.SpeechStatus.ERROR_LISTENER_NULL)
    }

    @Test
    fun `onTtsResult calls generic tts error when status is ERROR_TTS_NOT_READY`() {
        presenter.onTtsResult(FolketsTextToSpeech.SpeechStatus.ERROR_TTS_NOT_READY)
        val statusCaptor = ArgumentCaptor.forClass(FolketsTextToSpeech.SpeechStatus::class.java)
        verify(view).showGenericTTsError(statusCaptor.capture())
        assertThat(statusCaptor.value).isEqualTo(FolketsTextToSpeech.SpeechStatus.ERROR_TTS_NOT_READY)
    }

    @Test
    fun `onTtsResult calls generic tts error when status is ERROR_LANGUAGE_OR_PHRASE_MISSING`() {
        presenter.onTtsResult(FolketsTextToSpeech.SpeechStatus.ERROR_LANGUAGE_OR_PHRASE_MISSING)
        val statusCaptor = ArgumentCaptor.forClass(FolketsTextToSpeech.SpeechStatus::class.java)
        verify(view).showGenericTTsError(statusCaptor.capture())
        assertThat(statusCaptor.value).isEqualTo(FolketsTextToSpeech.SpeechStatus.ERROR_LANGUAGE_OR_PHRASE_MISSING)
    }

}