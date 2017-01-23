package com.mbcdev.folkets;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * MVP interfaces for {@link MainActivity}
 *
 * Created by barry on 20/08/2016.
 */
interface MainMvp {

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
        void search(@NonNull String query, @NonNull Callback<List<Word>> callback);

        /**
         * Switches the current language from "en" to "sv", or from "sv" to "en".
         */
        void switchBaseLanguage();

        /**
         * Gets the preferred language of the user, will either be "en", or "sv"
         *
         * @return "en" if the user prefers the English to Swedish dictionary, or "sv" if the
         *         user prefers the Swedish to English dictionary
         */
        @NonNull String getLanguageCode();

        /**
         * Shows an in-product message, if available.
         */
        void showIpm();

        /**
         * Cancels an in-product message, making it ineligible for display.
         */
        void cancelIpm();
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
         *
         * @param error The error type
         */
        void onError(@NonNull ErrorType error);

        /**
         * Sets the toolbar text
         * @param text The text to set
         */
        void setToolbarText(@NonNull String text);

        /**
         * Gets the Context
         *
         * @return the Context
         */
        @NonNull Context getContext();

        /**
         * Called when help (Zendesk) should be shown
         */
        void showHelp();
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
        void attachView(@NonNull View view);

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

        /**
         * Called when the user has requested help
         */
        void helpRequested();

        /**
         * Called when an in-product message has been requested. If an IPM is available, it will
         * be shown at the bottom of the screen.
         */
        void showIpmRequested();

        /**
         * Called when we don't want to show an in-product message any more.
         */
        void cancelIpmRequested();
    }
}
