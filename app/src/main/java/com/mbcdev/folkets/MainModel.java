package com.mbcdev.folkets;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.Locale;

/**
 * Default implementation of the {@link MainMvp.Model}
 *
 * Created by barry on 20/08/2016.
 */
public class MainModel implements MainMvp.Model {

    private static final String SWEDISH_LANGUAGE_CODE = "sv";
    private static final String ENGLISH_LANGUAGE_CODE = "en";

    private FolketsDatabase database = null;
    private SharedPreferences preferences;

    /**
     * Creates an instance of the model
     *
     * @param context A valid context
     */
    public MainModel(Context context) {
        this.database = new FolketsDatabase(context);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void search(final String query, @NonNull final Callback<List<Word>> callback) {
        database.search(getBaseLanguage(), query, callback);
    }

    @Override
    public void switchBaseLangauge() {
        boolean switchBaseLanguage = preferences.getBoolean("base_language", false);
        preferences.edit().putBoolean("base_language", !switchBaseLanguage).apply();
    }

    @Override
    public String getBaseLanguage() {

        boolean switchBaseLanguage = preferences.getBoolean("base_language", false);

        if (Locale.getDefault().getLanguage().equalsIgnoreCase(SWEDISH_LANGUAGE_CODE)) {
            return switchBaseLanguage ? ENGLISH_LANGUAGE_CODE : SWEDISH_LANGUAGE_CODE;
        } else {
            return switchBaseLanguage ? SWEDISH_LANGUAGE_CODE : ENGLISH_LANGUAGE_CODE;
        }
    }
}
