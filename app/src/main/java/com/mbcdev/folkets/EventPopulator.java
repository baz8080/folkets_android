package com.mbcdev.folkets;

import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.SearchEvent;

import java.util.Locale;

/**
 * A populating class for events which the app will send to Answers
 *
 * Created by barry on 31/07/2017.
 */
class EventPopulator {

    private static final String CONTENT_TYPES_ATTRIBUTE_KEY = "Types";

    /**
     * Populates the given ContentViewEvent with values from the given word
     *
     * @param context A context used to fetch string resources
     * @param word The word to create a ContentViewEvent for
     * @param eventOut The event to write values into
     */
    void contentViewEvent(
            @NonNull Context context, @NonNull Word word, @NonNull ContentViewEvent eventOut) {
        eventOut.putContentName(word.getWord());
        eventOut.putContentType(String.format(Locale.US, "%s word", word.getSourceLanguage()));
        eventOut.putCustomAttribute(
                CONTENT_TYPES_ATTRIBUTE_KEY,
                WordType.formatWordTypesForDisplay(context, word.getWordTypes()));
    }

    /**
     * Populates the given SearchEvent with the given word
     *
     * @param query The query to create a SearchEvent for
     * @param eventOut The event to write values into
     */
    void searchEvent(@NonNull String query, @NonNull SearchEvent eventOut) {
        eventOut.putQuery(query);
    }

    /**
     * Populates the TtsEvent with the given phrase and language code
     *
     * @param languageCode The language code of the phrase that was spoken
     * @param phrase The phrase that was spoken
     * @param eventOut The event to writes values into
     */
    void ttsEvent(@NonNull String languageCode, @NonNull String phrase, TtsEvent eventOut) {
        eventOut.putCustomAttribute("Language", languageCode);
        eventOut.putCustomAttribute("Phrase", phrase);
    }
}
