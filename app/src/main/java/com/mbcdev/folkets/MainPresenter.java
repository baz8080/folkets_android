package com.mbcdev.folkets;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Locale;

import timber.log.Timber;

/**
 * Default implementation of {@link MainMvp.Presenter}
 *
 * Created by barry on 21/08/2016.
 */
public class MainPresenter implements MainMvp.Presenter {

    private MainMvp.Model model;
    private MainMvp.View view;

    @Override
    public void attachView(MainMvp.View view) {
        this.view = view;
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
            public void onResult(List<Word> result) {
                view.showResults(result);
            }
        });
    }

    @Override
    public void initialiseData() {

        if (view == null) {
            Timber.d("The data cannot be initialised if the view is null");
            return;
        }

        view.disableSearch();
        view.showProgress();

        model = new MainModel(view.getContext());
        view.enableSearch();
        view.hideProgress();
        view.setToolbarText(model.getBaseLanguage());
        search("");
    }

    @Override
    public void switchBaseLanguage() {
        model.switchBaseLangauge();
        view.setToolbarText(model.getBaseLanguage());
        search("");
    }
}
