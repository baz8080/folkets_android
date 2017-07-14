package com.mbcdev.folkets

import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.SearchEvent
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
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onError(errorType: ErrorType?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    @Mock private lateinit var database: FolketsDatabase
    @Mock private lateinit var fabricProvider: FabricProvider
    @Mock private lateinit var answers: Answers

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(fabricProvider.answers).thenReturn(answers)
    }

    @Test
    fun `model search should call database search`() {
        val model = MainModel(database, fabricProvider)

        model.search("Barn", callback)

        verify(database).search(anyString(), any())
    }

    @Test
    fun `answers is not called when Fabric is not initialised`() {
        `when`(fabricProvider.isInitialised).thenReturn(false)
        val model = MainModel(database, fabricProvider)

        model.search("Barn", callback)

        verifyZeroInteractions(answers)

    }

    @Test
    fun `answers is called when Fabric is initialised`() {
        `when`(fabricProvider.isInitialised).thenReturn(true)
        val model = MainModel(database, fabricProvider)

        model.search("Barn", callback)

        verify(fabricProvider).answers

        val searchEventCaptor = ArgumentCaptor.forClass(SearchEvent::class.java)
        verify(answers).logSearch(searchEventCaptor.capture())

        // Can't test for much more than this
        assertThat(searchEventCaptor).isNotNull()
    }

}