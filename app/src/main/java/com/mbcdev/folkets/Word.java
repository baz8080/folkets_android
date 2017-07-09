package com.mbcdev.folkets;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zendesk.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

/**
 * Model to convert a word from a database cursor
 *
 * Created by barry on 21/08/2016.
 */
class Word implements Serializable, Comparable<Word> {

    private String sourceLanguage;
    private String word;
    private String comment;
    private List<WordType> wordTypes;
    private WordsWithComments translations;
    private List<String> inflections;
    private ValuesWithTranslations examples;
    private ValueWithTranslation definition;
    private ValueWithTranslation explanation;
    private String phonetic;
    private List<String> synonyms;
    private SaldoLinks saldoLinks;
    private List<String> compareWith;
    private ValuesWithTranslations antonyms;
    private String usage;
    private String variant;
    private ValuesWithTranslations idioms;
    private ValuesWithTranslations derivations;
    private ValuesWithTranslations compounds;

    /**
     * Creates an instance from the given cursor
     *
     * @param cursor the cursor containing the words.
     */
    Word(Cursor cursor) {
        
        if (cursor == null) {
            Timber.d("Cursor was null, cannot continue.");
            return;
        }

        sourceLanguage = cursor.getString(cursor.getColumnIndex("language"));
        word = cursor.getString(cursor.getColumnIndex("word"));
        comment = cursor.getString(cursor.getColumnIndex("comment"));
        wordTypes = compileWordTypes(cursor.getString(cursor.getColumnIndex("types")));
        translations = new WordsWithComments(cursor.getString(cursor.getColumnIndex("translations")));
        inflections = stringToList(cursor.getString(cursor.getColumnIndex("inflections")));
        examples = new ValuesWithTranslations(cursor.getString(cursor.getColumnIndex("examples")));
        definition = new ValueWithTranslation(cursor.getString(cursor.getColumnIndex("definition")));

        String rawExplanation = cursor.getString(cursor.getColumnIndex("explanation"));
        if (StringUtils.hasLength(rawExplanation)) {
            this.explanation = new ValueWithTranslation(rawExplanation);
        } else {
            this.explanation = null;
        }

        phonetic = cursor.getString(cursor.getColumnIndex("phonetic"));
        synonyms = stringToList(cursor.getString(cursor.getColumnIndex("synonyms")));
        saldoLinks = new SaldoLinks(cursor.getString(cursor.getColumnIndex("saldos")));
        compareWith = stringToList(cursor.getString(cursor.getColumnIndex("comparisons")));
        antonyms = new ValuesWithTranslations(cursor.getString(cursor.getColumnIndex("antonyms")));
        usage = cursor.getString(cursor.getColumnIndex("use"));
        variant = cursor.getString(cursor.getColumnIndex("variant"));
        idioms = new ValuesWithTranslations(cursor.getString(cursor.getColumnIndex("idioms")));
        derivations = new ValuesWithTranslations(cursor.getString(cursor.getColumnIndex("derivations")));
        compounds = new ValuesWithTranslations(cursor.getString(cursor.getColumnIndex("compounds")));
    }

    /**
     * Gets the source language of this word
     *
     * @return "en" if this is an English word, or "sv" if it is a Swedish word.
     */
    String getSourceLanguage() {
        return sourceLanguage;
    }

    /**
     * Returns the word, like 'barn'
     *
     * @return the word
     */
    String getWord() {
        return word;
    }

    /**
     * Gets the translations for the word, like 'child'
     *
     * @return the translations for the word
     */
    WordsWithComments getTranslations() {
        return translations;
    }

    /**
     * Gets the comment for the word
     *
     * @return the comment for the word
     */
    String getComment() {
        return comment;
    }

    /**
     * Gets a list of types of this word, like noun, adjective
     *
     * @return a list of types of this word
     */
    List<WordType> getWordTypes() {
        return wordTypes;
    }

    /**
     * Gets a list of the inflected values of this word
     *
     * @return a list of the inflected values of this word
     */
    List<String> getInflections() {
        return inflections;
    }

    /**
     * Gets the example usages of this word
     *
     * @return the example usages of this word
     */
    ValuesWithTranslations getExamples() {
        return examples;
    }

    /**
     * Gets the definition of this word
     *
     * @return the definition of this word
     */
    ValueWithTranslation getDefinition() {
        return definition;
    }

    /**
     * Gets the explanation of this word
     *
     * @return the explanation of this word
     */
    ValueWithTranslation getExplanation() {
        return explanation;
    }

    /**
     * Gets the phonetic of this word
     *
     * @return the phonetic of this word
     */
    String getPhonetic() {
        return phonetic;
    }

    /**
     * Gets this word's synonyms
     *
     * @return this word's synonyms
     */
    List<String> getSynonyms() {
        return synonyms;
    }

    /**
     * Gets this word's links to Saldo
     *
     * @return this word's links to Saldo
     */
    SaldoLinks getSaldoLinks() {
        return saldoLinks;
    }

    /**
     * Gets a list of words to compare with this word
     *
     * @return a list of words to compare with this word
     */
    List<String> getCompareWith() {
        return compareWith;
    }

    /**
     * Gets a list of antonyms of this word
     *
     * @return a list of antonyms of this word
     */
    ValuesWithTranslations getAntonyms() {
        return antonyms;
    }

    /**
     * Gets the usage of this word
     *
     * @return the usage of this word
     */
    String getUsage() {
        return usage;
    }

    /**
     * Gets the variant of this word
     *
     * @return the variant of this word
     */
    String getVariant() {
        return variant;
    }

    /**
     * Gets the idioms of this word
     *
     * @return the idioms of this word
     */
    ValuesWithTranslations getIdioms() {
        return idioms;
    }

    /**
     * Gets the derivations of this word
     *
     * @return the derivations of this word
     */
    ValuesWithTranslations getDerivations() {
        return derivations;
    }

    /**
     * Gets a list of words that feature this word as a part of a compound word
     *
     * @return a list of words that feature this word as a part of a compound word
     */
    ValuesWithTranslations getCompounds() {
        return compounds;
    }

    /**
     * Converts a csv string to a list
     *
     * @param csvString a csv string
     * @return A list of Strings, or an empty list
     */
    @NonNull
    private List<String> stringToList(@Nullable String csvString) {

        List<String> strings = new ArrayList<>();

        if (StringUtils.hasLength(csvString)) {
            String[] split = csvString.split(Utils.ASTERISK_SEPARATOR);

            for (String string : split) {
                if (StringUtils.hasLength(string)) {
                    strings.add(string.trim());
                }
            }
        }

        return strings;
    }

    @NonNull
    private List<WordType> compileWordTypes(@Nullable String types) {

        List<WordType> wordTypes = new ArrayList<>();

        if (types == null) {
            wordTypes.add(WordType.UNKNOWN);
        } else {
            for (String type : types.split(",")) {
                wordTypes.add(WordType.lookup(type));
            }
        }

        return wordTypes;
    }

    @Override
    public int compareTo(@NonNull Word o) {
        return this.word.toLowerCase(Locale.US).compareTo(o.getWord().toLowerCase(Locale.US));
    }
}
