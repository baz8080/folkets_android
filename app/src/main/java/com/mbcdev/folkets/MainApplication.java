package com.mbcdev.folkets;

import android.app.Application;
import android.speech.tts.TextToSpeech;

import com.zendesk.logger.Logger;
import com.zendesk.sdk.model.access.AnonymousIdentity;
import com.zendesk.sdk.network.impl.ZendeskConfig;

import timber.log.Timber;

/**
 * Main application class. Installs a debug timber tree
 *
 * Created by barry on 20/08/2016.
 */
public class MainApplication extends Application {

    private static MainApplication instance;

    private TextToSpeech textToSpeech;
    private boolean textToSpeechAvailable;

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

        instance = this;

        /*

            I'd rather not handle voice here, but it is being initialised early on for performance.
            If I defer it to WordActivity, the delay can be up to 5 seconds before tts is fully
            ready to speak. onInit itself is called rather quickly, but there is some background
            processing then which is the real issue. This issue does not manifest on high-end
            devices but can be clearly see on the likes of the Moto G (3rd generation)

         */
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeechAvailable = (status == TextToSpeech.SUCCESS);
            }
        });

        Timber.plant(new Timber.DebugTree());

        Logger.setLoggable(BuildConfig.DEBUG);

        ZendeskConfig.INSTANCE.init(
                this,
                "https://z3nfolkets.zendesk.com",
                getString(R.string.FOLKETS_ZEN_APP_ID),
                getString(R.string.FOLKETS_ZEN_OAUTH_CLIENT_ID));

        ZendeskConfig.INSTANCE.setIdentity(new AnonymousIdentity.Builder().build());
    }

    /**
     * Speaks a phrase in the given language
     *
     * @param language The language to speak in
     * @param phrase The phrase to speak
     */
    public void speak(Language language, String phrase) {

        if (instance == null || instance.textToSpeech == null || !instance.textToSpeechAvailable) {
            Timber.d("Text to speech is not available");
            return;
        }

        if (language != null && !instance.textToSpeech.getLanguage().equals(language.getLocale())) {
            instance.textToSpeech.setLanguage(language.getLocale());
        }

        instance.textToSpeech.speak(phrase, TextToSpeech.QUEUE_FLUSH, null);
    }
}
