package com.mbcdev.folkets;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Models a collection of {@link ValueWithTranslation}
 *
 * Created by barry on 21/08/2016.
 */
class ValuesWithTranslations implements Serializable {

    private final List<ValueWithTranslation> valuesWithTranslations;

    /**
     * Creates an instance from the raw database value
     *
     * @param rawValues the raw database value
     */
    ValuesWithTranslations(@NonNull String rawValues) {
        String[] values = rawValues.split(Utils.ASTERISK_SEPARATOR);
        valuesWithTranslations = new ArrayList<>(values.length);

        for (String valueWithTranslation : values) {
            if (valueWithTranslation.length() != 0) {
                valuesWithTranslations.add(new ValueWithTranslation(valueWithTranslation));
            }
        }
    }

    @Override
    public String toString() {
        return "ValuesWithTranslations{" +
                "valuesWithTranslations=" + valuesWithTranslations +
                '}';
    }

    /**
     * Gets the list of {@link ValueWithTranslation}
     *
     * @return the list of {@link ValueWithTranslation}
     */
     @NonNull List<ValueWithTranslation> getValuesWithTranslations() {
        return valuesWithTranslations;
    }
}