package com.mbcdev.folkets;

import java.util.HashMap;
import java.util.Map;

/**
 * Models the types of words in the database
 *
 * Created by barry on 21/08/2016.
 */
@SuppressWarnings("unused")
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
    UNKNOWN("", R.string.word_type_unknown);

    private final String rawType;

    private static final Map<String, WordType> cache = new HashMap<>(values().length);
    private final int textResourceId;

    WordType(String rawType, int textResourceId) {
        this.rawType = rawType;
        this.textResourceId = textResourceId;
    }

    public static WordType lookup(String rawType) {
        if (cache.containsKey(rawType)) {
            return cache.get(rawType);
        }

        for (WordType wordType : values()) {
            if (wordType.rawType.equals(rawType)) {
                cache.put(rawType, wordType);
                return wordType;
            }
        }

        return UNKNOWN;
    }

    public int getTextResourceId() {
        return textResourceId;
    }
}
