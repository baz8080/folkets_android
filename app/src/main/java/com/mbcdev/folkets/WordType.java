package com.mbcdev.folkets;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Models the types of words in the database
 *
 * Created by barry on 21/08/2016.
 */
public enum WordType {
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

    WordType(String rawType, int textResourceId) {
        this.rawType = rawType;
        this.textResourceId = textResourceId;
    }

    public static WordType lookup(String rawType) {

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

    public int getTextResourceId() {
        return textResourceId;
    }

    /**
     * Formats word types for display
     *
     * @param context A context used to resolve strings
     * @param wordTypes The types of words to format
     * @return A formatted string containing the string representations of the words
     */
    public static String formatWordTypesForDisplay(Context context, List<WordType> wordTypes) {

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
