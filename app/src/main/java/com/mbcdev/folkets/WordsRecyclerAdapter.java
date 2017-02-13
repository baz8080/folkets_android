package com.mbcdev.folkets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * RecyclerAdapter for a list of words
 *
 * Created by barry on 21/08/2016.
 */
class WordsRecyclerAdapter extends RecyclerView.Adapter<WordsRecyclerAdapter.ViewHolder> {

    private final List<Word> wordList;

    /**
     * Creates an instance with the given list of words
     *
     * @param wordList the list of words to display
     */
    WordsRecyclerAdapter(List<Word> wordList) {
        this.wordList = wordList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Word word = wordList.get(position);
        final Context context = holder.holderView.getContext();

        holder.holderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WordActivity.startWithWord(context, word);
            }
        });

        holder.wordTextView.setText(word.getWord());
        holder.wordTextView.setCompoundDrawablesWithIntrinsicBounds(
                word.getSourceLanguage().equals("en") ? R.drawable.flag_uk : R.drawable.flag_sv,
                0, 0, 0
        );

        String wordTypes = WordType.formatWordTypesForDisplay(
                holder.holderView.getContext(), word.getWordTypes());
        holder.wordTypeTextView.setText(wordTypes);

        StringBuilder stringBuilder = new StringBuilder();

        for (String wordWithComment : word.getTranslations().getWords()) {
            stringBuilder.append(wordWithComment).append("\n");
        }

        if (stringBuilder.length() > 0) {
            holder.translationTextView.setVisibility(View.VISIBLE);
            holder.translationTextView.setText(
                    stringBuilder.subSequence(0, stringBuilder.length() - 1)
            );
        } else {
            holder.translationTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    /**
     * Viewholder for the recyclerview
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        final View holderView;
        final TextView wordTextView;
        final TextView wordTypeTextView;
        final TextView translationTextView;

        ViewHolder(View v) {
            super(v);

            holderView = v;
            wordTextView = (TextView) v.findViewById(R.id.recycler_view_word);
            wordTypeTextView = (TextView) v.findViewById(R.id.recycler_view_type);
            translationTextView = (TextView) v.findViewById(R.id.recycler_view_translation);
        }
    }
}
