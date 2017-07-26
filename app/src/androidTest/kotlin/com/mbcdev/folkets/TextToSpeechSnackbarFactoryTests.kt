package com.mbcdev.folkets

import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests for [TextToSpeechSnackbarFactory]
 *
 * Created by barry on 25/07/2017.
 */
@RunWith(AndroidJUnit4::class)
class TextToSpeechSnackbarFactoryTests {

    lateinit var view : CoordinatorLayout

    @Before
    fun setup() {
        InstrumentationRegistry.getTargetContext().setTheme(R.style.AppTheme)
        view = CoordinatorLayout(InstrumentationRegistry.getTargetContext())
    }

    @Test
    fun genericErrorSnackbarShouldHaveExpectedBasicValues() {
        val snackbar = TextToSpeechSnackbarFactory.genericErrorSnackbar(view)

        assertThat(snackbar.duration).isEqualTo(Snackbar.LENGTH_LONG)
        assertThat(snackbar.isShown).isFalse()

        val snackbarLayout = snackbar.view

        val snackbarTextView = snackbarLayout.findViewById<TextView>(R.id.snackbar_text)
        assertThat(snackbarTextView).isNotNull()
        assertThat(snackbarTextView.text).isEqualTo("Oops, the speech didn't work!")

        val snackbarAction = snackbarLayout.findViewById<Button>(R.id.snackbar_action)
        assertThat(snackbarAction).isNotNull()
        assertThat(snackbarAction.hasOnClickListeners()).isFalse()
        assertThat(snackbarAction.visibility).isEqualTo(View.GONE)
    }

    @Test
    fun languageNotSupportedSnackbarShouldHaveExpectedBasicValues() {
        val snackbar = TextToSpeechSnackbarFactory.languageNotSupportedSnackbar(
                view, InstrumentationRegistry.getTargetContext())

        assertThat(snackbar.duration).isEqualTo(Snackbar.LENGTH_INDEFINITE)
        assertThat(snackbar.isShown).isFalse()

        val snackbarLayout = snackbar.view

        val snackbarTextView = snackbarLayout.findViewById<TextView>(R.id.snackbar_text)
        assertThat(snackbarTextView).isNotNull()
        assertThat(snackbarTextView.text).isEqualTo("Oops, the speech didn't work!")

        val snackbarAction = snackbarLayout.findViewById<Button>(R.id.snackbar_action)
        assertThat(snackbarAction).isNotNull()
        assertThat(snackbarAction.hasOnClickListeners()).isTrue()
        assertThat(snackbarAction.text).isEqualTo("Fix it")
        assertThat(snackbarAction.visibility).isEqualTo(View.VISIBLE)
    }

    @Test
    fun lowVolumeSnackbarShouldHaveExpectedBasicValues() {
        val snackbar = TextToSpeechSnackbarFactory.lowVolumeSnackbar(
                view, InstrumentationRegistry.getTargetContext())

        assertThat(snackbar.duration).isEqualTo(Snackbar.LENGTH_LONG)
        assertThat(snackbar.isShown).isFalse()

        val snackbarLayout = snackbar.view

        val snackbarTextView = snackbarLayout.findViewById<TextView>(R.id.snackbar_text)
        assertThat(snackbarTextView).isNotNull()
        assertThat(snackbarTextView.text).isEqualTo("Oops, The volume is too low!")

        val snackbarAction = snackbarLayout.findViewById<Button>(R.id.snackbar_action)
        assertThat(snackbarAction).isNotNull()
        assertThat(snackbarAction.hasOnClickListeners()).isTrue()
        assertThat(snackbarAction.text).isEqualTo("Audio Settings")
        assertThat(snackbarAction.visibility).isEqualTo(View.VISIBLE)
    }
}