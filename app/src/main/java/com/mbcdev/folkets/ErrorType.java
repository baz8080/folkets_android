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

    ErrorType(@StringRes int stringResourceId) {
        this.stringResourceId = stringResourceId;
    }

    int getStringResourceId() {
        return stringResourceId;
    }
}
