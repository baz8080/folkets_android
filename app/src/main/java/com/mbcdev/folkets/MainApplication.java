package com.mbcdev.folkets;

import android.app.Application;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;

import com.crashlytics.android.Crashlytics;
import com.zendesk.logger.Logger;
import com.zendesk.sdk.model.access.AnonymousIdentity;
import com.zendesk.sdk.network.impl.ZendeskConfig;
import com.zendesk.util.StringUtils;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Main application class. Installs a debug timber tree
 *
 * Created by barry on 20/08/2016.
 */
public class MainApplication extends Application {

    private static MainApplication instance;

    private FolketsTextToSpeech textToSpeech;

    private AudioManager audioManager;

    /**
     * Gets the instance of MainApplication
     *
     * @return gets the instance of MainApplication
     */
    public static MainApplication instance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
        instance = this;

        Timber.plant(new Timber.DebugTree());
        Logger.setLoggable(BuildConfig.DEBUG);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        /*
            I'd rather not handle voice here, but it is being initialised early on for performance.
            If I defer it to WordActivity, the delay can be up to 5 seconds before tts is fully
            ready to speak. onInit itself is called rather quickly, but there is some background
            processing then which is the real issue. This issue does not manifest on high-end
            devices but can be clearly see on the likes of the Moto G (3rd generation)
         */
        FolketsTextToSpeechInitListener listener = new FolketsTextToSpeechInitListener();
        TextToSpeech tts = new TextToSpeech(this, listener);
        textToSpeech = new FolketsTextToSpeech(tts, listener);

        if (StringUtils.hasLengthMany(
                getString(R.string.com_zendesk_sdk_url),
                getString(R.string.com_zendesk_sdk_identifier),
                getString(R.string.com_zendesk_sdk_clientIdentifier)
        )) {
            ZendeskConfig.INSTANCE.init(
                    this,
                    getString(R.string.com_zendesk_sdk_url),
                    getString(R.string.com_zendesk_sdk_identifier),
                    getString(R.string.com_zendesk_sdk_clientIdentifier));

            ZendeskConfig.INSTANCE.setIdentity(new AnonymousIdentity.Builder().build());
        }
    }

    /**
     * Calls {@link FolketsTextToSpeech#speak(Language, String)} to invoke TTS
     *
     * @param word The word to speak with TTS
     */
    public FolketsTextToSpeech.SpeechStatus speak(Word word) {

        if (audioManager != null && audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {
            return FolketsTextToSpeech.SpeechStatus.ERROR_VOLUME_TOO_LOW;
        }

        FolketsTextToSpeech.SpeechStatus status =
                FolketsTextToSpeech.SpeechStatus.ERROR_TTS_NULL;

        if (textToSpeech != null) {
            status = textToSpeech.speak(
                    Language.fromLanguageCode(word.getSourceLanguage()),
                    word.getWord()
            );
        }

        return status;
    }
}
