package com.mbcdev.folkets

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Tests for [FolketsPreferencesStorage]
 *
 * Created by barry on 10/08/2017.
 */
@SuppressLint("CommitPrefEdits")
class FolketsPreferenceStorageTests {

    private lateinit var preferences: SharedPreferences
    private lateinit var storage: FolketsPreferencesStorage
    private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setup() {
        preferences = mock(SharedPreferences::class.java)

        editor = mock(SharedPreferences.Editor::class.java)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)
        `when`(preferences.edit()).thenReturn(editor)

        storage = FolketsPreferencesStorage(preferences)
    }

    @Test
    fun `getHash returns the correct value`() {
        `when`(preferences.getString("db_hash", "")).thenReturn("ABCDEFG")

        val hash = storage.getDatabaseHash()
        assertThat(hash).isEqualTo("ABCDEFG")
    }


    @Test
    fun `setHash stores the correct value`() {
        storage.setDatabaseHash("QWERTY")

        val inOrder = inOrder(preferences, editor)

        inOrder.verify(preferences).edit()
        inOrder.verify(editor).putString("db_hash", "QWERTY")
        inOrder.verify(editor).apply()
    }
}