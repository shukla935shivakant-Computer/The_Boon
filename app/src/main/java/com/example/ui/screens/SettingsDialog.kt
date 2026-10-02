package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.audio.AudioSynthesizer
import com.example.data.i18n.BoonStrings
import com.example.data.local.UserProfileEntity
import com.example.ui.components.BoonLogo
import com.example.ui.components.BouncyButton
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RoseAccent

@Composable
fun SettingsDialog(
    user: UserProfileEntity?,
    languageCode: String,
    onClose: () -> Unit,
    onLanguageChange: (String) -> Unit,
    onAudioChange: (sfx: Float, music: Float, musicOn: Boolean) -> Unit,
    onResetToZero: () -> Unit
) {
    var sfxVal by remember { mutableFloatStateOf(user?.sfxVolume ?: AudioSynthesizer.sfxVolume) }
    var musicVal by remember { mutableFloatStateOf(user?.musicVolume ?: AudioSynthesizer.musicVolume) }
    var musicEnabled by remember { mutableStateOf(user?.isMusicEnabled ?: AudioSynthesizer.isMusicEnabled) }
    var showResetConfirmation by remember { mutableStateOf(false) }

    val languages = listOf(
        Triple("en", "English", "🇺🇸"),
        Triple("hi", "हिन्दी (Hindi)", "🇮🇳"),
        Triple("es", "Español", "🇪🇸"),
        Triple("fr", "Français", "🇫🇷"),
        Triple("zh", "简体中文", "🇨🇳")
    )

    Dialog(onDismissRequest = onClose) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        BoonLogo(size = 32.dp, elevation = 2.dp, shapeRadius = 10.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = BoonStrings.get("settings_title", languageCode),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF0F172A)
                        )
                    }
                    IconButton(onClick = onClose, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color(0xFF64748B))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Audio Section: SFX
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = BoonStrings.get("settings_sfx", languageCode),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                    Text("${(sfxVal * 100).toInt()}%", fontSize = 12.sp, color = EmeraldDark, fontWeight = FontWeight.Bold)
                }

                Slider(
                    value = sfxVal,
                    onValueChange = {
                        sfxVal = it
                        onAudioChange(sfxVal, musicVal, musicEnabled)
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = EmeraldPrimary,
                        activeTrackColor = EmeraldPrimary
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Ambient Music Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = BoonStrings.get("settings_music_toggle", languageCode),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                        Text(
                            text = "Calming procedural drone & bell harmony",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                    Switch(
                        checked = musicEnabled,
                        onCheckedChange = {
                            musicEnabled = it
                            onAudioChange(sfxVal, musicVal, musicEnabled)
                        },
                        colors = SwitchDefaults.colors(checkedThumbColor = EmeraldPrimary)
                    )
                }

                if (musicEnabled) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = BoonStrings.get("settings_music", languageCode),
                            fontSize = 12.sp,
                            color = Color(0xFF475569)
                        )
                        Text("${(musicVal * 100).toInt()}%", fontSize = 11.sp, color = EmeraldDark, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = musicVal,
                        onValueChange = {
                            musicVal = it
                            onAudioChange(sfxVal, musicVal, musicEnabled)
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = EmeraldPrimary,
                            activeTrackColor = EmeraldPrimary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Language Selector
                Text(
                    text = BoonStrings.get("settings_language", languageCode),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                languages.forEach { (code, label, flag) ->
                    val isSelected = languageCode.equals(code, ignoreCase = true)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) EmeraldLight else Color(0xFFF8FAFC))
                            .clickable { onLanguageChange(code) }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(flag, fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = label,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) EmeraldDark else Color(0xFF1E293B)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        if (isSelected) {
                            Text("✓", fontSize = 14.sp, fontWeight = FontWeight.Black, color = EmeraldDark)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Fresh App Mode (Reset to 0)
                BouncyButton(
                    onClick = { showResetConfirmation = true },
                    backgroundColor = Color(0xFFFFE4E6),
                    shadowColor = Color(0xFFFDA4AF),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.RestartAlt, contentDescription = "Reset", tint = RoseAccent, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = BoonStrings.get("settings_reset", languageCode),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = RoseAccent
                        )
                    }
                }
            }
        }
    }

    if (showResetConfirmation) {
        AlertDialog(
            onDismissRequest = { showResetConfirmation = false },
            title = {
                Text(
                    text = BoonStrings.get("settings_reset", languageCode),
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = BoonStrings.get("settings_reset_confirm", languageCode),
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showResetConfirmation = false
                        onResetToZero()
                    }
                ) {
                    Text(
                        text = BoonStrings.get("settings_reset_btn", languageCode),
                        color = RoseAccent,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirmation = false }) {
                    Text(BoonStrings.get("cancel", languageCode))
                }
            }
        )
    }
}
