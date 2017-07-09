package com.mbcdev.folkets;

import android.speech.tts.TextToSpeech;

/**
 * This mainly exists so I can test the initialisation of TTS.
 *
 * Created by barry on 11/06/2017.
 */
class FolketsTextToSpeechInitListener implements TextToSpeech.OnInitListener {

    private int status = TextToSpeech.ERROR;

    @Override
    public void onInit(int status) {
        this.status = status;
    }

    /**
     * Checks whether TTS is initialised or not. Initialised means that the TTS system initialised
     * and returned a code of {@link TextToSpeech#SUCCESS}
     *
     * @return true if TTS is initialised, false otherwise
     */
    boolean isInitialised() {
        return status == TextToSpeech.SUCCESS;
    }
}
