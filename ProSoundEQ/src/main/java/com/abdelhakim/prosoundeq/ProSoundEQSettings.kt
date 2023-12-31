/**
 * ProSoundEQ Android Library
 * Author: ABDELHAKIM KHAOUITI
 * GitHub: https://github.com/khaouitiabdelhakim
 * Last Modified: October 26, 2023
 *
 * Description: ProSoundEQSettings is a part of the ProSoundEQ Android library,
 * which provides audio equalization, bass boost, virtualizer, and reverb settings.
 * This object is responsible for initializing and managing audio effects settings.
 */

package com.abdelhakim.prosoundeq

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer
import android.media.audiofx.PresetReverb
import android.media.audiofx.Virtualizer
import androidx.core.content.ContextCompat.startActivity

object ProSoundEQSettings {



    /**
     * Initializes the audio effects settings.
     *
     * @param mediaSession An integer representing the media session.
     */
    fun init(mediaSession: Int) {
        ProSoundEQ.equalizer = Equalizer(1, mediaSession)
        ProSoundEQ.bassBoost = BassBoost(1, mediaSession)
        ProSoundEQ.virtualizer = Virtualizer(1, mediaSession)
        ProSoundEQ.reverb = PresetReverb(1, mediaSession)
    }

    /**
     * Sets the main color for the ProSoundEQ user interface.
     *
     * @param color A color string in the format "#RRGGBB" or "#AARRGGBB".
     */
    fun setColor(color: String) {
        try {
            ProSoundEQ.mainColor = Color.parseColor(color)
        } catch (_: Exception) {
            // Handle parsing exceptions
        }
    }
}
