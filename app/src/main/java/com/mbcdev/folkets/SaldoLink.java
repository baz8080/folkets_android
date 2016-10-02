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

    private String wordLink = "";
    private String associationsLink = "";
    private String inflectionsLink = "";

    /**
     * Creates an instance from the raw database value
     *
     * @param rawValue the raw database value
     */
    SaldoLink(@NonNull Context context, @Nullable String rawValue) {

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

            wordLink = context.getString(R.string.link_word_format,
                    rawLinks[0], context.getString(R.string.link_word));

            associationsLink = context.getString(R.string.link_associations_format,
                    rawLinks[1], context.getString(R.string.link_associations));

            inflectionsLink = context.getString(R.string.link_inflections_format,
                    rawLinks[2], context.getString(R.string.link_inflections)
            );
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

}