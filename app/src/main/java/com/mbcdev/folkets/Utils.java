package com.mbcdev.folkets;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.zendesk.util.CollectionUtils;
import com.zendesk.util.StringUtils;

import java.util.List;
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

    @NonNull
    static String listToString(List list) {

        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY_STRING;
        }

        StringBuilder sb = new StringBuilder();

        for (Object object : list) {
            sb.append(object.toString()).append("\n");
        }

        return sb.subSequence(0, sb.length() - 1).toString();
    }
}
