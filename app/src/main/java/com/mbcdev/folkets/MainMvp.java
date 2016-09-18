package com.mbcdev.folkets;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * MVP interfaces for {@link MainActivity}
 *
 * Created by barry on 20/08/2016.
 */
public interface MainMvp {

    /**
     * Defines model operations
     */
    interface Model {
        /**
         * Searches the model for words and definitions
         *
         * @param query The query to make
         * @param callback The callback used to deliver results
         */
        void search(String query, Callback<List<Word>> callback);

        void switchBaseLangauge();

        String getBaseLanguage();
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

        /**
         * Switches the base language. If the device has an swedish locale, then this will
         * switch the app to search the english database.
         */
        void switchBaseLanguage();
    }
}
