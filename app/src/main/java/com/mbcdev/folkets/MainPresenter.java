package com.mbcdev.folkets;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;

import com.zendesk.util.StringUtils;

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
    public void attachView(@NonNull MainMvp.View view) {
        this.view = view;
        Context context = view.getContext();
        model = new MainModel(context);
        view.setToolbarText(context.getString(R.string.main_search_title));
        search("");
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void search(@NonNull final String query) {

        if (view == null) {
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
                model.search(query, new com.mbcdev.folkets.Callback<List<Word>>() {
                    @Override
                    public void onSuccess(List<Word> result) {
                        view.showResults(result);
                    }

                    @Override
                    public void onError(ErrorType errorType) {
                        view.onError(errorType);
                    }
                });
            }
        }.start();
    }

    @Override
    public void helpRequested() {
        view.showHelp();
    }
}
