package com.mbcdev.folkets;

import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.zendesk.util.StringUtils;

import java.util.Locale;

import timber.log.Timber;

/**
 * Wrapper for {@link TextToSpeech} to handle changing languages.
 *
 * Created by barry on 11/06/2017.
 */
class FolketsTextToSpeech {

    enum FolketsSpeechStatus {
        ERROR_TTS_NULL,
        ERROR_LISTENER_NULL,
        ERROR_TTS_NOT_READY,
        ERROR_LANGUAGE_OR_PHRASE_MISSING,
        SUCCESS
    }

    private FolketsTextToSpeechInitListener listener;
    private TextToSpeech textToSpeech;

    /**
     * Creates an instance, wrapping the supplied TextToSpeech instance.
     *
     * @param textToSpeech The TextToSpeech object to wrap
     * @param listener The listener used to determine whether the TextToSpeech object is initialised
     */
    FolketsTextToSpeech(TextToSpeech textToSpeech, FolketsTextToSpeechInitListener listener) {
        this.textToSpeech = textToSpeech;
        this.listener = listener;
    }

    /**
     * Speaks a phrase in the given language
     *
     * @param language The language to speak in
     * @param phrase The phrase to speak
     * @return The status of the speaking, will be {@link FolketsSpeechStatus#SUCCESS} if successful,
     *         otherwise an error has occured.
     */
    @NonNull FolketsSpeechStatus speak(@Nullable Language language, @Nullable String phrase) {

        if (textToSpeech == null) {
            Timber.d("Text to speech is null");
            return FolketsSpeechStatus.ERROR_TTS_NULL;
        } else if (listener == null) {
            Timber.d("Text to speech initialisation listener is null");
            return FolketsSpeechStatus.ERROR_LISTENER_NULL;
        } else if (!listener.isInitialised()) {
            Timber.d("Text to speech initialisation is not ready yet");
            return FolketsSpeechStatus.ERROR_TTS_NOT_READY;
        } else if (language == null || StringUtils.isEmpty(phrase)) {
            Timber.d("Text to speech requires a valid language and phrase");
            return FolketsSpeechStatus.ERROR_LANGUAGE_OR_PHRASE_MISSING;
        }

        Locale currentTtsLanguage = textToSpeech.getLanguage();
        if (currentTtsLanguage == null || (!currentTtsLanguage.equals(language.getLocale()))) {
            Timber.d("Setting text to speech language to %s", language.getLocale());
            textToSpeech.setLanguage(language.getLocale());
        }

        textToSpeech.speak(phrase, TextToSpeech.QUEUE_FLUSH, null);

        CustomEvent event = new CustomEvent("TTS")
                .putCustomAttribute("Language", language.getCode())
                .putCustomAttribute("Phrase", phrase);

        Answers.getInstance().logCustom(event);
        return FolketsSpeechStatus.SUCCESS;
    }
}
