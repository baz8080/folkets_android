package com.mbcdev.folkets;

import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zendesk.util.StringUtils;

import java.util.Locale;

import timber.log.Timber;

/**
 * Wrapper for {@link TextToSpeech} to handle changing languages.
 *
 * Created by barry on 11/06/2017.
 */
class FolketsTextToSpeech {

    enum SpeechStatus {
        ERROR_TTS_NULL,
        ERROR_LISTENER_NULL,
        ERROR_TTS_NOT_READY,
        ERROR_LANGUAGE_OR_PHRASE_MISSING,
        ERROR_LANGUAGE_NOT_SUPPORTED,
        ERROR_VOLUME_TOO_LOW,
        SUCCESS
    }

    private final FolketsTextToSpeechInitListener listener;
    private final TextToSpeech textToSpeech;
    private final FabricProvider fabricProvider;

    /**
     * Creates an instance, wrapping the supplied TextToSpeech instance.
     *
     * @param textToSpeech The TextToSpeech object to wrap
     * @param listener The listener used to determine whether the TextToSpeech object is initialised
     * @param fabricProvider The provider of crashlytics and answers implementations
     */
    FolketsTextToSpeech(
            TextToSpeech textToSpeech, FolketsTextToSpeechInitListener listener,
            FabricProvider fabricProvider) {
        this.textToSpeech = textToSpeech;
        this.listener = listener;
        this.fabricProvider = fabricProvider;
    }

    /**
     * Speaks a phrase in the given language
     *
     * @param language The language to speak in
     * @param phrase The phrase to speak
     * @return The status of the speaking, will be {@link SpeechStatus#SUCCESS} if successful,
     *         otherwise an error has occured.
     */
    @NonNull
    SpeechStatus speak(@Nullable Language language, @Nullable String phrase) {

        if (textToSpeech == null) {
            Timber.d("Text to speech is null");
            return SpeechStatus.ERROR_TTS_NULL;
        } else if (listener == null) {
            Timber.d("Text to speech initialisation listener is null");
            return SpeechStatus.ERROR_LISTENER_NULL;
        } else if (!listener.isInitialised()) {
            Timber.d("Text to speech initialisation is not ready yet");
            return SpeechStatus.ERROR_TTS_NOT_READY;
        } else if (language == null || StringUtils.isEmpty(phrase)) {
            Timber.d("Text to speech requires a valid language and phrase");
            return SpeechStatus.ERROR_LANGUAGE_OR_PHRASE_MISSING;
        }

        Locale wordLocale = language.getLocale();
        int languageAvailability = textToSpeech.isLanguageAvailable(wordLocale);

        if (languageAvailability == TextToSpeech.LANG_MISSING_DATA
                        || languageAvailability == TextToSpeech.LANG_NOT_SUPPORTED) {
            Timber.d("Language code %s is not supported on this device", language.getCode());
            return SpeechStatus.ERROR_LANGUAGE_NOT_SUPPORTED;
        }

        Locale currentTtsLanguage = textToSpeech.getLanguage();
        if (currentTtsLanguage == null || (!currentTtsLanguage.equals(language.getLocale()))) {
            Timber.d("Setting text to speech language to %s", language.getLocale());
            textToSpeech.setLanguage(language.getLocale());
        }

        textToSpeech.speak(phrase, TextToSpeech.QUEUE_FLUSH, null);

        if (fabricProvider != null) {
            fabricProvider.logTextToSpeechEvent(language.getCode(), phrase);
        }

        return SpeechStatus.SUCCESS;
    }
}
