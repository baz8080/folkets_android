package com.mbcdev.folkets;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Default implementation of {@link com.mbcdev.folkets.SearchMvp.Model}
 *
 * Created by barry on 20/08/2016.
 */
public class SearchModel implements SearchMvp.Model {

    private FolketsDatabase database = null;

    public static void get(Context context, final Callback<SearchModel> callback) {
        FolketsDatabase.create(context.getApplicationContext(), new Callback<FolketsDatabase>() {

            @Override
            public void onSuccess(FolketsDatabase result) {
                final SearchModel model = new SearchModel();
                model.database = result;

                Utils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(model);
                    }
                });

            }
        });
    }

    @Override
    public void search(@NonNull final String baseLanguage, final String query, @NonNull final Callback<List<Word>> callback) {
        database.searchInexact(baseLanguage, query, callback);
    }
}
