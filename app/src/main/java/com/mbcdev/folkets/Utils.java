package com.mbcdev.folkets;

import android.os.Handler;
import android.os.Looper;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Utility methods
 *
 * Created by barry on 22/08/2016.
 */
public class Utils {

    private Utils() {
        // Intentionally empty
    }

    static final String ASTERISK_SEPARATOR = Pattern.quote("**");
    static final String PIPE_SEPARATOR = Pattern.quote("||");
    static final String EMPTY_STRING = "";

    /**
     * Runs a runnable on the UI thread
     *
     * @param runnable the runnable to run
     */
    static void runOnUiThread(Runnable runnable) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }

    /**
     * Checks if a string has length or not. Length does not include leading whitespace.
     *
     * @param string The string to check
     * @return true if the string has length, false otherwise
     */
    static boolean hasLength(String string) {
        return string != null && string.trim().length() > 0;
    }

    /**
     * Checks if a string is empty or not. Emptiness does not include leading whitespace.
     *
     * @param string The string to check
     * @return true if the string is empty, false otherwise
     */
    static boolean isEmpty(String string) {
        return !hasLength(string);
    }

    /**
     * Checks if a collection is empty or not.
     *
     * @param collection The collection to check
     * @return true if the collection is empty, false otherwise
     */
    static boolean isEmpty(Collection collection) {
        return collection != null && collection.size() == 0;
    }
}
