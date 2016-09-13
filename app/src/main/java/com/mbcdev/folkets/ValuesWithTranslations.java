package com.mbcdev.folkets;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Models a collection of {@link ValueWithTranslation}
 *
 * Created by barry on 21/08/2016.
 */
public class ValuesWithTranslations implements Parcelable {

    private static final String SEPARATOR = Pattern.quote("**");

    private final List<ValueWithTranslation> valuesWithTranslations;

    /**
     * Creates an instance from the raw database value
     *
     * @param rawValues the raw database value
     */
    public ValuesWithTranslations(String rawValues) {
        String[] values = rawValues.split(SEPARATOR);
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
    public List<ValueWithTranslation> getValuesWithTranslations() {
        return valuesWithTranslations;
    }

    protected ValuesWithTranslations(Parcel in) {
        if (in.readByte() == 0x01) {
            valuesWithTranslations = new ArrayList<>();
            in.readList(valuesWithTranslations, ValueWithTranslation.class.getClassLoader());
        } else {
            valuesWithTranslations = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (valuesWithTranslations == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(valuesWithTranslations);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ValuesWithTranslations> CREATOR = new Parcelable.Creator<ValuesWithTranslations>() {
        @Override
        public ValuesWithTranslations createFromParcel(Parcel in) {
            return new ValuesWithTranslations(in);
        }

        @Override
        public ValuesWithTranslations[] newArray(int size) {
            return new ValuesWithTranslations[size];
        }
    };
}