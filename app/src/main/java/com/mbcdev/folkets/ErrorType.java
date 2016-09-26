package com.mbcdev.folkets;

import android.support.annotation.StringRes;

/**
 * Models error types
 *
 * Created by barry on 24/09/2016.
 */
enum ErrorType {
    DATABASE_NULL(R.string.error_database_null);

    private final int stringResourceId;

    /**
     * Configures the enum constant with the correct error message
     *
     * @param stringResourceId The resourceId of the string for this ErrorType
     */
    ErrorType(@StringRes int stringResourceId) {
        this.stringResourceId = stringResourceId;
    }

    /**
     * Gets the resource ID of the string for this ErrorType
     *
     * @return
     */
    int getStringResourceId() {
        return stringResourceId;
    }
}
