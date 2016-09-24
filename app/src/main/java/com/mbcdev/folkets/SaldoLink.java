package com.mbcdev.folkets;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * Models a link to Saldo, which contains a lot of metadata about a word
 *
 * Created by barry on 21/08/2016.
 */
class SaldoLink implements Parcelable {

    private String wordLink = "";
    private String associationsLink = "";
    private String inflectionsLink = "";

    /**
     * Creates an instance from the raw database value
     *
     * @param rawValue the raw database value
     */
    SaldoLink(@NonNull String rawValue) {

        String[] rawLinks = rawValue.split(Utils.PIPE_SEPARATOR);

        if (rawLinks.length == 3) {

            String label = MainApplication.getInstance().getString(R.string.link_word);
            wordLink = String.format(Locale.US, "<a href=\"https://spraakbanken.gu.se/ws/saldo-ws/fl/html/%s\">%s</a>", rawLinks[0], label);

            label = MainApplication.getInstance().getString(R.string.link_associations);
            associationsLink = String.format(Locale.US, "<a href=\"https://spraakbanken.gu.se/ws/saldo-ws/lid/html/%s\">%s</a>", rawLinks[1], label);

            label = MainApplication.getInstance().getString(R.string.link_inflections);
            inflectionsLink = String.format(Locale.US, "<a href=\"https://spraakbanken.gu.se/ws/saldo-ws/lid/html/%s\">%s</a>", rawLinks[2], label);
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

    boolean hasValidLinks() {
        return Utils.hasLength(wordLink) &&
                Utils.hasLength(associationsLink) &&
                Utils.hasLength(inflectionsLink);
    }

    /**
     * Gets a URL to a page which has details about the word
     *
     * @return a URL to a page which has details about the word, or the empty string
     */
    @NonNull String getWordLink() {
        return wordLink;
    }

    /**
     * Gets a URL to a page which has details about a word's inflections
     *
     * @return a URL to a page which has details about a word's inflections, or the empty string
     */
    @NonNull String getInflectionsLink() {
        return inflectionsLink;
    }

    /**
     * Gets a URL to a page which has details about a word's associations
     *
     * @return a URL to a page which has details about a word's associations, or the empty string
     */
    @NonNull String getAssociationsLink() {
        return associationsLink;
    }


    /////////////////////////////////////////
    //  And now, for the parcelable crap!  //
    /////////////////////////////////////////

    SaldoLink(Parcel in) {
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