package com.mbcdev.folkets;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Creates and models a word from a cursor
 *
 * Created by barry on 21/08/2016.
 */
public class Word implements Parcelable {

    private final String word;
    private final String comment;
    private final WordType wordType;
    private final WordsWithComments translations;
    private final List<String> inflections;
    private final ValuesWithTranslations examples;
    private final ValueWithTranslation definition;
    private final ValueWithTranslation explanation;
    private final String phonetic;
    private final List<String> synonyms;
    private final SaldoLinks saldoLinks;
    private final List<String> compareWith;
    private final ValuesWithTranslations antonyms;
    private final String usage;
    private final String variant;
    private final ValuesWithTranslations idioms;
    private final ValuesWithTranslations derivations;
    private final ValuesWithTranslations compounds;

    public Word(Cursor cursor) {
        word = cursor.getString(cursor.getColumnIndex("word"));
        comment = cursor.getString(cursor.getColumnIndex("comment"));
        wordType = WordType.lookup(cursor.getString(cursor.getColumnIndex("type")));
        translations = new WordsWithComments(cursor.getString(cursor.getColumnIndex("translations")));
        inflections = stringToList(cursor.getString(cursor.getColumnIndex("inflections")));
        examples = new ValuesWithTranslations(cursor.getString(cursor.getColumnIndex("examples")));
        definition = new ValueWithTranslation(cursor.getString(cursor.getColumnIndex("definition")));

        String rawExplanation = cursor.getString(cursor.getColumnIndex("explanation"));
        if (rawExplanation.length() != 0) {
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

    public String getWord() {
        return word;
    }

    public WordsWithComments getTranslations() {
        return translations;
    }

    public String getComment() {
        return comment;
    }

    public WordType getWordType() {
        return wordType;
    }

    public List<String> getInflections() {
        return inflections;
    }

    public ValuesWithTranslations getExamples() {
        return examples;
    }

    public ValueWithTranslation getDefinition() {
        return definition;
    }

    public ValueWithTranslation getExplanation() {
        return explanation;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public SaldoLinks getSaldoLinks() {
        return saldoLinks;
    }

    public List<String> getCompareWith() {
        return compareWith;
    }

    public ValuesWithTranslations getAntonyms() {
        return antonyms;
    }

    public String getUsage() {
        return usage;
    }

    public String getVariant() {
        return variant;
    }

    public ValuesWithTranslations getIdioms() {
        return idioms;
    }

    public ValuesWithTranslations getDerivations() {
        return derivations;
    }

    public ValuesWithTranslations getCompounds() {
        return compounds;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", comment='" + comment + '\'' +
                ", wordType=" + wordType +
                ", translations=" + translations +
                ", inflections=" + inflections +
                ", examples=" + examples +
                ", definition=" + definition +
                ", explanation=" + explanation +
                ", phonetic='" + phonetic + '\'' +
                ", synonyms=" + synonyms +
                ", saldoLinks=" + saldoLinks +
                ", compareWith=" + compareWith +
                ", antonyms=" + antonyms +
                ", usage='" + usage + '\'' +
                ", variant='" + variant + '\'' +
                ", idioms=" + idioms +
                ", derivations=" + derivations +
                ", compounds=" + compounds +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    protected Word(Parcel in) {
        word = in.readString();
        comment = in.readString();
        wordType = (WordType) in.readValue(WordType.class.getClassLoader());
        translations = (WordsWithComments) in.readValue(WordsWithComments.class.getClassLoader());
        if (in.readByte() == 0x01) {
            inflections = new ArrayList<>();
            in.readList(inflections, String.class.getClassLoader());
        } else {
            inflections = null;
        }
        examples = (ValuesWithTranslations) in.readValue(ValuesWithTranslations.class.getClassLoader());
        definition = (ValueWithTranslation) in.readValue(ValueWithTranslation.class.getClassLoader());
        explanation = (ValueWithTranslation) in.readValue(ValueWithTranslation.class.getClassLoader());
        phonetic = in.readString();
        if (in.readByte() == 0x01) {
            synonyms = new ArrayList<>();
            in.readList(synonyms, String.class.getClassLoader());
        } else {
            synonyms = null;
        }
        saldoLinks = (SaldoLinks) in.readValue(SaldoLinks.class.getClassLoader());
        if (in.readByte() == 0x01) {
            compareWith = new ArrayList<>();
            in.readList(compareWith, String.class.getClassLoader());
        } else {
            compareWith = null;
        }
        antonyms = (ValuesWithTranslations) in.readValue(ValuesWithTranslations.class.getClassLoader());
        usage = in.readString();
        variant = in.readString();
        idioms = (ValuesWithTranslations) in.readValue(ValuesWithTranslations.class.getClassLoader());
        derivations = (ValuesWithTranslations) in.readValue(ValuesWithTranslations.class.getClassLoader());
        compounds = (ValuesWithTranslations) in.readValue(ValuesWithTranslations.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(comment);
        dest.writeValue(wordType);
        dest.writeValue(translations);
        if (inflections == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(inflections);
        }
        dest.writeValue(examples);
        dest.writeValue(definition);
        dest.writeValue(explanation);
        dest.writeString(phonetic);
        if (synonyms == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(synonyms);
        }
        dest.writeValue(saldoLinks);
        if (compareWith == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(compareWith);
        }
        dest.writeValue(antonyms);
        dest.writeString(usage);
        dest.writeString(variant);
        dest.writeValue(idioms);
        dest.writeValue(derivations);
        dest.writeValue(compounds);
    }

    private List<String> stringToList(String listCsv) {

        List<String> strings = new ArrayList<>();

        if (Utils.hasLength(listCsv)) {
            strings = Arrays.asList(listCsv.split(","));
        }

        return strings;
    }
}