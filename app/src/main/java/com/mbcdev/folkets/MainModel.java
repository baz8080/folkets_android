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
class MainModel implements MainMvp.Model {

    private static final String SWEDISH_LANGUAGE_CODE = "sv";
    private static final String ENGLISH_LANGUAGE_CODE = "en";
    private static final String BASE_LANGUAGE = "base_language";

    private FolketsDatabase database = null;
    private final SharedPreferences preferences;

    /**
     * Creates an instance of the model
     *
     * @param context A valid context
     */
    MainModel(@NonNull Context context) {
        this.database = new FolketsDatabase(context);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void search(@NonNull final String query, @NonNull final Callback<List<Word>> callback) {
        Language searchLanguage = Language.fromLanguageCode(getLanguageCode());
        database.search(searchLanguage.getTableName(), query, callback);
    }

    @Override
    public void switchBaseLanguage() {
        boolean switchBaseLanguage = preferences.getBoolean(BASE_LANGUAGE, false);
        preferences.edit().putBoolean(BASE_LANGUAGE, !switchBaseLanguage).apply();
    }

    @NonNull
    @Override
    public String getLanguageCode() {

        boolean switchBaseLanguage = preferences.getBoolean(BASE_LANGUAGE, false);

        if (Locale.getDefault().getLanguage().equalsIgnoreCase(SWEDISH_LANGUAGE_CODE)) {
            return switchBaseLanguage ? ENGLISH_LANGUAGE_CODE : SWEDISH_LANGUAGE_CODE;
        } else {
            return switchBaseLanguage ? SWEDISH_LANGUAGE_CODE : ENGLISH_LANGUAGE_CODE;
        }
    }
}
