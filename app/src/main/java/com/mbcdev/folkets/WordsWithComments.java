package com.mbcdev.folkets;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zendesk.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

/**
 * Models a collection of words that have a comment
 *
 * Created by barry on 21/08/2016.
 */
class WordsWithComments implements Serializable {

    private static final String WORD_ONLY_FORMAT = "%s:\t%s";
    private static final String WORD_AND_COMMENT_FORMAT = "%s:\t%s (%s)";

    private final List<String> words;

    WordsWithComments(@Nullable String rawValues) {

        words = new ArrayList<>();

        if (StringUtils.isEmpty(rawValues)) {
            Timber.d("Raw value is empty, returning.");
            return;
        }

        String[] values = rawValues.split(Utils.ASTERISK_SEPARATOR);

        int wordNumber = 0;

        for (String wordWithComment : values) {

            String[] components = wordWithComment.split(Utils.PIPE_SEPARATOR);

            if (components.length > 0) {

                String word = components[0].trim();
                String comment = components.length == 2 ? components[1].trim() : null;

                if (StringUtils.hasLength(word)) {
                    wordNumber++;

                    String wordToAdd = (StringUtils.isEmpty(comment)) ?
                            String.format(Locale.US, WORD_ONLY_FORMAT, wordNumber, word) :
                            String.format(Locale.US, WORD_AND_COMMENT_FORMAT, wordNumber, word, comment);

                    words.add(wordToAdd);
                }
            }
        }
    }

    /**
     * Gets the list of words and comments
     *
     * @return the list of words and comments
     */
    @NonNull List<String> getWords() {
        return words;
    }

    /**
     * Gets the words as a string separated by newlines
     *
     * @return the words as a string separated by newlines, or the empty string if there are none.
     */
    @NonNull String getWordsFormattedForDisplay() {
        return Utils.listToString(words);
    }
}