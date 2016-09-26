package com.mbcdev.folkets;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Locale;

import timber.log.Timber;

/**
 * Models a language
 *
 * Created by barry on 24/09/2016.
 */
enum Language {
    ENGLISH("folkets_en_sv", "en"),
    SWEDISH("folkets_sv_en", "sv");

    private final String tableName;
    private final String code;

    /**
     * Initialises the enum constant with the specified values
     *
     * @param tableName The table name that this language's data is in
     * @param code The two letter code of the language
     */
    Language(String tableName, String code) {
        this.tableName = tableName;
        this.code = code;
    }

    /**
     * Gets the language code
     *
     * @return the language code
     */
    String getCode() {
        return code;
    }

    /**
     * Gets the table name
     *
     * @return the table name
     */
    String getTableName() {
        return tableName;
    }

    /**
     * Finds a language from the given language code
     *
     * @param code The language code to look for
     * @return The language code, or ENGLISH if not found
     */
    @NonNull static Language fromLanguageCode(@Nullable String code) {

        if (code == null) {
            return Language.ENGLISH;
        }

        String lowercaseCode = code.toLowerCase(Locale.US);

        if (ENGLISH.getCode().equals(lowercaseCode)) {
            return ENGLISH;
        } else if (SWEDISH.getCode().equals(lowercaseCode)) {
            return SWEDISH;
        } else {
            Timber.d("Code unknown or invalid. Returning ENGLISH");
            return ENGLISH;
        }
    }
}
