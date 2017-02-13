package com.mbcdev.folkets;

import android.support.annotation.NonNull;

import com.zendesk.util.StringUtils;

import java.io.Serializable;

/**
 * Models a value that has a translation
 *
 * Created by barry on 21/08/2016.
 */
class ValueWithTranslation implements Serializable {

    private String value = "";
    private String translation = "";

    /**
     * Creates an instance using the raw value from the database
     *
     * @param rawValue The raw value from the database
     */
    ValueWithTranslation(String rawValue) {

        if (StringUtils.isEmpty(rawValue)) {
            return;
        }

        String[] values = rawValue.split(Utils.PIPE_SEPARATOR);

        if (values.length == 1) {
            value = values[0].trim();
            translation = "";
        } else if (values.length == 2) {
            value = values[0].trim();
            translation = values[1].trim();
        }
    }

    /**
     * Gets the value of this value
     *
     * @return the value of this value, or the empty string
     */
    @NonNull String getValue() {
        return value;
    }

    /**
     * Gets the translation of this value
     *
     * @return the translation of this value, or the empty string
     */
    @NonNull String getTranslation() {
        return translation;
    }
}