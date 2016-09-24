package com.mbcdev.folkets;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Models a collection of words that have a comment
 *
 * Created by barry on 21/08/2016.
 */
class WordsWithComments implements Parcelable {

    private final List<String> words;

    WordsWithComments(@NonNull String rawValues) {
        String[] values = rawValues.split(Utils.ASTERISK_SEPARATOR);
        words = new ArrayList<>(values.length);

        int wordNumber = 0;

        for (String wordWithComment : values) {

            String[] components = wordWithComment.split(Utils.PIPE_SEPARATOR);

            if (components.length > 0) {

                wordNumber++;

                if (components.length == 1) {
                    String word = components[0].trim();
                    words.add(String.format(Locale.US, "%s:\t%s", wordNumber, word));
                } else if (components.length == 2) {
                    String word = components[0].trim();
                    String comment = components[1].trim();
                    words.add(String.format(Locale.US, "%s:\t%s (%s)", wordNumber, word, comment));
                }
            }
        }
    }

    /**
     * Gets the list of words and comments
     *
     * @return the list of words and comments
     */
    List<String> getWords() {
        return words;
    }

    @Override
    public String toString() {
        return "WordsWithComments{" +
                "words=" + words +
                '}';
    }


    /////////////////////////////////////////
    //  And now, for the parcelable crap!  //
    /////////////////////////////////////////

    WordsWithComments(Parcel in) {
        if (in.readByte() == 0x01) {
            words = new ArrayList<>();
            in.readList(words, String.class.getClassLoader());
        } else {
            words = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (words == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(words);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<WordsWithComments> CREATOR = new Parcelable.Creator<WordsWithComments>() {
        @Override
        public WordsWithComments createFromParcel(Parcel in) {
            return new WordsWithComments(in);
        }

        @Override
        public WordsWithComments[] newArray(int size) {
            return new WordsWithComments[size];
        }
    };
}