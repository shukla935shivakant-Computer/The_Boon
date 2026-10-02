package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.i18n.BoonStrings
import com.example.ui.theme.AmberGold
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RoseAccent
import com.example.ui.theme.SkyBlueAccent
import com.example.ui.theme.VioletAccent

@Composable
fun XpCelebrationDialog(
    xp: Int,
    questTitle: String,
    languageCode: String = "en",
    onDismiss: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "confetti")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Confetti and Star Burst
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(RoundedCornerShape(55.dp))
                            .background(Color(0xFFFEF3C7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val colors = listOf(EmeraldPrimary, AmberGold, RoseAccent, SkyBlueAccent, VioletAccent)
                            for (i in 0 until 12) {
                                val angle = Math.toRadians((i * 30 + rotation).toDouble())
                                val dist = size.width * 0.38f
                                val x = (size.width / 2) + (dist * Math.cos(angle)).toFloat()
                                val y = (size.height / 2) + (dist * Math.sin(angle)).toFloat()
                                drawCircle(
                                    color = colors[i % colors.size],
                                    radius = 7f,
                                    center = Offset(x, y)
                                )
                            }
                        }
                        Text("🌟", fontSize = 54.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = BoonStrings.get("celebration_quest_completed", languageCode),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = EmeraldDark
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "+$xp ${BoonStrings.get("celebration_xp_earned", languageCode)}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = AmberGold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = questTitle,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF475569)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    BouncyButton(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = EmeraldPrimary,
                        shadowColor = EmeraldDark
                    ) {
                        Text(
                            text = BoonStrings.get("celebration_continue", languageCode),
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}
