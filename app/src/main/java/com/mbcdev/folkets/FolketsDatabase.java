package com.mbcdev.folkets;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okio.BufferedSink;
import okio.HashingSource;
import okio.Okio;

import static android.os.AsyncTask.execute;
import static com.mbcdev.folkets.Utils.runOnUiThread;
import static timber.log.Timber.d;
import static timber.log.Timber.e;

/**
 * Wraps the preexisting database in a {@link SQLiteOpenHelper}
 *
 * Created by barry on 20/08/2016.
 */
public class FolketsDatabase {

    private static final String FOLKETS_DB = "folkets.db";
    private SQLiteDatabase database;
    private SharedPreferences preferences;

    /**
     * Creates an instance of the SQLiteOpenHelper. Intentionally private
     *
     * @param context A valid context
     */
    public FolketsDatabase(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        synchronized (this) {
            initialiseDatabase(context);
        }
    }

    /**
     * Searches the database for
     *
     * @param baseLanguage "en" for an English to Swedish search. "sv" for a Swedish to English
     *                     search // TODO enum
     * @param query The query. This will be appended with % to get inexact matches
     * @param callback The callback used to deliver the results.
     */
    public void search(@NonNull final String baseLanguage, final String query, @NonNull final Callback<List<Word>> callback) {

        if (this.database == null) {
            callback.onResult(new ArrayList<Word>());
            return;
        }

        execute(new Runnable() {
            @Override
            public void run() {

                Cursor cursor = database.query(
                        getTableName(baseLanguage), null, "word like ?",
                        new String[] { query + "%" }, null, null, "word asc limit 0,100");

                d("Number of results %s", cursor.getCount());
                final List<Word> words = new ArrayList<>();

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    words.add(new Word(cursor));
                    cursor.moveToNext();
                }

                cursor.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResult(words);
                    }
                });
            }
        });
    }

    private void initialiseDatabase(Context context) {
        d("initialiseDatabase: Start");

        final File file = new File(context.getFilesDir(), FOLKETS_DB);

        if (file.exists()) {
            d("initialiseDatabase: DB already exists.");
            String storedHash = preferences.getString("db_hash", "");

            try (InputStream inputStream = context.getResources().openRawResource(R.raw.folkets);
                 HashingSource hashingSource = HashingSource.md5(Okio.source(inputStream))) {

                Okio.buffer(hashingSource).readAll(Okio.blackhole());
                String currentHash = hashingSource.hash().hex();

                d("initialiseDatabase: Stored hash %s, current DB hash %s", storedHash, currentHash);

                if (!storedHash.equals(currentHash)) {
                    d("initialiseDatabase: New database detected");
                    copyDbToStorage(context, file);
                } else {
                    d("initialiseDatabase: Database is unchanged");
                    database = getDatabase(file);
                }

            } catch (IOException e) {
                e(e, "initialiseDatabase: Error computing hash for file %s", file.getPath());
            }


        } else {
            d("initialiseDatabase: DB does not exist.");
            copyDbToStorage(context, file);
        }
    }

    private SQLiteDatabase getDatabase(File file) {
        return SQLiteDatabase.openDatabase(
                file.getPath(), null,
                SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READONLY);
    }

    private void copyDbToStorage(Context context, File file) {
        d("copyDbToStorage: Start");

        InputStream inputStream = context.getResources().openRawResource(R.raw.folkets);

        try (
                HashingSource hashingSource = HashingSource.md5(Okio.source(inputStream));
                BufferedSink bufferedSink = Okio.buffer(Okio.sink(file))) {

            bufferedSink.writeAll(hashingSource);

            String hash = hashingSource.hash().hex();
            d("copyDbToStorage: Saving hash of db as %s", hash);
            preferences.edit().putString("db_hash", hash).apply();

            database = getDatabase(file);

        } catch (final IOException e) {
            e(e, "copyDbToStorage: Error copying database.");
        }
    }

    /**
     * Gets the table name for the given base language
     *
     * @param baseLanguage The base language, "en" or "sv" //TODO enum
     * @return The name of the table to search
     */
    private String getTableName(@NonNull String baseLanguage) {
        if ("en".equals(baseLanguage)) {
            return "folkets_en_sv";
        } else {
            return "folkets_sv_en";
        }
    }
}

