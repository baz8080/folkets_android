package com.mbcdev.folkets;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.List;

/**
 * Created by barry on 22/08/2016.
 */
public class Utils {

    public static void runOnUiThread(Runnable runnable) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }

    public static boolean hasLength(String string) {
        return string != null && string.trim().length() > 0;
    }

    public static boolean isEmpty(String string) {
        return !hasLength(string);
    }

    public static String formatWordTypesForDisplay(Context context, List<WordType> wordTypes) {

        StringBuilder wordTypeBuilder = new StringBuilder();

        for (int i = 0, size = wordTypes.size(); i < size; i++) {

            String wordType = context.getString(wordTypes.get(i).getTextResourceId());
            wordTypeBuilder.append(wordType);

            if (i < size - 1) {
                wordTypeBuilder.append(", ");
            }
        }

        return wordTypeBuilder.toString();
    }
}
