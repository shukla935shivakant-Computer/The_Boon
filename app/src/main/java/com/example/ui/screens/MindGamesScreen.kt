package com.example.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioSynthesizer
import com.example.data.i18n.BoonStrings
import com.example.ui.components.BouncyButton
import com.example.ui.theme.AmberGold
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RoseAccent
import com.example.ui.theme.SkyBlueAccent
import com.example.ui.theme.SkyBlueLight
import com.example.ui.theme.VioletAccent
import com.example.ui.theme.VioletLight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.random.Random

data class GameMeta(
    val id: Int,
    val iconEmoji: String,
    val color: Color
) {
    fun getTitle(lang: String): String = BoonStrings.get("game_${id}_name", lang)
    fun getSubtitle(lang: String): String = BoonStrings.get("game_${id}_desc", lang)
}

@Composable
fun MindGamesScreen(
    languageCode: String = "en",
    onAwardXp: (Int, String) -> Unit
) {
    var activeGameId by remember { mutableStateOf<Int?>(null) }

    val games = listOf(
        GameMeta(1, "🃏", Color(0xFF10B981)),
        GameMeta(2, "🎨", Color(0xFFF59E0B)),
        GameMeta(3, "⚡", Color(0xFF0284C7)),
        GameMeta(4, "🔤", Color(0xFF8B5CF6)),
        GameMeta(5, "⏱️", Color(0xFFF43F5E)),
        GameMeta(6, "🎯", Color(0xFF14B8A6)),
        GameMeta(7, "💡", Color(0xFFEC4899)),
        GameMeta(8, "🎵", Color(0xFF6366F1)),
        GameMeta(9, "🚩", Color(0xFF84CC16)),
        GameMeta(10, "🧘", Color(0xFF06B6D4))
    )

    if (activeGameId == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFEDE9FE))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = BoonStrings.get("games_banner_title", languageCode),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = VioletAccent
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = BoonStrings.get("games_banner_sub", languageCode),
                            fontSize = 12.sp,
                            color = Color(0xFF334155),
                            lineHeight = 16.sp
                        )
                    }
                    Text("🏆", fontSize = 34.sp)
                }
            }

            // Games Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                contentPadding = PaddingValues(bottom = 90.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(games) { game ->
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(3.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                AudioSynthesizer.playPop()
                                activeGameId = game.id
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(game.color.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(game.iconEmoji, fontSize = 26.sp)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = game.getTitle(languageCode),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF0F172A)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = game.getSubtitle(languageCode),
                                fontSize = 11.sp,
                                color = Color(0xFF64748B),
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    } else {
        BackHandler { activeGameId = null }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Top Return Bar
            val currentGame = games.find { it.id == activeGameId }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { activeGameId = null }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = EmeraldDark
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = currentGame?.getTitle(languageCode) ?: BoonStrings.get("tab_games", languageCode),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
            }

            // Render specific playable game
            when (activeGameId) {
                1 -> MemoryMatchGame(languageCode) { onAwardXp(60, "Memory Match") }
                2 -> ColorTapGame(languageCode) { onAwardXp(60, "Color Tap") }
                3 -> MathSpeedRushGame(languageCode) { onAwardXp(60, "Math Speed") }
                4 -> WordScrambleGame(languageCode) { onAwardXp(60, "Word Scramble") }
                5 -> ReactionReflexGame(languageCode) { onAwardXp(60, "Reaction Reflex") }
                6 -> BalanceBallGame(languageCode) { onAwardXp(70, "Balance Ball") }
                7 -> SimonPatternGame(languageCode) { onAwardXp(70, "Simon Pattern") }
                8 -> PitchEarTrainerGame(languageCode) { onAwardXp(60, "Pitch Trainer") }
                9 -> MazeRunnerGame(languageCode) { onAwardXp(70, "Maze Runner") }
                10 -> FocusBreathingGame(languageCode) { onAwardXp(50, "Focus Breathing") }
            }
        }
    }
}

// 1. Memory Match Game
@Composable
fun MemoryMatchGame(
    languageCode: String,
    onWin: () -> Unit
) {
    val icons = remember {
        listOf("🦊", "🦉", "🌿", "🍄", "🐞", "🌻", "🌈", "🐝")
    }
    val cards = remember {
        val list = mutableStateListOf<MemoryCard>()
        (icons + icons).shuffled().forEachIndexed { index, emoji ->
            list.add(MemoryCard(index, emoji))
        }
        list
    }
    var flippedIndices by remember { mutableStateOf<List<Int>>(emptyList()) }
    var matchedCount by remember { mutableIntStateOf(0) }
    var movesCount by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("${BoonStrings.get("game_memory_moves", languageCode)} $movesCount", fontWeight = FontWeight.Bold, color = Color(0xFF334155))
            Text("${BoonStrings.get("game_memory_matched", languageCode)} $matchedCount / 8", fontWeight = FontWeight.Bold, color = EmeraldDark)
        }

        Spacer(modifier = Modifier.height(14.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(cards.size) { idx ->
                val card = cards[idx]
                val isFlipped = card.isMatched || flippedIndices.contains(idx)

                Box(
                    modifier = Modifier
                        .height(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isFlipped) Color.White else EmeraldPrimary)
                        .border(
                            width = 2.dp,
                            color = if (card.isMatched) AmberGold else EmeraldDark,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable(enabled = !isFlipped && flippedIndices.size < 2) {
                            AudioSynthesizer.playPop()
                            val current = flippedIndices.toMutableList()
                            current.add(idx)
                            flippedIndices = current

                            if (current.size == 2) {
                                movesCount++
                                val first = cards[current[0]]
                                val second = cards[current[1]]
                                if (first.emoji == second.emoji) {
                                    first.isMatched = true
                                    second.isMatched = true
                                    matchedCount++
                                    flippedIndices = emptyList()
                                    AudioSynthesizer.playCorrect()
                                    if (matchedCount == 8) {
                                        AudioSynthesizer.playFanfare()
                                        onWin()
                                    }
                                } else {
                                    scope.launch {
                                        delay(800)
                                        flippedIndices = emptyList()
                                        AudioSynthesizer.playWrong()
                                    }
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isFlipped) card.emoji else "❓",
                        fontSize = if (isFlipped) 28.sp else 20.sp
                    )
                }
            }
        }

        if (matchedCount == 8) {
            Text("🎉 ${BoonStrings.get("game_memory_win", languageCode)}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
        }
    }
}

class MemoryCard(val id: Int, val emoji: String, var isMatched: Boolean = false)

// 2. Color Tap Game (Stroop Effect)
@Composable
fun ColorTapGame(
    languageCode: String,
    onScoreTarget: () -> Unit
) {
    val colorNames = listOf(
        BoonStrings.get("color_red", languageCode),
        BoonStrings.get("color_blue", languageCode),
        BoonStrings.get("color_green", languageCode),
        BoonStrings.get("color_yellow", languageCode)
    )
    val colorValues = listOf(Color.Red, SkyBlueAccent, Color(0xFF16A34A), AmberSecondary)

    var wordIndex by remember { mutableIntStateOf(Random.nextInt(4)) }
    var inkIndex by remember { mutableIntStateOf(Random.nextInt(4)) }
    var score by remember { mutableIntStateOf(0) }
    var timeLeft by remember { mutableIntStateOf(15) }
    var gameOver by remember { mutableStateOf(false) }

    LaunchedEffect(timeLeft, gameOver) {
        if (!gameOver && timeLeft > 0) {
            delay(1000)
            timeLeft--
            if (timeLeft == 0) {
                gameOver = true
                if (score >= 5) onScoreTarget()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("${BoonStrings.get("game_stroop_score", languageCode)} $score", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
            Text("${BoonStrings.get("game_stroop_time", languageCode)} ${timeLeft}s", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RoseAccent)
        }

        if (!gameOver) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(BoonStrings.get("game_stroop_instruction", languageCode), fontSize = 14.sp, color = Color(0xFF64748B))
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = colorNames[wordIndex],
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Black,
                    color = colorValues[inkIndex]
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    for (i in 0..1) {
                        BouncyButton(
                            onClick = {
                                if (i == inkIndex) {
                                    score++
                                    AudioSynthesizer.playCorrect()
                                } else {
                                    AudioSynthesizer.playWrong()
                                }
                                wordIndex = Random.nextInt(4)
                                inkIndex = Random.nextInt(4)
                            },
                            backgroundColor = colorValues[i],
                            shadowColor = colorValues[i].copy(alpha = 0.7f),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(colorNames[i], color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    for (i in 2..3) {
                        BouncyButton(
                            onClick = {
                                if (i == inkIndex) {
                                    score++
                                    AudioSynthesizer.playCorrect()
                                } else {
                                    AudioSynthesizer.playWrong()
                                }
                                wordIndex = Random.nextInt(4)
                                inkIndex = Random.nextInt(4)
                            },
                            backgroundColor = colorValues[i],
                            shadowColor = colorValues[i].copy(alpha = 0.7f),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(colorNames[i], color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(BoonStrings.get("game_stroop_timesup", languageCode), fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F172A))
                Spacer(modifier = Modifier.height(6.dp))
                Text("${BoonStrings.get("game_stroop_score", languageCode)} $score", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
                Spacer(modifier = Modifier.height(16.dp))
                BouncyButton(
                    onClick = {
                        score = 0
                        timeLeft = 15
                        gameOver = false
                    },
                    backgroundColor = EmeraldPrimary,
                    shadowColor = EmeraldDark
                ) {
                    Text(BoonStrings.get("game_play_again", languageCode), color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// 3. Math Speed Rush
@Composable
fun MathSpeedRushGame(
    languageCode: String,
    onWin: () -> Unit
) {
    var a by remember { mutableIntStateOf(Random.nextInt(3, 12)) }
    var b by remember { mutableIntStateOf(Random.nextInt(2, 9)) }
    var op by remember { mutableStateOf("+") }
    var correctAns by remember { mutableIntStateOf(a + b) }
    var options by remember { mutableStateOf(generateMathOptions(a + b)) }
    var score by remember { mutableIntStateOf(0) }

    fun nextQuestion() {
        val nextOp = listOf("+", "-", "*").random()
        val nextA = if (nextOp == "*") Random.nextInt(2, 7) else Random.nextInt(5, 20)
        val nextB = if (nextOp == "*") Random.nextInt(2, 6) else Random.nextInt(2, nextA)
        val ans = when (nextOp) {
            "+" -> nextA + nextB
            "-" -> nextA - nextB
            else -> nextA * nextB
        }
        a = nextA
        b = nextB
        op = nextOp
        correctAns = ans
        options = generateMathOptions(ans)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("${BoonStrings.get("game_math_streak", languageCode)} $score / 5", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SkyBlueAccent)

        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SkyBlueLight),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("$a $op $b = ?", fontSize = 42.sp, fontWeight = FontWeight.Black, color = Color(0xFF0369A1))
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            options.forEach { opt ->
                BouncyButton(
                    onClick = {
                        if (opt == correctAns) {
                            score++
                            AudioSynthesizer.playCorrect()
                            if (score >= 5) {
                                AudioSynthesizer.playFanfare()
                                onWin()
                            }
                        } else {
                            score = 0
                            AudioSynthesizer.playWrong()
                        }
                        nextQuestion()
                    },
                    backgroundColor = SkyBlueAccent,
                    shadowColor = Color(0xFF0369A1),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("$opt", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
        }
    }
}

fun generateMathOptions(correct: Int): List<Int> {
    val set = mutableSetOf(correct)
    while (set.size < 4) {
        val diff = listOf(-3, -2, -1, 1, 2, 3, 5).random()
        val candidate = (correct + diff).coerceAtLeast(0)
        set.add(candidate)
    }
    return set.toList().shuffled()
}

// 4. Word Scramble Game
@Composable
fun WordScrambleGame(
    languageCode: String,
    onWin: () -> Unit
) {
    val wordList = listOf("NATURE", "GRAVITY", "SPROUT", "VOLCANO", "OXYGEN", "PLANET")
    var currentIdx by remember { mutableIntStateOf(0) }
    val currentWord = wordList[currentIdx % wordList.size]
    var scrambled by remember { mutableStateOf(currentWord.toList().shuffled().joinToString("")) }
    var hintRevealed by remember { mutableStateOf(false) }
    val userLetters = remember { mutableStateListOf<Char>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(BoonStrings.get("game_4_name", languageCode), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = VioletAccent)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(BoonStrings.get("game_word_unjumble", languageCode), fontSize = 13.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(10.dp))
            Text(scrambled, fontSize = 36.sp, fontWeight = FontWeight.Black, color = VioletAccent, letterSpacing = 4.sp)

            if (hintRevealed) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("${BoonStrings.get("game_hint_starts_with", languageCode)} '${currentWord.first()}'", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AmberSecondary)
            }
        }

        // Selected letters box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFEDE9FE))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (userLetters.isEmpty()) BoonStrings.get("game_tap_letters", languageCode) else userLetters.joinToString(""),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = if (userLetters.isEmpty()) Color.Gray else Color(0xFF0F172A)
            )
        }

        // Tappable letter buttons
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            scrambled.forEach { char ->
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(VioletAccent)
                        .clickable {
                            AudioSynthesizer.playPop()
                            userLetters.add(char)
                            if (userLetters.joinToString("") == currentWord) {
                                AudioSynthesizer.playCorrect()
                                onWin()
                                userLetters.clear()
                                currentIdx++
                                val next = wordList[currentIdx % wordList.size]
                                scrambled = next.toList().shuffled().joinToString("")
                                hintRevealed = false
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("$char", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            BouncyButton(
                onClick = { userLetters.clear() },
                backgroundColor = Color(0xFF64748B),
                shadowColor = Color(0xFF334155)
            ) {
                Text(BoonStrings.get("game_clear", languageCode), color = Color.White, fontWeight = FontWeight.Bold)
            }
            BouncyButton(
                onClick = { hintRevealed = true },
                backgroundColor = AmberSecondary,
                shadowColor = AmberGold
            ) {
                Text(BoonStrings.get("game_hint", languageCode), color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// 5. Reaction Reflex Game
@Composable
fun ReactionReflexGame(
    languageCode: String,
    onWin: () -> Unit
) {
    var state by remember { mutableStateOf("READY") } // READY, WAITING, TAP_NOW, FINISHED
    var startTime by remember { mutableStateOf(0L) }
    var reactionTimeMs by remember { mutableStateOf(0L) }

    LaunchedEffect(state) {
        if (state == "WAITING") {
            val randomDelay = Random.nextLong(1500, 3500)
            delay(randomDelay)
            startTime = System.currentTimeMillis()
            state = "TAP_NOW"
            AudioSynthesizer.playTone(880.0, 100)
        }
    }

    val (bg, label) = when (state) {
        "READY" -> Pair(Color(0xFFE2E8F0), BoonStrings.get("game_reaction_ready", languageCode))
        "WAITING" -> Pair(RoseAccent, BoonStrings.get("game_reaction_wait", languageCode))
        "TAP_NOW" -> Pair(Color(0xFF22C55E), BoonStrings.get("game_reaction_tapnow", languageCode))
        else -> Pair(EmeraldLight, "${reactionTimeMs}ms!")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(bg)
            .clickable {
                when (state) {
                    "READY" -> {
                        AudioSynthesizer.playPop()
                        state = "WAITING"
                    }
                    "WAITING" -> {
                        AudioSynthesizer.playWrong()
                        state = "READY" // Too early!
                    }
                    "TAP_NOW" -> {
                        reactionTimeMs = System.currentTimeMillis() - startTime
                        AudioSynthesizer.playCorrect()
                        state = "FINISHED"
                        onWin()
                    }
                    "FINISHED" -> {
                        state = "READY"
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color.White)
            if (state == "FINISHED") {
                Spacer(modifier = Modifier.height(10.dp))
                Text(BoonStrings.get("game_reaction_fast", languageCode), color = EmeraldDark, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// 6. Balance Ball Game (with touch drag and accelerometer fallback)
@Composable
fun BalanceBallGame(
    languageCode: String,
    onWin: () -> Unit
) {
    val context = LocalContext.current
    var ballX by remember { mutableStateOf(150f) }
    var ballY by remember { mutableStateOf(150f) }
    var targetX by remember { mutableStateOf(150f) }
    var targetY by remember { mutableStateOf(80f) }
    var hasWon by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        val accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null && !hasWon) {
                    ballX = (ballX - event.values[0] * 2.5f).coerceIn(40f, 260f)
                    ballY = (ballY + event.values[1] * 2.5f).coerceIn(40f, 260f)
                    val dist = sqrt((ballX - targetX) * (ballX - targetX) + (ballY - targetY) * (ballY - targetY))
                    if (dist < 30f) {
                        hasWon = true
                        AudioSynthesizer.playFanfare()
                        onWin()
                    }
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager?.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        onDispose { sensorManager?.unregisterListener(listener) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(BoonStrings.get("game_balance_instruction", languageCode), fontSize = 13.sp, color = Color.Gray, textAlign = TextAlign.Center)

        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(CircleShape)
                .background(Color(0xFFE2E8F0))
                .border(4.dp, Color(0xFF0F766E), CircleShape)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        ballX = (ballX + dragAmount.x).coerceIn(40f, 260f)
                        ballY = (ballY + dragAmount.y).coerceIn(40f, 260f)
                        val dist = sqrt((ballX - targetX) * (ballX - targetX) + (ballY - targetY) * (ballY - targetY))
                        if (dist < 30f && !hasWon) {
                            hasWon = true
                            AudioSynthesizer.playFanfare()
                            onWin()
                        }
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Draw target hole
                drawCircle(color = AmberGold, radius = 28f, center = Offset(targetX, targetY))
                drawCircle(color = Color(0xFFB45309), radius = 12f, center = Offset(targetX, targetY))

                // Draw ball
                drawCircle(color = Color(0xFF0F766E), radius = 20f, center = Offset(ballX, ballY))
            }
        }

        if (hasWon) {
            Text(BoonStrings.get("game_balance_bullseye", languageCode), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
        } else {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// 7. Simon Pattern Memory Game
@Composable
fun SimonPatternGame(
    languageCode: String,
    onWin: () -> Unit
) {
    val colors = listOf(Color(0xFF22C55E), Color(0xFFEF4444), Color(0xFFF59E0B), Color(0xFF3B82F6))
    val frequencies = listOf(440.0, 554.37, 659.25, 880.0)
    var sequence by remember { mutableStateOf(listOf(Random.nextInt(4), Random.nextInt(4))) }
    var userIndex by remember { mutableIntStateOf(0) }
    var highlightedPad by remember { mutableStateOf<Int?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(sequence) {
        delay(600)
        for (pad in sequence) {
            highlightedPad = pad
            AudioSynthesizer.playTone(frequencies[pad], 200)
            delay(350)
            highlightedPad = null
            delay(150)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("${BoonStrings.get("game_simon_repeat", languageCode)} ${sequence.size - 1}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEC4899))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            for (row in 0..1) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    for (col in 0..1) {
                        val index = row * 2 + col
                        val isLit = highlightedPad == index
                        Box(
                            modifier = Modifier
                                .size(130.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .background(if (isLit) colors[index] else colors[index].copy(alpha = 0.4f))
                                .border(4.dp, colors[index], RoundedCornerShape(24.dp))
                                .clickable {
                                    AudioSynthesizer.playTone(frequencies[index], 150)
                                    if (index == sequence[userIndex]) {
                                        userIndex++
                                        if (userIndex == sequence.size) {
                                            AudioSynthesizer.playCorrect()
                                            if (sequence.size >= 5) {
                                                onWin()
                                            }
                                            userIndex = 0
                                            sequence = sequence + Random.nextInt(4)
                                        }
                                    } else {
                                        AudioSynthesizer.playWrong()
                                        userIndex = 0
                                        sequence = listOf(Random.nextInt(4), Random.nextInt(4))
                                    }
                                }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

// 8. Pitch Ear Trainer Game
@Composable
fun PitchEarTrainerGame(
    languageCode: String,
    onWin: () -> Unit
) {
    var freqA by remember { mutableStateOf(440.0) }
    var freqB by remember { mutableStateOf(660.0) }
    var score by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()

    fun newRound() {
        val base = Random.nextDouble(300.0, 700.0)
        val diff = Random.nextDouble(40.0, 120.0)
        if (Random.nextBoolean()) {
            freqA = base
            freqB = base + diff
        } else {
            freqA = base + diff
            freqB = base
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Streak: $score / 5", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = VioletAccent)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(BoonStrings.get("game_pitch_instruction", languageCode), fontSize = 13.sp, color = Color.Gray, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(20.dp))
            BouncyButton(
                onClick = {
                    scope.launch {
                        AudioSynthesizer.playTone(freqA, 300)
                        delay(400)
                        AudioSynthesizer.playTone(freqB, 300)
                    }
                },
                backgroundColor = VioletAccent,
                shadowColor = Color(0xFF5B21B6)
            ) {
                Text(BoonStrings.get("game_pitch_play", languageCode), color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.fillMaxWidth()) {
            BouncyButton(
                onClick = {
                    if (freqA > freqB) {
                        score++
                        AudioSynthesizer.playCorrect()
                        if (score >= 5) onWin()
                    } else {
                        score = 0
                        AudioSynthesizer.playWrong()
                    }
                    newRound()
                },
                backgroundColor = SkyBlueAccent,
                shadowColor = Color(0xFF0369A1),
                modifier = Modifier.weight(1f)
            ) {
                Text(BoonStrings.get("game_pitch_note1_higher", languageCode), color = Color.White, fontWeight = FontWeight.Bold)
            }

            BouncyButton(
                onClick = {
                    if (freqB > freqA) {
                        score++
                        AudioSynthesizer.playCorrect()
                        if (score >= 5) onWin()
                    } else {
                        score = 0
                        AudioSynthesizer.playWrong()
                    }
                    newRound()
                },
                backgroundColor = EmeraldPrimary,
                shadowColor = EmeraldDark,
                modifier = Modifier.weight(1f)
            ) {
                Text(BoonStrings.get("game_pitch_note2_higher", languageCode), color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// 9. Maze Runner Game
@Composable
fun MazeRunnerGame(
    languageCode: String,
    onWin: () -> Unit
) {
    var playerRow by remember { mutableIntStateOf(0) }
    var playerCol by remember { mutableIntStateOf(0) }
    val goalRow = 4
    val goalCol = 4

    // 5x5 grid with some walls (1 = wall, 0 = path)
    val maze = listOf(
        listOf(0, 0, 1, 0, 0),
        listOf(1, 0, 1, 0, 1),
        listOf(0, 0, 0, 0, 0),
        listOf(0, 1, 1, 1, 0),
        listOf(0, 0, 0, 0, 0)
    )

    fun move(dr: Int, dc: Int) {
        val nr = (playerRow + dr).coerceIn(0, 4)
        val nc = (playerCol + dc).coerceIn(0, 4)
        if (maze[nr][nc] == 0) {
            playerRow = nr
            playerCol = nc
            AudioSynthesizer.playPop()
            if (nr == goalRow && nc == goalCol) {
                AudioSynthesizer.playFanfare()
                onWin()
            }
        } else {
            AudioSynthesizer.playWrong()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(BoonStrings.get("game_maze_instruction", languageCode), fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF84CC16))

        // Maze Grid
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            for (r in 0..4) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    for (c in 0..4) {
                        val isWall = maze[r][c] == 1
                        val isPlayer = r == playerRow && c == playerCol
                        val isGoal = r == goalRow && c == goalCol

                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isWall) Color(0xFF334155) else Color(0xFFF1F5F9)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isPlayer) Text("🏃", fontSize = 24.sp)
                            else if (isGoal) Text("🚩", fontSize = 24.sp)
                        }
                    }
                }
            }
        }

        // D-Pad Controls
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BouncyButton(onClick = { move(-1, 0) }) { Text("▲ Up", color = Color.White, fontWeight = FontWeight.Bold) }
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                BouncyButton(onClick = { move(0, -1) }) { Text("◀ Left", color = Color.White, fontWeight = FontWeight.Bold) }
                BouncyButton(onClick = { move(0, 1) }) { Text("Right ▶", color = Color.White, fontWeight = FontWeight.Bold) }
            }
            Spacer(modifier = Modifier.height(4.dp))
            BouncyButton(onClick = { move(1, 0) }) { Text("▼ Down", color = Color.White, fontWeight = FontWeight.Bold) }
        }
    }
}

// 10. Focus Breathing Circle (4-4-4 Box Breathing)
@Composable
fun FocusBreathingGame(
    languageCode: String,
    onWin: () -> Unit
) {
    var currentPhaseIdx by remember { mutableIntStateOf(0) }
    var secondsLeft by remember { mutableIntStateOf(4) }
    var cycleCount by remember { mutableIntStateOf(0) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val phases = listOf(
        BoonStrings.get("game_breathe_in", languageCode),
        BoonStrings.get("game_breathe_hold", languageCode),
        BoonStrings.get("game_breathe_out", languageCode),
        BoonStrings.get("game_breathe_rest", languageCode)
    )

    LaunchedEffect(Unit) {
        var phaseCounter = 0
        while (true) {
            currentPhaseIdx = phaseCounter % phases.size
            AudioSynthesizer.playTone(432.0 + (currentPhaseIdx * 20), 400)
            for (sec in 4 downTo 1) {
                secondsLeft = sec
                delay(1000)
            }
            phaseCounter++
            cycleCount++
            if (cycleCount >= 4) {
                onWin()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(BoonStrings.get("game_breathe_title", languageCode), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF06B6D4))

        Box(
            modifier = Modifier
                .size(240.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(
                    androidx.compose.ui.graphics.Brush.radialGradient(
                        colors = listOf(Color(0xFF22D3EE), Color(0xFF0891B2))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(phases[currentPhaseIdx], fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(6.dp))
                Text("$secondsLeft", fontSize = 38.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
        }

        Text(BoonStrings.get("game_breathe_benefit", languageCode), fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center)
    }
}
