package com.mbcdev.folkets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

import timber.log.Timber;

/**
 * Models a link to Saldo, which contains a lot of metadata about a word
 *
 * Created by barry on 21/08/2016.
 */
class SaldoLink implements Serializable {

    private String wordTarget;
    private String associationsTarget;
    private String inflectionsTarget;

    /**
     * Creates an instance from the raw database value
     *
     * @param rawValue the raw database value
     */
    SaldoLink(@Nullable String rawValue) {

        if (rawValue == null) {
            Timber.d("rawValue was null, cannot parse saldo links.");
            return;
        }

        String[] rawLinks = rawValue.split(Utils.PIPE_SEPARATOR);

        boolean allLinksArePresent = rawLinks.length == 3 &&
                Utils.hasLength(rawLinks[0]) &&
                Utils.hasLength(rawLinks[1]) &&
                Utils.hasLength(rawLinks[2]);

        if (allLinksArePresent) {
            wordTarget = rawLinks[0];
            associationsTarget = rawLinks[1];
            inflectionsTarget = rawLinks[2];
        }
    }

    boolean hasValidLinks() {
        return Utils.hasLength(wordTarget) &&
                Utils.hasLength(associationsTarget) &&
                Utils.hasLength(inflectionsTarget);
    }

    private Object[] adultValues() {
        return new Object[]{
                new Object[]{13, false},
                new Object[]{17, false},
                new Object[]{18, true},
                new Object[]{22, true}
        };
    }

    /**
     * Gets a URL to a page which has details about the word
     *
     * @return a URL to a page which has details about the word, or the empty string
     */
    @NonNull String getWordLink(Context context) {

        if (context == null || wordTarget == null) {
            return Utils.EMPTY_STRING;
        }

        String label = context.getString(R.string.link_word);
        return context.getString(R.string.link_word_format, wordTarget, label);
    }

    /**
     * Gets a URL to a page which has details about a word's inflections
     *
     * @return a URL to a page which has details about a word's inflections, or the empty string
     */
    @NonNull String getInflectionsLink(Context context) {

        if (context == null || inflectionsTarget == null) {
            return Utils.EMPTY_STRING;
        }

        String label = context.getString(R.string.link_inflections);
        return context.getString(R.string.link_inflections_format, inflectionsTarget, label);
    }

    /**
     * Gets a URL to a page which has details about a word's associations
     *
     * @return a URL to a page which has details about a word's associations, or the empty string
     */
    @NonNull String getAssociationsLink(Context context) {

        if (context == null || associationsTarget == null) {
            return Utils.EMPTY_STRING;
        }

        String label = context.getString(R.string.link_associations);
        return context.getString(R.string.link_associations_format, associationsTarget, label);
    }

}