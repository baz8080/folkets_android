package com.mbcdev.folkets

import android.widget.LinearLayout
import android.widget.TextView
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Tests for [WordActivity.SectionLinearLayout]
 *
 * Created by barry on 17/07/2017.
 */
class SectionLinearLayoutTests {

    @Mock private lateinit var linearLayoutMock : LinearLayout

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `required widgets can be found in the linear layout`() {
        WordActivity.SectionLinearLayout(linearLayoutMock, null, null)

        verify(linearLayoutMock).findViewById<TextView>(R.id.include_word_section_title)
        verify(linearLayoutMock).findViewById<TextView>(R.id.include_word_section_content)
    }

    @Test
    fun `setText on titleTextView is called when it is not null`() {
        val titleTextViewMock = mock(TextView::class.java)
        `when`(linearLayoutMock.findViewById<TextView>(R.id.include_word_section_title))
                .thenReturn(titleTextViewMock)

        WordActivity.SectionLinearLayout(linearLayoutMock, "Title", null)

        val textCaptor = ArgumentCaptor.forClass(String::class.java)
        verify(titleTextViewMock).text = textCaptor.capture()
        assertThat(textCaptor.value).isEqualTo("Title")
    }

    @Test
    fun `setText on titleTextView is null safe`() {
        `when`(linearLayoutMock.findViewById<TextView>(R.id.include_word_section_title))
                .thenReturn(null)

        WordActivity.SectionLinearLayout(linearLayoutMock, "Title", null)
    }

    @Test
    fun `setText on contentTextView is called when it is not null`() {
        val contentTextViewMock = mock(TextView::class.java)
        `when`(linearLayoutMock.findViewById<TextView>(R.id.include_word_section_content))
                .thenReturn(contentTextViewMock)

        WordActivity.SectionLinearLayout(linearLayoutMock, null, "content")

        val textCaptor = ArgumentCaptor.forClass(String::class.java)
        verify(contentTextViewMock).text = textCaptor.capture()
        assertThat(textCaptor.value).isEqualTo("content")
    }

    @Test
    fun `setText on contentTextView is null safe`() {
        `when`(linearLayoutMock.findViewById<TextView>(R.id.include_word_section_content))
                .thenReturn(null)

        WordActivity.SectionLinearLayout(linearLayoutMock, null, "content")
    }

}