package com.mbcdev.folkets

import android.content.SharedPreferences

/**
 * This class offers preferences based storage for the app.
 *
 * @constructor Creates a preferences based storage for the app.
 */
internal class FolketsPreferencesStorage(private val preferences: SharedPreferences) {

    private val HASH_KEY_NAME = "db_hash"

    /**
     * Gets the stored database hash
     *
     * @return the stored database hash, or the empty string if there is none
     */
    fun getDatabaseHash(): String {
        return preferences.getString(HASH_KEY_NAME, "")
    }

    /**
     * Stores the hash of the database
     *
     * @param hash The hash to store
     */
    fun setDatabaseHash(hash: String) {
        preferences.edit().putString(HASH_KEY_NAME, hash).apply()
    }
}