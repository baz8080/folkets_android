package com.mbcdev.folkets;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Provides Fabric and related kit information
 *
 * Created by barry on 15/07/2017.
 */
interface FabricProvider {

    /**
     * Returns true if Fabric is initialised
     *
     * @return true if Fabric is initialised, false otherwise
     */
    boolean isInitialised();

    /**
     * Logs an Answers event when TTS has been invoked.
     *
     * @param languageCode the language code, either "en" or "sv"
     * @param word the word that TTS was invoked with
     */
    void logTextToSpeechEvent(@NonNull String languageCode, @NonNull String word);

    /**
     * Logs an Answers event when a search has been made
     *
     * @param query the text that was searched for
     */
    void logSearchEvent(@NonNull String query);

    /**
     * Logs an Answers event when a Word is viewed
     *
     * @param context A context used to look up words
     * @param word The word to log
     */
    void logWordViewedEvent(@NonNull Context context, @NonNull Word word);
}
