package com.mbcdev.folkets;

import android.support.annotation.Nullable;

/**
 * Defines a callback used to wrap asynchronous operations
 *
 * Created by barry on 20/08/2016.
 */
public interface Callback<T> {

    /**
     * Called when a result is available
     *
     * @param result The result, may be null
     */
    void onResult(@Nullable T result);
}
