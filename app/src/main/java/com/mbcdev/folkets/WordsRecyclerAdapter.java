package com.mbcdev.folkets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zendesk.logger.Logger;
import com.zendesk.util.StringUtils;

import java.util.List;

/**
 * RecyclerAdapter for a list of words
 *
 * Created by barry on 21/08/2016.
 */
class WordsRecyclerAdapter extends RecyclerView.Adapter<WordsRecyclerAdapter.ViewHolder> {

    private final static String LOG_TAG = "WordsRecyclerAdapter";

    private final List<Word> wordList;
    private final MainMvp.Presenter presenter;

    /**
     * Creates an instance with the given list of words
     *
     * @param wordList the list of words to display
     * @param presenter the presenter to notify when actions happen
     */
    WordsRecyclerAdapter(@NonNull List<Word> wordList, @NonNull MainMvp.Presenter presenter) {
        this.wordList = wordList;
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (holder == null) {
            Logger.e(LOG_TAG, "The ViewHolder was null. Doing nothing.");
            return;
        }

        final Word word = wordList.get(position);

        if (word == null) {
            Logger.e(LOG_TAG, "The Word at position %d was null. Doing nothing.", position);
            return;
        }

        final Context context = holder.holderView.getContext();

        holder.holderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onWordSelected(word);
            }
        });

        holder.ttsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onTtsRequested(word);
            }
        });

        holder.wordTextView.setText(word.getWord());
        holder.wordTextView.setCompoundDrawablesWithIntrinsicBounds(word.getFlag(), 0, 0, 0);

        String wordTypes = WordType.formatWordTypesForDisplay(context, word.getWordTypes());
        holder.wordTypeTextView.setText(wordTypes);

        String translations = word.getTranslations().getWordsFormattedForDisplay();

        if (StringUtils.hasLength(translations)) {
            holder.translationTextView.setVisibility(View.VISIBLE);
            holder.translationTextView.setText(translations);
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
        final ImageView ttsImageView;

        ViewHolder(View v) {
            super(v);

            holderView = v;
            wordTextView = v.findViewById(R.id.recycler_view_word);
            wordTypeTextView = v.findViewById(R.id.recycler_view_type);
            translationTextView = v.findViewById(R.id.recycler_view_translation);
            ttsImageView = v.findViewById(R.id.recycler_view_tts);
        }
    }
}
