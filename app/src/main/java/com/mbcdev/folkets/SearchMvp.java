package com.mbcdev.folkets;

import android.content.Context;

import java.util.List;

/**
 * Created by barry on 20/08/2016.
 */
public interface SearchMvp {

    interface Model {
        void search(String baseLanguage, String query, Callback<List<Word>> callback);
    }

    interface View {
        void showResults(List<Word> words);
        void onSearchError();
        void setToolbarText(String text);
        void showProgress();
        void disableSearch();
        void hideProgress();
        void enableSearch();
        Context getContext();
    }

    interface Presenter {
        void attachView(View view);
        void initialiseData();
        void detachView();
        void search(String query);
    }
}
