package com.mbcdev.folkets

import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Tests for [MainPresenter.SearchCallback]
 *
 * Created by barry on 27/03/2017.
 */
class SearchCallbackTests {

    @Test
    fun `The callback's onSuccess method should be null-safe when the view is null from the outset`() {
        val callback = MainPresenter.SearchCallback(null)
        callback.onSuccess(emptyList())
    }

    @Test
    fun `The callback's onError method should be null-safe when the view is null from the outset`() {
        val callback = MainPresenter.SearchCallback(null)
        callback.onError(ErrorType.DATABASE_NULL)
    }

    @Test
    fun `The callback's onSuccess method should be null-safe when the view becomes null after creation`() {
        var view = mock(MainMvp.View::class.java)
        val callback = MainPresenter.SearchCallback(view)

        @Suppress("UNUSED_VALUE")
        view = null

        System.gc()
        callback.onSuccess(emptyList())
    }

    @Test
    fun `The callback's onError method should be null-safe when the view becomes null after creation`() {
        var view = mock(MainMvp.View::class.java)
        val callback = MainPresenter.SearchCallback(view)

        @Suppress("UNUSED_VALUE")
        view = null

        System.gc()
        callback.onError(ErrorType.DATABASE_NULL)
    }

    @Test
    fun `The callback's onSuccess method should be null-safe when the results are null`() {
        val view = mock(MainMvp.View::class.java)
        val callback = MainPresenter.SearchCallback(view)
        callback.onSuccess(null)
    }

    @Test
    fun `The callback's onError method should be null-safe when the errorType is null`() {
        val view = mock(MainMvp.View::class.java)
        val callback = MainPresenter.SearchCallback(view)
        callback.onError(null)
    }
}