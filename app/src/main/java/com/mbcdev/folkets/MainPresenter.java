package com.mbcdev.folkets;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Default implementation of {@link MainMvp.Presenter}
 *
 * Created by barry on 21/08/2016.
 */
class MainPresenter implements MainMvp.Presenter {

    private MainMvp.Model model;
    private MainMvp.View view;

    @Override
    public void init(@NonNull MainMvp.View view, @NonNull MainMvp.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void detach() {
        this.view = null;
        this.model = null;
    }

    @Override
    public void search(@NonNull final String query) {
        if (view != null && model != null) {
            model.search(query, new SearchCallback(view));
        }
    }

    @Override
    public void helpRequested() {
        if (view != null) {
            view.showHelp();
        }
    }

    @Override
    public void onWordSelected(Word word) {
        if (view != null) {
            view.showWordDetail(word);
        }
    }

    @Override
    public void onTtsRequested(Word word) {
        if (view != null) {
            view.speak(word);
        }
    }

    @Override
    public void onTtsResult(FolketsTextToSpeech.SpeechStatus status) {
        if (view != null && status != null) {

            switch (status) {

                case ERROR_TTS_NULL:
                case ERROR_LISTENER_NULL:
                case ERROR_TTS_NOT_READY:
                case ERROR_LANGUAGE_OR_PHRASE_MISSING:
                    view.showGenericTTsError(status);
                    break;
                case ERROR_LANGUAGE_NOT_SUPPORTED:
                    view.showLanguageNotSupportedError();
                    break;
                case ERROR_VOLUME_TOO_LOW:
                    view.showLowVolumeError();
                    break;
                case SUCCESS:
                    break;
            }

        }
    }

    @VisibleForTesting
    static class SearchCallback implements Callback<List<Word>> {

        private final WeakReference<MainMvp.View> viewReference;

        SearchCallback(MainMvp.View view) {
            this.viewReference = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(List<Word> result) {
            MainMvp.View view = viewReference.get();

            if (view != null) {
                view.showResults(result);
            }
        }

        @Override
        public void onError(ErrorType errorType) {
            MainMvp.View view = viewReference.get();

            if (view != null) {
                view.onError(errorType);
            }
        }
    }

    @VisibleForTesting
    MainMvp.View getView() {
        return view;
    }

    @VisibleForTesting
    MainMvp.Model getModel() {
        return model;
    }
}