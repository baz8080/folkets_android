package com.mbcdev.folkets

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

/**
 * Instrument tests for [MainPresenter]
 *
 * Created by barry on 07/03/2017.
 */
@RunWith(AndroidJUnit4::class)
class MainPresenterInstrumentTests {

    @Test
    fun test_searchShouldBeNullSafe() {

        val model = Mockito.mock(MainMvp.Model::class.java)
        val view = Mockito.mock(MainMvp.View::class.java)
        val presenter = MainPresenter()

        // This test relies on a Looper. It's more reliable to run this on the main looper, rather
        // than to prepare one and call loop when messages are assumed to be present.

        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            presenter.init(view, model)
            presenter.search("plumbus")
            presenter.detach()
        }

    }
}