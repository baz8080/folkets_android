package com.mbcdev.folkets;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.zendesk.util.StringUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import timber.log.Timber;

/**
 * Default implementation of {@link MainMvp.Presenter}
 *
 * Created by barry on 21/08/2016.
 */
class MainPresenter implements MainMvp.Presenter {

    private MainMvp.Model model;
    private MainMvp.View view;

    private CountDownTimer countDownTimer;

    @Override
    public void init(@NonNull MainMvp.View view, @NonNull MainMvp.Model model) {
        this.view = view;
        this.model = model;

        search("");
    }

    @Override
    public void detach() {
        this.view = null;
        this.model = null;

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void search(@NonNull final String query) {

        if (view == null || model == null) {
            Timber.d("View is null. Will not search.");
            return;
        }

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        long delay = StringUtils.hasLength(query)
                ? 150
                : 0;

        countDownTimer = new CountDownTimer(delay, delay) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Intentionally empty
            }

            @Override
            public void onFinish() {
                model.search(query, new SearchCallback(view));
            }
        }.start();
    }

    @Override
    public void helpRequested() {
        if (view != null) {
            view.showHelp();
        }
    }

    @Override
    public void onWordSelected(Word word) {
        if (view != null) {
            view.showWordDetail(word);
        }
    }

    @Override
    public void onTtsRequested(Word word) {
        if (view != null) {
            view.speak(word);
        }
    }

    @VisibleForTesting
    static class SearchCallback implements Callback<List<Word>> {

        private final WeakReference<MainMvp.View> viewReference;

        SearchCallback(MainMvp.View view) {
            this.viewReference = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(List<Word> result) {
            MainMvp.View view = viewReference.get();

            if (view != null) {
                view.showResults(result);
            }
        }

        @Override
        public void onError(ErrorType errorType) {
            MainMvp.View view = viewReference.get();

            if (view != null) {
                view.onError(errorType);
            }
        }
    }
}