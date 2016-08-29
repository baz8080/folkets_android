package com.mbcdev.folkets;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * MVP interfaces for {@link SearchActivity}
 *
 * Created by barry on 20/08/2016.
 */
public interface SearchMvp {

    /**
     * Defines model operations
     */
    interface Model {
        void search(String baseLanguage, String query, Callback<List<Word>> callback);
    }

    /**
     * Defines view operations.
     */
    interface View {

        /**
         * Shows results from a search
         *
         * @param words The words to show
         */
        void showResults(@NonNull List<Word> words);

        /**
         * Shows an error encountered when searching
         */
        void onSearchError();

        /**
         * Sets the toolbar text
         * @param text The text to set
         */
        void setToolbarText(@NonNull String text);

        /**
         * Shows a progress indicator
         */
        void showProgress();

        /**
         * Disables the search component
         */
        void disableSearch();

        /**
         * Hides the progress indicator
         */
        void hideProgress();

        /**
         * Enables the search component
         */
        void enableSearch();

        /**
         * Gets the Context
         *
         * @return the Context
         */
        Context getContext();
    }

    /**
     * Defines presenter operations
     */
    interface Presenter {
        /**
         * Attaches the view to the presenter
         *
         * @param view the view to attach
         */
        void attachView(View view);

        /**
         * Initialises the data for searching
         */
        void initialiseData();

        /**
         * Detaches the view from the presenter
         */
        void detachView();

        /**
         * Searches the database
         *
         * @param query the query to search for
         */
        void search(@NonNull String query);
    }
}
