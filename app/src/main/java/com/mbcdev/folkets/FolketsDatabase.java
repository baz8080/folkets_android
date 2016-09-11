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
 * Created by barry on 20/08/2016.
 */
public class FolketsDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String FOLKETS_DB = "folkets.db";

    private FolketsDatabase(Context context, String path) {
        super(context, path, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Intentionally empty
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Intentionally empty
    }

    public static void create(final Context context, final Callback<FolketsDatabase> callback) {

        final File file = new File(context.getFilesDir(), FOLKETS_DB);

        if (file.exists()) {
            d("Database file already exists.");
            callback.onSuccess(new FolketsDatabase(context, file.getPath()));
            return;
        }

        d("Database file does not exist, so will copy to storage");
        final InputStream inputstream = context.getResources().openRawResource(R.raw.folkets);

        execute(new Runnable() {
            @Override
            public void run() {
                try (Source a = Okio.source(inputstream); BufferedSink b = Okio.buffer(Okio.sink(file))) {
                    b.writeAll(a);
                    callback.onSuccess(new FolketsDatabase(context, file.getPath()));
                } catch (final IOException e) {
                    e(e);
                    callback.onSuccess(null); // FIXME
                }
            }
        });
    }

    public void search(@NonNull final String baseLanguage, final String query, @NonNull final Callback<List<Word>> callback) {
        execute(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = getReadableDatabase().query(
                        getDatabaseName(baseLanguage), null, "word like ?",
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
                        callback.onSuccess(words);
                    }
                });
            }
        });
    }

    private String getDatabaseName(@NonNull String baseLanguage) {
        if ("en".equals(baseLanguage)) {
            return "folkets_en_sv";
        } else {
            return "folkets_sv_en";
        }
    }
}
