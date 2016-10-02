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
class FolketsDatabase {

    private static final String FOLKETS_DB = "folkets.db";
    private final Context context;

    private SQLiteDatabase database;
    private final SharedPreferences preferences;

    /**
     * Creates an instance of the SQLiteOpenHelper. Intentionally private
     *
     * @param context A valid context
     */
    FolketsDatabase(@NonNull Context context) {
        this.context = context.getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        synchronized (this) {
            initialiseDatabase(context);
        }
    }

    /**
     * Searches the database for
     *
     * @param tableName The table name to run the query in.
     * @param query The query. This will be appended with % to get inexact matches
     * @param callback The callback used to deliver the results.
     */
    void search(
            @NonNull final String tableName, @NonNull final String query,
            @NonNull final Callback<List<Word>> callback) {

        if (this.database == null) {
            d("The database is null.");
            callback.onError(ErrorType.DATABASE_NULL);
            return;
        }

        execute(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = database.query(
                        tableName, null, "word like ?",
                        new String[] { query + "%" }, null, null, "word asc limit 0,100");

                d("Number of results %s", cursor.getCount());
                final List<Word> words = new ArrayList<>();

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    words.add(new Word(context, cursor));
                    cursor.moveToNext();
                }

                cursor.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(words);
                    }
                });
            }
        });
    }

    /**
     * Initialises the database.
     * <p>
     *     The database will be copied from raw resources to the main storage if:
     *     <ol>
     *         <li>The database does not exist in main storage</li>
     *         <li>The database in raw resources is different to the one in main storage</li>
     *     </ol>
     * </p>
     * <p>
     *     If the database already exists in main storage, then its checksum is compared to the
     *     checksum of the one in raw storage. If the checksums do not match, then the raw
     *     resource is copied to main memory.
     * </p>
     *
     * @param context A valid context
     */
    private void initialiseDatabase(@NonNull Context context) {
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

    /**
     * Gets an instance of the database.
     *
     * @param file The file, used to get the path to the database
     * @return An instance of the database
     */
    private SQLiteDatabase getDatabase(@NonNull File file) {
        return SQLiteDatabase.openDatabase(
                file.getPath(), null,
                SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * Copies the database from the raw resources to the main storage
     *
     * @param context A valid context
     * @param file The file to write to
     */
    private void copyDbToStorage(@NonNull Context context, @NonNull File file) {
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
}

