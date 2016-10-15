package com.mbcdev.folkets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.zendesk.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Models the types of words in the database
 *
 * Created by barry on 21/08/2016.
 */
enum WordType {
    NOUN("nn", R.string.word_type_noun),
    ADJECTIVE("jj", R.string.word_type_adjective),
    PRONOUN("pn", R.string.word_type_pronoun),
    PRONOUN_DETERMINER("hp", R.string.word_type_pronoun_determiner),
    PRONOUN_POSSESSIVE("ps", R.string.word_type_pronoun_possessive),
    PROPER_NOUN("pm", R.string.word_type_proper_noun),
    VERB("vb", R.string.word_type_verb),
    ADVERB("ab", R.string.word_type_adverb),
    PREFIX("prefix", R.string.word_type_prefix),
    SUFFIX("suffix", R.string.word_type_suffix),
    ARTICLE("article", R.string.word_type_article),
    ABBREVIATION("abbrev", R.string.word_type_abbreviation),
    PREPOSITION("pp", R.string.word_type_preposition),
    INTERJECTION("in", R.string.word_type_interjection),
    CARDINAL_NUMBER("rg", R.string.word_type_cardinal_number),
    CONJUNCTION("kn", R.string.word_type_conjunction),
    INFINITIVAL_MARKER("ie", R.string.word_type_infinitivial),
    SUBORDINATING_CONJUNCTION("sn", R.string.word_type_subordinating_conjunction),
    AUXILIARY_VERB("hjälpverb", R.string.word_type_auxiliary_verb),
    ORDINAL_NUMBER("ro", R.string.word_type_ordinal),
    LATIN("latin", R.string.word_type_latin),
    PARTICIPLE("pc", R.string.word_type_participle),
    UNKNOWN("", R.string.word_type_unknown);

    private final String rawType;

    private static final Map<String, WordType> cache = new HashMap<>(values().length);
    private final int textResourceId;

    /**
     * Initialises the WordType
     *
     * @param rawType The raw database value
     * @param textResourceId The string resource id describing the WordType
     */
    WordType(@NonNull String rawType, @StringRes int textResourceId) {
        this.rawType = rawType;
        this.textResourceId = textResourceId;
    }

    /**
     * Looks up a WordType from it's raw database value
     *
     * @param rawType The raw database value
     * @return The WordType if found, or {@link WordType#UNKNOWN} if unknown
     */
    @NonNull public static WordType lookup(@Nullable String rawType) {

        if (rawType == null) {
            return UNKNOWN;
        }

        String trimmedRawType = rawType.trim();

        if (cache.containsKey(trimmedRawType)) {
            return cache.get(trimmedRawType);
        }

        for (WordType wordType : values()) {
            if (wordType.rawType.equals(trimmedRawType)) {
                cache.put(trimmedRawType, wordType);
                return wordType;
            }
        }

        return UNKNOWN;
    }

    /**
     * Gets the string resource id of the string describing the WordType
     *
     * @return the string resource id of the string describing the WordType
     */
    @StringRes int getTextResourceId() {
        return textResourceId;
    }

    /**
     * Formats word types for display
     *
     * @param context A context used to resolve strings
     * @param wordTypes The types of words to format
     * @return A formatted string containing the string representations of the words
     */
    @NonNull public static String formatWordTypesForDisplay(
            @Nullable Context context, @Nullable List<WordType> wordTypes) {

        if (context == null || wordTypes == null) {
            return StringUtils.EMPTY_STRING;
        }

        StringBuilder wordTypeBuilder = new StringBuilder();

        for (int i = 0, size = wordTypes.size(); i < size; i++) {

            String wordType = context.getString(wordTypes.get(i).getTextResourceId());
            wordTypeBuilder.append(wordType);

            if (i < size - 1) {
                wordTypeBuilder.append(", ");
            }
        }

        return wordTypeBuilder.toString();
    }
}
