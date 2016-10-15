package com.mbcdev.folkets;

import android.os.Handler;
import android.os.Looper;

import java.util.regex.Pattern;

/**
 * Utility methods
 *
 * Created by barry on 22/08/2016.
 */
class Utils {

    private Utils() {
        // Intentionally empty
    }

    static final String ASTERISK_SEPARATOR = Pattern.quote("**");
    static final String PIPE_SEPARATOR = Pattern.quote("||");

    /**
     * Runs a runnable on the UI thread
     *
     * @param runnable the runnable to run
     */
    static void runOnUiThread(Runnable runnable) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }
}
