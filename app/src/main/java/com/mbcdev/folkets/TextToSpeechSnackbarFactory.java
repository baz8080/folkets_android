package com.mbcdev.folkets;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.zendesk.sdk.model.helpcenter.SimpleArticle;
import com.zendesk.sdk.support.ViewArticleActivity;

/**
 * Some SnackBar factories for the ones that will be shared between {@link MainMvp.View} and
 * {@link WordActivity}
 *
 * Created by barry on 23/07/2017.
 */
class TextToSpeechSnackbarFactory {

    private TextToSpeechSnackbarFactory() {
        // Intentionally empty
    }

    static Snackbar lowVolumeSnackbar(@NonNull final View view, @NonNull final Context context) {

        return Snackbar.make(view, R.string.tts_volume_too_low, Snackbar.LENGTH_LONG)
                .setAction(R.string.error_audio_settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Settings.ACTION_SOUND_SETTINGS);
                        context.startActivity(intent);
                    }
                });
    }

    static Snackbar languageNotSupportedSnackbar(
            @NonNull final View view, @NonNull final Context context) {

        return Snackbar.make(view, R.string.error_tts_didnt_work, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.error_fix_it, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent viewArticleIntent = new Intent(context, ViewArticleActivity.class);
                        viewArticleIntent.putExtra(
                                "article_simple", new SimpleArticle(115004243105L, ""));
                        viewArticleIntent.putExtra("extra_contact_us_button_visibility", false);

                        Intent ttsSettingsIntent = new Intent();
                        ttsSettingsIntent.setAction("com.android.settings.TTS_SETTINGS");

                        context.startActivities(new Intent[] {
                                ttsSettingsIntent,
                                viewArticleIntent
                        });
                    }
                });
    }

    static Snackbar genericErrorSnackbar(@NonNull final View view) {
        return Snackbar.make(view, R.string.error_tts_didnt_work, Snackbar.LENGTH_LONG);
    }
}
