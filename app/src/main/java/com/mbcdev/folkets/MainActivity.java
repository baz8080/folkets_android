package com.mbcdev.folkets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.crashlytics.android.answers.Answers;
import com.zendesk.sdk.model.helpcenter.SimpleArticle;
import com.zendesk.sdk.support.SupportActivity;
import com.zendesk.sdk.support.ViewArticleActivity;

import java.util.List;

import timber.log.Timber;

/**
 * This activity will show a list of words and translations, and allow users to tap on a result
 * to see the full definition of the word
 */
public class MainActivity extends AppCompatActivity implements MainMvp.View {

    private RecyclerView recyclerView;
    private MainMvp.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.main_search_title);
        }

        recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addOnScrollListener(new KeyboardHidingScrollListener());

        presenter = new MainPresenter();

        FolketsDatabase database = new FolketsDatabase(this);
        FabricProvider fabricProvider = new DefaultFabricProvider(Answers.getInstance());
        MainMvp.Model model = new MainModel(database, fabricProvider);

        presenter.init(this, model);
        presenter.search("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
        Timber.d("onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.main_search).getActionView();
        searchView.setImeOptions(searchView.getImeOptions() | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        searchView.setQueryHint(getString(R.string.main_search_title));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.search(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            presenter.helpRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showResults(@NonNull List<Word> words) {
        recyclerView.setAdapter(new WordsRecyclerAdapter(words, presenter));
    }

    @Override
    public void onError(@NonNull ErrorType errorType) {
        String error = getString(errorType.getStringResourceId());
        Snackbar.make(recyclerView, error, Snackbar.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showHelp() {
        new SupportActivity.Builder()
                .show(this);
    }

    @Override
    public void showWordDetail(Word word) {
        WordActivity.startWithWord(this, word);
    }

    @Override
    public void speak(Word word) {
        FolketsTextToSpeech.SpeechStatus status = MainApplication.instance().speak(word);
        presenter.onTtsResult(status);
    }

    @Override
    public void showLowVolumeError() {

        Snackbar.make(recyclerView, R.string.tts_volume_too_low, Snackbar.LENGTH_LONG)
                .setAction(R.string.error_audio_settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Settings.ACTION_SOUND_SETTINGS);
                        startActivity(intent);
                    }
                })
                .show();
    }

    @Override
    public void showLanguageNotSupportedError() {
        Snackbar.make(recyclerView, R.string.error_tts_didnt_work, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.error_fix_it, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent viewArticleIntent = new Intent(MainActivity.this, ViewArticleActivity.class);
                        viewArticleIntent.putExtra("article_simple", new SimpleArticle(115004243105L, ""));
                        viewArticleIntent.putExtra("extra_contact_us_button_visibility", false);

                        Intent ttsSettingsIntent = new Intent();
                        ttsSettingsIntent.setAction("com.android.settings.TTS_SETTINGS");

                        startActivities(new Intent[] {
                                ttsSettingsIntent,
                                viewArticleIntent
                        });
                    }
                })
                .show();
    }

    @Override
    public void showGenericTTsError(FolketsTextToSpeech.SpeechStatus status) {
        Snackbar.make(recyclerView, R.string.error_tts_didnt_work, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Attempts to hide the keyboard when the RecyclerView scroll state changes
     */
    private class KeyboardHidingScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            if (getCurrentFocus() == null) {
                return;
            }

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
