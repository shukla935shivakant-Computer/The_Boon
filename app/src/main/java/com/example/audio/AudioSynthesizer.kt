package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.sin

object AudioSynthesizer {
    private const val SAMPLE_RATE = 22050
    private val scope = CoroutineScope(Dispatchers.Default)

    var sfxVolume: Float = 0.8f
    var musicVolume: Float = 0.4f
    var isMusicEnabled: Boolean = false
        set(value) {
            field = value
            if (value) {
                startAmbientMusic()
            } else {
                stopAmbientMusic()
            }
        }

    private var musicJob: Job? = null

    fun playPop() {
        if (sfxVolume <= 0.01f) return
        scope.launch {
            val durationMs = 70
            val numSamples = (durationMs * SAMPLE_RATE) / 1000
            val buffer = ShortArray(numSamples)
            val freq = 800.0

            for (i in 0 until numSamples) {
                val t = i.toDouble() / SAMPLE_RATE
                // Decay envelope
                val envelope = 1.0 - (i.toDouble() / numSamples)
                val sample = sin(2.0 * Math.PI * freq * t) * envelope * sfxVolume * Short.MAX_VALUE
                buffer[i] = sample.toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }
            playPcm(buffer)
        }
    }

    fun playCorrect() {
        if (sfxVolume <= 0.01f) return
        scope.launch {
            // Ascending arpeggio: C5, E5, G5, C6
            val notes = doubleArrayOf(523.25, 659.25, 783.99, 1046.50)
            val noteDurationMs = 80
            val totalSamples = ((noteDurationMs * notes.size + 150) * SAMPLE_RATE) / 1000
            val buffer = ShortArray(totalSamples)

            var writeOffset = 0
            for (idx in notes.indices) {
                val freq = notes[idx]
                val noteSamples = (noteDurationMs * SAMPLE_RATE) / 1000
                for (i in 0 until noteSamples) {
                    val t = i.toDouble() / SAMPLE_RATE
                    val envelope = (1.0 - (i.toDouble() / noteSamples) * 0.4)
                    val sample = sin(2.0 * Math.PI * freq * t) * envelope * sfxVolume * Short.MAX_VALUE * 0.7
                    val sampleIndex = writeOffset + i
                    if (sampleIndex < totalSamples) {
                        buffer[sampleIndex] = sample.toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
                    }
                }
                writeOffset += noteSamples
            }
            // Add a ring out on the final note
            val finalSamples = totalSamples - writeOffset
            val finalFreq = notes.last()
            for (i in 0 until finalSamples) {
                val t = (i + noteDurationMs * SAMPLE_RATE / 1000).toDouble() / SAMPLE_RATE
                val envelope = 1.0 - (i.toDouble() / finalSamples)
                val sample = sin(2.0 * Math.PI * finalFreq * t) * envelope * sfxVolume * Short.MAX_VALUE * 0.7
                val sampleIndex = writeOffset + i
                if (sampleIndex < totalSamples) {
                    buffer[sampleIndex] = sample.toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
                }
            }
            playPcm(buffer)
        }
    }

    fun playWrong() {
        if (sfxVolume <= 0.01f) return
        scope.launch {
            val durationMs = 280
            val numSamples = (durationMs * SAMPLE_RATE) / 1000
            val buffer = ShortArray(numSamples)

            for (i in 0 until numSamples) {
                val progress = i.toDouble() / numSamples
                // Descending pitch with wobble vibrato
                val freq = 320.0 - (progress * 180.0) + (sin(progress * 30.0) * 15.0)
                val t = i.toDouble() / SAMPLE_RATE
                val envelope = 1.0 - progress
                val sample = sin(2.0 * Math.PI * freq * t) * envelope * sfxVolume * Short.MAX_VALUE * 0.65
                buffer[i] = sample.toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }
            playPcm(buffer)
        }
    }

    fun playTone(freq: Double, durationMs: Int) {
        if (sfxVolume <= 0.01f) return
        scope.launch {
            val numSamples = (durationMs * SAMPLE_RATE) / 1000
            val buffer = ShortArray(numSamples)
            for (i in 0 until numSamples) {
                val t = i.toDouble() / SAMPLE_RATE
                val envelope = if (i < 200) (i / 200.0) else (1.0 - (i.toDouble() / numSamples) * 0.3)
                val sample = sin(2.0 * Math.PI * freq * t) * envelope * sfxVolume * Short.MAX_VALUE * 0.6
                buffer[i] = sample.toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }
            playPcm(buffer)
        }
    }

    fun playFanfare() {
        if (sfxVolume <= 0.01f) return
        scope.launch {
            // Joyous chords sequence
            val sequence = listOf(
                listOf(523.25, 659.25, 783.99) to 120, // C major
                listOf(587.33, 739.99, 880.00) to 120, // D major
                listOf(659.25, 830.61, 987.77) to 140, // E major
                listOf(783.99, 987.77, 1046.50, 1318.5) to 400 // High triumph chord
            )
            for ((chord, dur) in sequence) {
                val numSamples = (dur * SAMPLE_RATE) / 1000
                val buffer = ShortArray(numSamples)
                for (i in 0 until numSamples) {
                    val t = i.toDouble() / SAMPLE_RATE
                    val env = 1.0 - (i.toDouble() / numSamples) * 0.4
                    var sum = 0.0
                    for (f in chord) {
                        sum += sin(2.0 * Math.PI * f * t)
                    }
                    val sample = (sum / chord.size) * env * sfxVolume * Short.MAX_VALUE * 0.7
                    buffer[i] = sample.toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
                }
                playPcm(buffer)
                delay(dur.toLong() + 20)
            }
        }
    }

    fun startAmbientMusic() {
        if (musicJob?.isActive == true) return
        musicJob = scope.launch {
            // Calming procedural ambient chords: C major9, F major7, G sus4, A minor7
            val chordProgressions = listOf(
                listOf(261.63, 329.63, 392.00, 493.88), // Cmaj7
                listOf(220.00, 261.63, 329.63, 392.00), // Am7
                listOf(174.61, 220.00, 261.63, 329.63), // Fmaj7
                listOf(196.00, 246.94, 293.66, 392.00)  // G
            )
            var chordIndex = 0

            while (isActive && isMusicEnabled) {
                if (musicVolume <= 0.01f) {
                    delay(500)
                    continue
                }
                val chord = chordProgressions[chordIndex % chordProgressions.size]
                chordIndex++
                val chordDurationSec = 3.5
                val numSamples = (chordDurationSec * SAMPLE_RATE).toInt()
                val buffer = ShortArray(numSamples)

                for (i in 0 until numSamples) {
                    val t = i.toDouble() / SAMPLE_RATE
                    // Soft bell-like swell envelope
                    val norm = i.toDouble() / numSamples
                    val env = sin(norm * Math.PI) * musicVolume * 0.25

                    var sum = 0.0
                    for ((fIdx, f) in chord.withIndex()) {
                        // slight slow detune chorus
                        val lfo = 1.0 + (0.002 * sin(t * 1.5 + fIdx))
                        sum += sin(2.0 * Math.PI * (f * lfo) * t)
                    }
                    val sample = (sum / chord.size) * env * Short.MAX_VALUE
                    buffer[i] = sample.toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
                }

                playPcm(buffer)
                delay((chordDurationSec * 1000).toLong() - 100)
            }
        }
    }

    fun stopAmbientMusic() {
        musicJob?.cancel()
        musicJob = null
    }

    private fun playPcm(buffer: ShortArray) {
        try {
            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(SAMPLE_RATE)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(buffer.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(buffer, 0, buffer.size)
            audioTrack.play()
            // Track cleans itself up or when GC collected
        } catch (_: Exception) {
            // Audio hardware fallback
        }
    }
}
