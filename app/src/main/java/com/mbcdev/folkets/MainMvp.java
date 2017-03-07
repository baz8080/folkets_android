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
         * Initialises the presenter
         *
         * @param view the view to attach to the presenter
         * @param model the model to attach to the presenter
         */
        void init(@NonNull View view, @NonNull Model model);

        /**
         * Detaches the view from the presenter
         */
        void detach();

        /**
         * Searches the database
         *
         * @param query the query to search for
         */
        void search(@NonNull String query);

        /**
         * Called when the user has requested help
         */
        void helpRequested();
    }
}
