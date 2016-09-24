package com.mbcdev.folkets;

import android.support.annotation.NonNull;

import java.util.List;

import timber.log.Timber;

/**
 * Default implementation of {@link MainMvp.Presenter}
 *
 * Created by barry on 21/08/2016.
 */
class MainPresenter implements MainMvp.Presenter {

    private MainMvp.Model model;
    private MainMvp.View view;

    @Override
    public void attachView(@NonNull MainMvp.View view) {
        this.view = view;
        model = new MainModel(view.getContext());
        view.setToolbarText(model.getLanguageCode());
        search("");
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void search(@NonNull String query) {

        if (view == null) {
            Timber.d("View is null. Will not search.");
            return;
        }

        model.search(query, new Callback<List<Word>>() {
            @Override
            public void onSuccess(List<Word> result) {
                view.showResults(result);
            }

            @Override
            public void onError(ErrorType errorType) {
                view.onError(errorType);
            }
        });
    }

    @Override
    public void switchBaseLanguage() {
        model.switchBaseLanguage();
        view.setToolbarText(model.getLanguageCode());
        search("");
    }
}
