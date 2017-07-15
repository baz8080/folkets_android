package com.mbcdev.folkets;

import android.support.annotation.NonNull;

import com.crashlytics.android.answers.Answers;

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
}
