package com.mbcdev.folkets;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Default implementation of the {@link MainMvp.Model}
 *
 * Created by barry on 20/08/2016.
 */
class MainModel implements MainMvp.Model {

    private final FabricProvider fabricProvider;
    private final FolketsDatabase database;

    /**
     * Creates an instance of the model
     *
     * @param database An instance of FolketsDatabase
     */
    MainModel(@NonNull FolketsDatabase database, @NonNull FabricProvider fabricProvider) {
        this.database = database;
        this.fabricProvider = fabricProvider;
    }

    @Override
    public void search(@NonNull final String query, @NonNull final Callback<List<Word>> callback) {
        database.search(query, callback);
        fabricProvider.logSearchEvent(query);
    }
}

