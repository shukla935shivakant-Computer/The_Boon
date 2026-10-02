package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioSynthesizer
import com.example.data.i18n.BoonStrings
import com.example.data.local.UserProfileEntity
import com.example.ui.theme.AmberGold
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldPrimary

@Composable
fun BoonTopBar(
    user: UserProfileEntity?,
    onLanguageChange: (String) -> Unit,
    onOpenSettings: () -> Unit
) {
    var isLangMenuOpen by remember { mutableStateOf(false) }
    val currentLang = user?.languageCode ?: "en"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        EmeraldDark,
                        EmeraldPrimary
                    )
                )
            )
            .statusBarsPadding()
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // App Identity & Level
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Official Brand Logo
                BoonLogo(size = 38.dp, elevation = 4.dp, shapeRadius = 12.dp)

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = BoonStrings.get("app_name", currentLang),
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Text(
                        text = "${BoonStrings.get("topbar_lvl", currentLang)} ${user?.level ?: 1} • ${user?.name ?: "Explorer"}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            // Stats and Quick Actions
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Streak Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🔥", fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${user?.streakDays ?: 0}d",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = AmberGold
                        )
                    }
                }

                // XP Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(AmberSecondary)
                        .padding(horizontal = 9.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⭐", fontSize = 11.sp)
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${user?.xp ?: 0}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }

                // Language Switcher Icon
                Box {
                    IconButton(
                        onClick = {
                            AudioSynthesizer.playPop()
                            isLangMenuOpen = true
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Language",
                            tint = Color.White
                        )
                    }

                    DropdownMenu(
                        expanded = isLangMenuOpen,
                        onDismissRequest = { isLangMenuOpen = false }
                    ) {
                        val languages = listOf(
                            Triple("en", "English", "🇺🇸"),
                            Triple("hi", "हिन्दी (Hindi)", "🇮🇳"),
                            Triple("es", "Español (Spanish)", "🇪🇸"),
                            Triple("fr", "Français (French)", "🇫🇷"),
                            Triple("zh", "简体中文 (Chinese)", "🇨🇳")
                        )
                        languages.forEach { (code, label, flag) ->
                            DropdownMenuItem(
                                text = { Text("$flag  $label") },
                                onClick = {
                                    onLanguageChange(code)
                                    isLangMenuOpen = false
                                }
                            )
                        }
                    }
                }

                // Settings Gear Button
                IconButton(
                    onClick = onOpenSettings,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
