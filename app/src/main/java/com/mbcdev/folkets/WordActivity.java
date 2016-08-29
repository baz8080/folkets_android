package com.mbcdev.folkets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import timber.log.Timber;

public class WordActivity extends AppCompatActivity {

    private ViewGroup container;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        final Word word = getIntent().getParcelableExtra("extra_word");
        Timber.d(word.toString());

        TextView wordTextView = (TextView) findViewById(R.id.activity_word_word);
        wordTextView.setText(word.getWord());

        TextView wordExtraDataTextView = (TextView) findViewById(R.id.activity_word_extra_data);

        wordExtraDataTextView.setText(getString(word.getWordType().getTextResourceId()));

        container = (ViewGroup) findViewById(R.id.activity_word_container);
        inflater = LayoutInflater.from(this);

        addSection(getString(R.string.translations_header), word.getTranslations());
        addSection(getString(R.string.definition_header), word.getDefinition());
        addSection(getString(R.string.explanation_header), word.getExplanation());
        addSection(getString(R.string.examples_header), word.getExamples());
        addSection(getString(R.string.antonyms_header), word.getAntonyms());
        addSection(getString(R.string.compounds_header), word.getCompounds());
        addSection(getString(R.string.derivations_header), word.getDerivations());
        addSection(getString(R.string.idioms_header), word.getIdioms());
        addSection("Usage", word.getUsage());
        addSection("Variant", word.getVariant());
        addSection("Phonetic", word.getPhonetic());
        addSection("Phonetic", word.getComment());
        addSection("Inflections", word.getInflections());
        addSection("Synonyms", word.getSynonyms());
        addSection("Comparisons", word.getCompareWith());
    }

    private void addSection(String title, List<String> list) {

        if (title == null || list == null) {
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0, listSize = list.size(); i < listSize; i++) {
            builder.append(list.get(i));

            if (i < listSize -1) {
                builder.append("\n");
            }
        }

        if (builder.length() > 0) {
            addSection(title, builder.toString());
        }
    }

    private void addSection(String title, WordsWithComments valuesWithTranslation) {

        if (valuesWithTranslation == null || valuesWithTranslation.getWords() == null || valuesWithTranslation.getWords().size() == 0) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (String wordWithComment : valuesWithTranslation.getWords()) {
            stringBuilder.append(wordWithComment);

            if (stringBuilder.length() > 0) {
                stringBuilder.append("\n");
            }
        }

        if (stringBuilder.length() > 0) {
            String content = stringBuilder.toString().trim();
            SectionLinearLayout section = new SectionLinearLayout(title, content);
            container.addView(section.getLayout());
        }

    }

    private void addSection(String title, ValuesWithTranslations valuesWithTranslation) {

        if (valuesWithTranslation == null) {
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (ValueWithTranslation valueWithTranslation : valuesWithTranslation.getValuesWithTranslations()) {
            builder.append(buildValueWithTranslation(valueWithTranslation));

            if (builder.length() > 0) {
                builder.append("\n\n");
            }
        }

        if (builder.length() > 0) {
            SectionLinearLayout section = new SectionLinearLayout(title, builder.toString().trim());
            container.addView(section.getLayout());
        }
    }

    private void addSection(String title, ValueWithTranslation valueWithTranslation) {

        if (valueWithTranslation == null) {
            return;
        }

        String context = buildValueWithTranslation(valueWithTranslation);

        if (Utils.isEmpty(context)) {
            return;
        }

        SectionLinearLayout section = new SectionLinearLayout(title, context);
        container.addView(section.getLayout());
    }

    private void addSection(String title, String content) {

        if (Utils.isEmpty(title) || Utils.isEmpty(content)) {
            return;
        }

        SectionLinearLayout section = new SectionLinearLayout(title, content);
        container.addView(section.getLayout());
    }

    private String buildValueWithTranslation (ValueWithTranslation valueWithTranslation) {
        StringBuilder builder = new StringBuilder();

        if (Utils.hasLength(valueWithTranslation.getValue())) {
            builder.append(valueWithTranslation.getValue());
        }

        if (builder.length() > 0) {
            builder.append("\n\n");
        }

        if (Utils.hasLength(valueWithTranslation.getTranslation())) {
            builder.append(valueWithTranslation.getTranslation());
        }

        return builder.toString().trim();
    }

    class SectionLinearLayout {

        private final LinearLayout layout;

        public SectionLinearLayout(String title, String content) {
            this.layout = (LinearLayout) inflater.inflate(R.layout.include_word_section, container, false);

            TextView titleTextView = (TextView) layout.findViewById(R.id.include_word_section_title);

            if (titleTextView != null) {
                titleTextView.setText(title);
            }

            TextView contentTextView = (TextView) layout.findViewById(R.id.include_word_section_content);

            if (contentTextView != null) {
                contentTextView.setText(content);
            }
        }

        LinearLayout getLayout() {
            return this.layout;
        }
    }
}
