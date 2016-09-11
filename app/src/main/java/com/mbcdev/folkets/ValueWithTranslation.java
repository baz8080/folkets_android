package com.mbcdev.folkets;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.regex.Pattern;

/**
 * Created by barry on 21/08/2016.
 */
public class ValueWithTranslation implements Parcelable {

    private static final String DELIMITER = Pattern.quote("||");

    private String value;
    private String translation;

    public ValueWithTranslation(@NonNull String rawValue) {
        String[] values = rawValue.split(DELIMITER);

        if (values.length == 1) {
            value = values[0].trim();
            translation = "";
        } else if (values.length == 2) {
            value = values[0].trim();
            translation = values[1].trim();
        }

    }

    public String getValue() {
        return value;
    }

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