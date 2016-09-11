package com.mbcdev.folkets;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by barry on 21/08/2016.
 */
public class SaldoLink implements Parcelable {

    private static final String DELIMITER = Pattern.quote("||");

    private String wordLink = "";
    private String associationsLink = "";
    private String inflectionsLink = "";

    public SaldoLink(String rawValue) {

        String[] rawLinks = rawValue.split(DELIMITER);

        if (rawLinks.length == 3) {
            wordLink = String.format(Locale.US, "https://spraakbanken.gu.se/ws/saldo-ws/fl/html/%s", rawLinks[0]);
            associationsLink = String.format(Locale.US, "https://spraakbanken.gu.se/ws/saldo-ws/lid/html/%s", rawLinks[1]);
            inflectionsLink = String.format(Locale.US, "https://spraakbanken.gu.se/ws/saldo-ws/lid/html/%s", rawLinks[2]);
        }
    }

    @Override
    public String toString() {
        return "SaldoLink{" +
                "wordLink='" + wordLink + '\'' +
                ", associationsLink='" + associationsLink + '\'' +
                ", inflectionsLink='" + inflectionsLink + '\'' +
                '}';
    }

    protected SaldoLink(Parcel in) {
        wordLink = in.readString();
        associationsLink = in.readString();
        inflectionsLink = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(wordLink);
        dest.writeString(associationsLink);
        dest.writeString(inflectionsLink);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SaldoLink> CREATOR = new Parcelable.Creator<SaldoLink>() {
        @Override
        public SaldoLink createFromParcel(Parcel in) {
            return new SaldoLink(in);
        }

        @Override
        public SaldoLink[] newArray(int size) {
            return new SaldoLink[size];
        }
    };
}