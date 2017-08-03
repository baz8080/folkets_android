package com.mbcdev.folkets;

import com.crashlytics.android.answers.CustomEvent;

/**
 * A CustomEvent for TTS events.
 * This event is managed by {@link EventPopulator#ttsEvent(String, String, TtsEvent)}
 *
 * Created by barry on 01/08/2017.
 */
class TtsEvent extends CustomEvent {

    TtsEvent() {
        super("TTS");
    }
}
