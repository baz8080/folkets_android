package com.mbcdev.folkets;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import static android.os.AsyncTask.execute;
import static com.mbcdev.folkets.Utils.runOnUiThread;
import static timber.log.Timber.d;
import static timber.log.Timber.e;

/**
 * Wraps the preexisting database in a {@link SQLiteOpenHelper}
 *
 * Created by barry on 20/08/2016.
 */
public class FolketsDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String FOLKETS_DB = "folkets.db";

    /**
     * Creates an instance of the SQLiteOpenHelper. Intentionally private
     *
     * @param context A valid context
     * @param path The path to the folkets database
     */
    private FolketsDatabase(Context context, String path) {
        super(context, path, null, VERSION);
    }

    /**
     * Factory method to create an instance of a {@link FolketsDatabase}.
     *
     * @param context A valid Context
     * @param callback The callback which will deliver an instance of the database
     */
    public static void create(final Context context, final Callback<FolketsDatabase> callback) {

        final File file = new File(context.getFilesDir(), FOLKETS_DB);

        if (file.exists()) {
            d("Database file already exists.");
            callback.onResult(new FolketsDatabase(context, file.getPath()));
            return;
        }

        d("Database file does not exist, so will copy to storage");
        final InputStream inputstream = context.getResources().openRawResource(R.raw.folkets);

        execute(new Runnable() {
            @Override
            public void run() {
                try (Source a = Okio.source(inputstream); BufferedSink b = Okio.buffer(Okio.sink(file))) {
                    b.writeAll(a);
                    callback.onResult(new FolketsDatabase(context, file.getPath()));
                } catch (final IOException e) {
                    e(e);
                    callback.onResult(null);
                }
            }
        });
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Intentionally empty
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Intentionally empty
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
        execute(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = getReadableDatabase().query(
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
