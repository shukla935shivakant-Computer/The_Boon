package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.audio.AudioSynthesizer
import com.example.data.i18n.BoonStrings
import com.example.data.seed.StoryItem
import com.example.ui.components.BouncyButton
import com.example.ui.theme.AmberGold
import com.example.ui.theme.AmberLight
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RoseAccent
import com.example.ui.theme.RoseLight
import com.example.ui.theme.SkyBlueAccent
import com.example.ui.theme.SkyBlueLight

@Composable
fun StoriesScreen(
    stories: List<StoryItem>,
    activeStory: StoryItem?,
    isPlayingAudio: Boolean,
    languageCode: String,
    onOpenStory: (StoryItem) -> Unit,
    onCloseStory: () -> Unit,
    onToggleAudio: (StoryItem) -> Unit,
    onCheer: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF8B5CF6), Color(0xFF6D28D9))
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = BoonStrings.get("stories_banner_title", languageCode),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = BoonStrings.get("stories_banner_sub", languageCode),
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text("🎧", fontSize = 38.sp)
            }
        }

        // Stories list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            items(stories, key = { it.id }) { story ->
                StoryCard(
                    story = story,
                    languageCode = languageCode,
                    onOpen = { onOpenStory(story) },
                    onCheer = { onCheer(story.id) }
                )
            }
        }
    }

    if (activeStory != null) {
        StoryReaderDialog(
            story = activeStory,
            isPlaying = isPlayingAudio,
            languageCode = languageCode,
            onClose = onCloseStory,
            onToggleAudio = { onToggleAudio(activeStory) },
            onCheer = { onCheer(activeStory.id) }
        )
    }
}

@Composable
fun StoryCard(
    story: StoryItem,
    languageCode: String,
    onOpen: () -> Unit,
    onCheer: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpen() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFFEDE9FE)),
                contentAlignment = Alignment.Center
            ) {
                Text(story.emoji, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFEDE9FE))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(story.getCategory(languageCode), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6D28D9))
                    }

                    Text("⏱️ ${story.readMinutes} ${BoonStrings.get("min", languageCode)}", fontSize = 11.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = story.getTitle(languageCode),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF0F172A)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = story.getMoral(languageCode),
                    fontSize = 11.sp,
                    color = Color(0xFF64748B),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Cheer button
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(RoseLight)
                    .clickable { onCheer() }
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("❤️", fontSize = 12.sp)
                Spacer(modifier = Modifier.width(2.dp))
                Text("${story.cheersCount}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RoseAccent)
            }
        }
    }
}

@Composable
fun StoryReaderDialog(
    story: StoryItem,
    isPlaying: Boolean,
    languageCode: String,
    onClose: () -> Unit,
    onToggleAudio: () -> Unit,
    onCheer: () -> Unit
) {
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
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Text(story.emoji, fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = story.getTitle(languageCode),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF0F172A)
                            )
                            Text(story.getCategory(languageCode), fontSize = 11.sp, color = Color(0xFF6D28D9), fontWeight = FontWeight.Bold)
                        }
                    }

                    IconButton(onClick = onClose, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Moral Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFFEF3C7))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "🌱 ${BoonStrings.get("story_moral_label", languageCode)} ${story.getMoral(languageCode)}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Scrollable Story Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = story.getContent(languageCode),
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        color = Color(0xFF334155)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Bottom Controls: Listen TTS and Cheer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BouncyButton(
                        onClick = onToggleAudio,
                        backgroundColor = if (isPlaying) RoseAccent else EmeraldPrimary,
                        shadowColor = if (isPlaying) Color(0xFFBE123C) else EmeraldDark,
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isPlaying) BoonStrings.get("story_stop", languageCode) else BoonStrings.get("story_listen", languageCode),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(RoseLight)
                            .clickable { onCheer() }
                            .padding(horizontal = 14.dp, vertical = 12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("❤️", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("${story.cheersCount}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = RoseAccent)
                        }
                    }
                }
            }
        }
    }
}
