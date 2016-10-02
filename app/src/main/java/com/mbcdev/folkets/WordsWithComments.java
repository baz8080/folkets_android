package com.mbcdev.folkets;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Models a collection of words that have a comment
 *
 * Created by barry on 21/08/2016.
 */
class WordsWithComments implements Serializable {

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
}