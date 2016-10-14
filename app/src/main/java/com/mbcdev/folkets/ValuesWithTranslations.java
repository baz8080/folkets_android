package com.mbcdev.folkets;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Models a collection of {@link ValueWithTranslation}
 *
 * Created by barry on 21/08/2016.
 */
class ValuesWithTranslations implements Serializable {

    private final List<ValueWithTranslation> valuesWithTranslations = new ArrayList<>();

    /**
     * Creates an instance from the raw database value
     *
     * @param rawValues the raw database value
     */
    ValuesWithTranslations(String rawValues) {

        if (Utils.isEmpty(rawValues)) {
            Timber.d("RawValues is null, cannot continue.");
            return;
        }

        String[] values = rawValues.split(Utils.ASTERISK_SEPARATOR);

        for (String value : values) {
            if (Utils.hasLength(value)) {
                ValueWithTranslation valueWithTranslation = new ValueWithTranslation(value);

                if (Utils.hasLength(valueWithTranslation.getValue())) {
                    valuesWithTranslations.add(valueWithTranslation);
                }
            }
        }
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