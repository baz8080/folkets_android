package com.mbcdev.folkets;

import android.support.annotation.NonNull;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

import io.fabric.sdk.android.Fabric;

/**
 * Default implementation of FabricProvider
 *
 * Created by barry on 15/07/2017.
 */
class DefaultFabricProvider implements FabricProvider {

    private final Answers answers;

    DefaultFabricProvider(Answers answers) {
        this.answers = answers;
    }

    @Override
    public boolean isInitialised() {
        return Fabric.isInitialized();
    }

    @NonNull
    @Override
    public Answers getAnswers() {
        return answers;
    }

    @Override
    public void logTextToSpeechEvent(@NonNull String languageCode, @NonNull String phrase) {
        if (!isInitialised()) {
            return;
        }

        CustomEvent textToSpeechEvent = new CustomEvent("TTS")
                .putCustomAttribute("Language", languageCode)
                .putCustomAttribute("Phrase", phrase);

        answers.logCustom(textToSpeechEvent);
    }
}
