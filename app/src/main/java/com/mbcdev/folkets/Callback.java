package com.mbcdev.folkets;

/**
 * Defines a callback used to deliver results asyncronously
 *
 * Created by barry on 20/08/2016.
 */
public interface Callback<T> {
    void onSuccess(T result);
}
