package com.mbcdev.folkets

import com.crashlytics.android.answers.Answers
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Tests for [MainModel]
 *
 * Created by barry on 15/07/2017.
 */
class MainModelTests {

    private var callback = object : Callback<List<Word>> {
        override fun onSuccess(result: List<Word>?) {
            // Intentionally empty
        }

        override fun onError(errorType: ErrorType?) {
            // Intentionally empty
        }
    }

    @Mock private lateinit var database: FolketsDatabase
    @Mock private lateinit var answers: Answers
    @Mock private lateinit var populator: EventPopulator

    private lateinit var fabricProvider: FabricProvider

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        fabricProvider = spy(DefaultFabricProvider(answers, populator))
    }

    @Test
    fun `model search should call database search`() {
        val model = MainModel(database, fabricProvider)

        model.search("Barn", callback)

        verify(database).search(anyString(), any())
    }

    @Test
    fun `logSearchEvent is called when a search is made`() {
        `when`(fabricProvider.isInitialised).thenReturn(true)
        val model = MainModel(database, fabricProvider)

        model.search("Barn", callback)

        val queryCaptor = ArgumentCaptor.forClass(String::class.java)
        verify(fabricProvider).logSearchEvent(queryCaptor.capture())
        assertThat(queryCaptor.value).isEqualTo("Barn")
    }

}