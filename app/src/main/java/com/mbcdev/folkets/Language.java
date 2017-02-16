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
    ENGLISH("en"),
    SWEDISH("sv");

    private final String code;
    private final Locale locale;

    /**
     * Initialises the enum constant with the specified values
     *
     * @param code The two letter code of the language
     */
    Language(String code) {
        this.code = code;
        this.locale = new Locale(code);
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
     * Gets the language as a locale
     *
     * @return the language as a locale
     */
    Locale getLocale() {
        return locale;
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
