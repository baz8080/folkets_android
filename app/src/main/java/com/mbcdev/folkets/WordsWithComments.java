package com.mbcdev.folkets;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Models a collection of words that have a comment
 *
 * Created by barry on 21/08/2016.
 */
public class WordsWithComments implements Parcelable {

    private final List<String> words;

    public WordsWithComments(String rawValues) {
        String[] values = rawValues.split(Utils.ASTERISK_SEPARATOR);
        words = new ArrayList<>(values.length);

        int wordNumber = 0;

        for (String wordWithComment : values) {

            wordNumber++;

            String[] components = wordWithComment.split(Utils.PIPE_SEPARATOR);

            String word = components[0].trim();
            String comment = "";

            if (components.length == 2) {
                comment = components[1].trim();
            }

            if (Utils.hasLength(word)) {
                words.add(String.format(Locale.US, "%s:\t%s", wordNumber, components[0].trim()));
            } else if (Utils.hasLength(comment)) {
                words.add(
                        String.format(Locale.US, "%s:\t%s (%s)",
                                wordNumber, components[0].trim(), components[1].trim())
                );
            }
        }
    }

    public List<String> getWords() {
        return words;
    }

    @Override
    public String toString() {
        return "WordsWithComments{" +
                "words=" + words +
                '}';
    }

    protected WordsWithComments(Parcel in) {
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