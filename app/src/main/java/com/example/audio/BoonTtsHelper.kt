package com.example.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class BoonTtsHelper(context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var isReady = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isReady = true
            tts?.language = Locale.ENGLISH
        }
    }

    fun speak(text: String, languageCode: String = "en") {
        if (!isReady || tts == null) return
        val locale = when (languageCode.lowercase()) {
            "hi" -> Locale("hi", "IN")
            "es" -> Locale("es", "ES")
            "fr" -> Locale.FRENCH
            "zh" -> Locale.CHINESE
            else -> Locale.ENGLISH
        }
        tts?.language = locale
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "BoonTtsUtterance")
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        isReady = false
    }
}
