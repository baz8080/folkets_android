package com.mbcdev.folkets;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Locale;

import timber.log.Timber;

/**
 * Default implementation of {@link com.mbcdev.folkets.SearchMvp.Presenter}
 *
 * Created by barry on 21/08/2016.
 */
public class SearchPresenter implements SearchMvp.Presenter {

    private static final String SWEDISH_LANGUAGE_CODE = "sv";
    private static final String ENGLISH_LANGUAGE_CODE = "en";

    private SearchMvp.Model model;
    private SearchMvp.View view;

    @Override
    public void attachView(SearchMvp.View view) {
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
            public void onSuccess(List<Word> result) {
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

        SearchModel.get(view.getContext().getApplicationContext(), new Callback<SearchModel>() {
            @Override
            public void onSuccess(SearchModel result) {
                model = result;
                view.enableSearch();
                view.hideProgress();
                search("");
            }
        });
    }

    /**
     * Gets the base language based on the device locale
     *
     * @return "sv" if the device's language is Swedish, "en" otherwise
     */
    private String getBaseLanguage() {

        if (Locale.getDefault().getLanguage().equalsIgnoreCase(SWEDISH_LANGUAGE_CODE)) {
            return SWEDISH_LANGUAGE_CODE;
        } else {
            return ENGLISH_LANGUAGE_CODE;
        }
    }
}
