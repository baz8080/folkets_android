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

    private static final String SWEDISH_LANGUAGE_CODE = "sv";
    private static final String ENGLISH_LANGUAGE_CODE = "en";

    private MainMvp.Model model;
    private MainMvp.View view;

    private boolean switchMainLanguage;

    @Override
    public void attachView(MainMvp.View view) {
        this.view = view;

        if (view != null) {
            view.setToolbarText(getBaseLanguage().toUpperCase(Locale.US));
        }
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

        model.search(getBaseLanguage(), query, new Callback<List<Word>>() {
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

        MainModel.get(view.getContext().getApplicationContext(), new Callback<MainModel>() {
            @Override
            public void onResult(MainModel result) {
                model = result;
                view.enableSearch();
                view.hideProgress();
                search("");
            }
        });
    }

    @Override
    public void switchBaseLanguage() {
        switchMainLanguage = !switchMainLanguage;

        search("");
    }

    private String getBaseLanguage() {

        if (Locale.getDefault().getLanguage().equalsIgnoreCase(SWEDISH_LANGUAGE_CODE)) {
            return switchMainLanguage ? ENGLISH_LANGUAGE_CODE : SWEDISH_LANGUAGE_CODE;
        } else {
            return switchMainLanguage ? SWEDISH_LANGUAGE_CODE : ENGLISH_LANGUAGE_CODE;
        }
    }
}
