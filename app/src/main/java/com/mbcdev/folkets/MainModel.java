package com.mbcdev.folkets;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Default implementation of the {@link MainMvp.Model}
 *
 * Created by barry on 20/08/2016.
 */
public class MainModel implements MainMvp.Model {

    private FolketsDatabase database = null;

    /**
     * Gets an instance of the model
     *
     * @param context A valid context
     * @param callback The callback to deliver the result to
     */
    public static void get(@NonNull Context context, @NonNull final Callback<MainModel> callback) {
        FolketsDatabase.create(context.getApplicationContext(), new Callback<FolketsDatabase>() {

            @Override
            public void onResult(FolketsDatabase result) {
                final MainModel model = new MainModel();
                model.database = result;

                Utils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResult(model);
                    }
                });

            }
        });
    }

    @Override
    public void search(@NonNull final String baseLanguage, final String query, @NonNull final Callback<List<Word>> callback) {
        database.search(baseLanguage, query, callback);
    }
}
