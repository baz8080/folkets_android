package com.mbcdev.folkets

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import java.util.ArrayList


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
        val word = Word.getTestingWord("Skärp")
        presenter.onWordSelected(word)

        val wordCaptor = ArgumentCaptor.forClass(Word::class.java)
        verify(view).showWordDetail(wordCaptor.capture())

        assertThat(word.word).isEqualTo("Skärp")
    }

    @Test
    fun `onTtsRequested calls speak`() {
        val word = Word.getTestingWord("Hjo")
        presenter.onTtsRequested(word)

        val wordCaptor = ArgumentCaptor.forClass(Word::class.java)
        verify(view).speak(wordCaptor.capture())

        assertThat(word.word).isEqualTo("Hjo")
    }
}