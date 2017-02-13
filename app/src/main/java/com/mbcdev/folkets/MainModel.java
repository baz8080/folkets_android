package com.mbcdev.folkets;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Default implementation of the {@link MainMvp.Model}
 *
 * Created by barry on 20/08/2016.
 */
class MainModel implements MainMvp.Model {

    private FolketsDatabase database = null;

    /**
     * Creates an instance of the model
     *
     * @param context A valid context
     */
    MainModel(@NonNull Context context) {
        this.database = new FolketsDatabase(context);
    }

    @Override
    public void search(@NonNull final String query, @NonNull final Callback<List<Word>> callback) {
        database.search(query, callback);
    }
}
