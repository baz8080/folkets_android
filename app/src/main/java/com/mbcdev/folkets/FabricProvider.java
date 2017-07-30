package com.mbcdev.folkets;

import android.support.annotation.NonNull;

import com.crashlytics.android.answers.Answers;

/**
 * Provides Fabric and related kit information
 *
 * Created by barry on 15/07/2017.
 */
interface FabricProvider {

    /**
     *
     * @return true if Fabric is initialised, false otherwise
     */
    boolean isInitialised();

    /**
     * Gets the instance of Answers
     *
     * @return an instance of Answers
     */
    @NonNull Answers getAnswers();

    void logTextToSpeechEvent(@NonNull String languageCode, @NonNull String word);
}
