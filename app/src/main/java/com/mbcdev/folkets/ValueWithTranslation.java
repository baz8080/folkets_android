package com.mbcdev.folkets;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Models a value that has a translation
 *
 * Created by barry on 21/08/2016.
 */
public class ValueWithTranslation implements Parcelable {

    private String value;
    private String translation;

    /**
     * Creates an instance using the raw value from the database
     *
     * @param rawValue The raw value from the database
     */
    public ValueWithTranslation(@NonNull String rawValue) {
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
     * @return the value of this value
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the translation of this value
     *
     * @return the translation of this value
     */
    public String getTranslation() {
        return translation;
    }

    @Override
    public String toString() {
        return "ValueWithTranslation{" +
                "value='" + value + '\'' +
                ", translation='" + translation + '\'' +
                '}';
    }

    protected ValueWithTranslation(Parcel in) {
        value = in.readString();
        translation = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
        dest.writeString(translation);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ValueWithTranslation> CREATOR = new Parcelable.Creator<ValueWithTranslation>() {
        @Override
        public ValueWithTranslation createFromParcel(Parcel in) {
            return new ValueWithTranslation(in);
        }

        @Override
        public ValueWithTranslation[] newArray(int size) {
            return new ValueWithTranslation[size];
        }
    };
}