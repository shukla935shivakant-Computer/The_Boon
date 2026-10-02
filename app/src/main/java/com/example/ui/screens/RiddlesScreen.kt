package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.i18n.BoonStrings
import com.example.data.seed.RiddleItem
import com.example.ui.components.BouncyButton
import com.example.ui.theme.AmberGold
import com.example.ui.theme.AmberLight
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RoseAccent

@Composable
fun RiddlesScreen(
    currentRiddle: RiddleItem,
    currentIndex: Int,
    totalRiddles: Int,
    options: List<String>,
    selectedOption: String?,
    isCorrect: Boolean?,
    isHintRevealed: Boolean,
    solvedCount: Int,
    languageCode: String,
    onSelectOption: (String) -> Unit,
    onNextRiddle: () -> Unit,
    onRevealHint: () -> Unit,
    onSpeak: (String) -> Unit
) {
    val questionText = when (languageCode.lowercase()) {
        "hi" -> currentRiddle.questionHi
        "es" -> currentRiddle.questionEs
        "fr" -> currentRiddle.questionFr
        "zh" -> currentRiddle.questionZh
        else -> currentRiddle.questionEn
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = BoonStrings.get("riddle_title", languageCode),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    color = EmeraldDark
                )
                Text(
                    text = "${BoonStrings.get("riddle_num_of", languageCode)} #${currentIndex + 1} ${BoonStrings.get("riddle_of", languageCode)} $totalRiddles",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(AmberLight)
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "${BoonStrings.get("riddle_solved_prefix", languageCode)} $solvedCount",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB45309)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Riddle Question Card with TTS Speaker
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(EmeraldLight)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(BoonStrings.get("riddle_mystery_label", languageCode), color = EmeraldDark, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    // TTS Audio Speaker Button
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFE2E8F0))
                            .clickable { onSpeak(languageCode) }
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Listen",
                            tint = EmeraldDark,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = BoonStrings.get("riddle_listen", languageCode),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = EmeraldDark
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = questionText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF0F172A),
                    lineHeight = 24.sp
                )

                // Hint reveal box
                if (isHintRevealed) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(AmberLight)
                            .padding(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("💡", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = currentRiddle.getHint(languageCode),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF92400E)
                            )
                        }
                    }
                }

                // Correct Explanation Card
                if (isCorrect == true) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(EmeraldLight)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "✨ ${currentRiddle.getExplanation(languageCode)}",
                            fontSize = 12.sp,
                            color = EmeraldDark,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 8-Option Randomized Choice Jumble Grid
        Text(
            text = BoonStrings.get("riddle_pick_instruction", languageCode),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF334155)
        )

        Spacer(modifier = Modifier.height(6.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f, fill = false),
            contentPadding = PaddingValues(bottom = 6.dp)
        ) {
            items(options) { opt ->
                val isSelected = selectedOption == opt
                val isAnswer = opt.equals(currentRiddle.getCorrectAnswer(languageCode), ignoreCase = true)

                val (btnBg, btnBorder, btnText) = when {
                    isCorrect == true && isAnswer -> Triple(Color(0xFFDCFCE7), EmeraldDark, EmeraldDark)
                    isSelected && isCorrect == false -> Triple(Color(0xFFFFE4E6), RoseAccent, RoseAccent)
                    else -> Triple(Color.White, Color(0xFFCBD5E1), Color(0xFF1E293B))
                }

                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(btnBg)
                        .border(1.5.dp, btnBorder, RoundedCornerShape(16.dp))
                        .clickable(enabled = isCorrect != true) {
                            onSelectOption(opt)
                        }
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = opt,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected || (isCorrect == true && isAnswer)) FontWeight.Bold else FontWeight.Medium,
                        color = btnText,
                        maxLines = 1
                    )
                }
            }
        }

        // Bottom Action Bar: Hint & Next Riddle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 76.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (!isHintRevealed && isCorrect != true) {
                BouncyButton(
                    onClick = onRevealHint,
                    backgroundColor = AmberSecondary,
                    shadowColor = AmberGold,
                    modifier = Modifier.weight(1f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Lightbulb, contentDescription = "Hint", tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(BoonStrings.get("riddle_hint", languageCode), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }

            BouncyButton(
                onClick = onNextRiddle,
                backgroundColor = EmeraldPrimary,
                shadowColor = EmeraldDark,
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(BoonStrings.get("riddle_next", languageCode), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.NavigateNext, contentDescription = "Next", tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
