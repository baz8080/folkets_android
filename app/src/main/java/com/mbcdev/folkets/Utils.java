package com.mbcdev.folkets;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Utility methods
 *
 * Created by barry on 22/08/2016.
 */
public class Utils {

    private Utils() {
        // Intentionally empty
    }

    /**
     * Runs a runnable on the UI thread. Useful for delivering results of asynchronous operations
     * @param runnable The runnable to post to the UI thread
     */
    public static void runOnUiThread(@NonNull Runnable runnable) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }

    /**
     * Checks if the string has length
     * @param string the string to check
     * @return true if the string has length, false otherwise
     */
    public static boolean hasLength(@Nullable String string) {
        return string != null && string.trim().length() > 0;
    }

    /**
     * Checks if the string is empty
     * @param string the string to check
     * @return true if the string is empty, false otherwise
     */
    public static boolean isEmpty(String string) {
        return !hasLength(string);
    }
}
