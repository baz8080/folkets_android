package com.mbcdev.folkets;

import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.SearchEvent;

import io.fabric.sdk.android.Fabric;

/**
 * Default implementation of FabricProvider
 *
 * Created by barry on 15/07/2017.
 */
class DefaultFabricProvider implements FabricProvider {

    private final Answers answers;
    private final EventPopulator populator;

    DefaultFabricProvider(@NonNull Answers answers, @NonNull EventPopulator populator) {
        this.answers = answers;
        this.populator = populator;
    }

    @Override
    public boolean isInitialised() {
        return Fabric.isInitialized();
    }

    @Override
    public void logTextToSpeechEvent(@NonNull String languageCode, @NonNull String phrase) {
        if (!isInitialised()) {
            return;
        }

        TtsEvent event = new TtsEvent();
        populator.ttsEvent(languageCode, phrase, event);
        answers.logCustom(event);
    }

    @Override
    public void logSearchEvent(@NonNull String query) {
        if (!isInitialised()) {
            return;
        }

        SearchEvent event = new SearchEvent();
        populator.searchEvent(query, event);
        answers.logSearch(event);
    }

    @Override
    public void logWordViewedEvent(@NonNull Context context, @NonNull Word word) {
        if (!isInitialised()) {
            return;
        }

        ContentViewEvent event = new ContentViewEvent();
        populator.contentViewEvent(context, word, event);
        answers.logContentView(event);
    }

    /**
     * Gets an instance with default implementations of Answers and EventPopulator
     *
     * @return an instance with default implementations of Answers and EventPopulator
     */
    @NonNull
    static FabricProvider instance() {
        return new DefaultFabricProvider(Answers.getInstance(), new EventPopulator());
    }
}
