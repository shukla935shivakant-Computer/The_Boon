package com.example.ui.screens

import android.graphics.Bitmap
import android.media.MediaRecorder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.audio.AudioSynthesizer
import com.example.data.i18n.BoonStrings
import com.example.data.local.QuestEntity
import com.example.ui.components.BouncyButton
import com.example.ui.theme.AmberGold
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RoseAccent
import com.example.ui.theme.SkyBlueAccent
import com.example.ui.theme.VioletAccent
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestsScreen(
    quests: List<QuestEntity>,
    selectedCategory: String,
    activeProofQuest: QuestEntity?,
    languageCode: String,
    onCategorySelected: (String) -> Unit,
    onStartQuest: (QuestEntity) -> Unit,
    onSubmitProof: (questId: String, title: String, category: String, xp: Int, photoUri: String?, audioUri: String?, notes: String) -> Unit,
    onCloseProof: () -> Unit
) {
    val categories = listOf("All", "Nature", "Chores", "Gratitude", "Art", "Social")
    val filteredQuests = if (selectedCategory == "All") {
        quests
    } else {
        quests.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Hero Exploration Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(EmeraldLight)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = BoonStrings.get("quests_banner_title", languageCode),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = EmeraldDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = BoonStrings.get("quests_banner_sub", languageCode),
                        fontSize = 12.sp,
                        color = Color(0xFF334155),
                        lineHeight = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("🎒", fontSize = 38.sp)
            }
        }

        // Category filter chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { cat ->
                val isSelected = selectedCategory == cat
                val key = when (cat) {
                    "Nature" -> "cat_nature"
                    "Chores" -> "cat_chores"
                    "Gratitude" -> "cat_gratitude"
                    "Art" -> "cat_art"
                    "Social" -> "cat_social"
                    else -> "cat_all"
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) EmeraldPrimary else Color.White)
                        .clickable { onCategorySelected(cat) }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = BoonStrings.get(key, languageCode),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else Color(0xFF475569),
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Quests List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            items(filteredQuests, key = { it.id }) { quest ->
                QuestItemCard(
                    quest = quest,
                    languageCode = languageCode,
                    onStartQuest = { onStartQuest(quest) }
                )
            }
        }
    }

    // Modal Sheet for Proof Submission
    if (activeProofQuest != null) {
        ProofSubmissionDialog(
            quest = activeProofQuest,
            languageCode = languageCode,
            onSubmit = onSubmitProof,
            onDismiss = onCloseProof
        )
    }
}

@Composable
fun QuestItemCard(
    quest: QuestEntity,
    languageCode: String,
    onStartQuest: () -> Unit
) {
    val title = when (languageCode.lowercase()) {
        "hi" -> quest.titleHi
        "es" -> quest.titleEs
        "fr" -> quest.titleFr
        "zh" -> quest.titleZh
        else -> quest.titleEn
    }

    val desc = when (languageCode.lowercase()) {
        "hi" -> quest.descriptionHi
        "es" -> quest.descriptionEs
        "fr" -> quest.descriptionFr
        "zh" -> quest.descriptionZh
        else -> quest.descriptionEn
    }

    val (badgeBg, badgeText) = when (quest.difficulty.lowercase()) {
        "easy" -> Pair(Color(0xFFDCFCE7), Color(0xFF166534))
        "medium" -> Pair(Color(0xFFFEF3C7), Color(0xFFB45309))
        else -> Pair(Color(0xFFFFE4E6), Color(0xFF9F1239))
    }

    val categoryIcon = when (quest.category.lowercase()) {
        "nature" -> "🌿"
        "chores" -> "🧹"
        "gratitude" -> "🙏"
        "art" -> "🎨"
        "social" -> "🤝"
        else -> "⭐"
    }

    val categoryKey = when (quest.category.lowercase()) {
        "nature" -> "cat_nature"
        "chores" -> "cat_chores"
        "gratitude" -> "cat_gratitude"
        "art" -> "cat_art"
        "social" -> "cat_social"
        else -> "cat_all"
    }
    val localizedCategory = BoonStrings.get(categoryKey, languageCode)
    val localizedDifficulty = BoonStrings.get(quest.difficulty.lowercase(), languageCode)

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: Category, Difficulty, Estimated Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(categoryIcon, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = localizedCategory,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Difficulty pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(badgeBg)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = localizedDifficulty,
                            color = badgeText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Time estimate
                    Text(
                        text = "⏱️ ${quest.estimatedMinutes} ${BoonStrings.get("min", languageCode)}",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Quest Title
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Quest Description
            Text(
                text = desc,
                fontSize = 13.sp,
                color = Color(0xFF475569),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Bottom action row: XP reward & Start button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // XP Reward Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFFEF3C7))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "⭐ +${quest.xpReward} XP",
                        color = Color(0xFFB45309),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp
                    )
                }

                if (quest.isCompleted) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(EmeraldLight)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Completed",
                            tint = EmeraldDark,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = BoonStrings.get("completed_label", languageCode),
                            color = EmeraldDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                } else {
                    BouncyButton(
                        onClick = onStartQuest,
                        backgroundColor = EmeraldPrimary,
                        shadowColor = EmeraldDark,
                        cornerRadius = 14.dp,
                        elevation = 4.dp
                    ) {
                        Text(
                            text = BoonStrings.get("start_quest", languageCode),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProofSubmissionDialog(
    quest: QuestEntity,
    languageCode: String,
    onSubmit: (questId: String, title: String, category: String, xp: Int, photoUri: String?, audioUri: String?, notes: String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var notesText by remember { mutableStateOf("") }
    var capturedPhotoUri by remember { mutableStateOf<String?>(null) }
    var recordedAudioUri by remember { mutableStateOf<String?>(null) }
    var isRecordingAudio by remember { mutableStateOf(false) }
    var recorder by remember { mutableStateOf<MediaRecorder?>(null) }

    // Camera Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            try {
                val file = File(context.cacheDir, "proof_${System.currentTimeMillis()}.jpg")
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
                }
                capturedPhotoUri = file.absolutePath
                AudioSynthesizer.playCorrect()
            } catch (_: Exception) {}
        }
    }

    // Photo Gallery Launcher (Android Photo Picker)
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            capturedPhotoUri = uri.toString()
            AudioSynthesizer.playCorrect()
        }
    }

    Dialog(onDismissRequest = onDismiss) {
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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = BoonStrings.get("proof_title", languageCode),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = EmeraldDark
                        )
                        Text(
                            text = quest.getTitle(languageCode),
                            fontSize = 13.sp,
                            color = Color(0xFF64748B),
                            maxLines = 1
                        )
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Mandatory Proof Capture Buttons
                Text(
                    text = BoonStrings.get("submit_proof", languageCode),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Live Camera Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFE0F2FE))
                            .clickable {
                                cameraLauncher.launch(null)
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Camera",
                                tint = SkyBlueAccent
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = BoonStrings.get("proof_camera", languageCode),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SkyBlueAccent
                            )
                        }
                    }

                    // Gallery Picker
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFEDE9FE))
                            .clickable {
                                galleryLauncher.launch(
                                    androidx.activity.result.PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = "Gallery",
                                tint = VioletAccent
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = BoonStrings.get("proof_gallery", languageCode),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = VioletAccent
                            )
                        }
                    }

                    // Voice Recorder
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isRecordingAudio) Color(0xFFFFE4E6) else Color(0xFFFEF3C7))
                            .clickable {
                                if (!isRecordingAudio) {
                                    try {
                                        val audioFile = File(context.cacheDir, "voice_${System.currentTimeMillis()}.m4a")
                                        val rec = MediaRecorder().apply {
                                            setAudioSource(MediaRecorder.AudioSource.MIC)
                                            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                                            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                                            setOutputFile(audioFile.absolutePath)
                                            prepare()
                                            start()
                                        }
                                        recorder = rec
                                        isRecordingAudio = true
                                        recordedAudioUri = audioFile.absolutePath
                                        AudioSynthesizer.playPop()
                                    } catch (_: Exception) {}
                                } else {
                                    try {
                                        recorder?.stop()
                                        recorder?.release()
                                        recorder = null
                                        isRecordingAudio = false
                                        AudioSynthesizer.playCorrect()
                                    } catch (_: Exception) {
                                        isRecordingAudio = false
                                    }
                                }
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = if (isRecordingAudio) Icons.Default.Stop else Icons.Default.Mic,
                                contentDescription = "Voice",
                                tint = if (isRecordingAudio) RoseAccent else AmberSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isRecordingAudio) BoonStrings.get("recording_active", languageCode) else BoonStrings.get("proof_voice", languageCode),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isRecordingAudio) RoseAccent else AmberSecondary
                            )
                        }
                    }
                }

                // Active proof badges
                if (capturedPhotoUri != null || recordedAudioUri != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (capturedPhotoUri != null) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(EmeraldLight)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(BoonStrings.get("photo_attached", languageCode), fontSize = 11.sp, color = EmeraldDark, fontWeight = FontWeight.Bold)
                            }
                        }
                        if (recordedAudioUri != null && !isRecordingAudio) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(AmberGold.copy(alpha = 0.2f))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(BoonStrings.get("audio_recorded", languageCode), fontSize = 11.sp, color = Color(0xFFB45309), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Discovery Notes Field
                Text(
                    text = BoonStrings.get("proof_title", languageCode),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = notesText,
                    onValueChange = { notesText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    placeholder = {
                        Text(
                            BoonStrings.get("proof_notes_placeholder", languageCode),
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF0F172A),
                        unfocusedTextColor = Color(0xFF0F172A),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = EmeraldPrimary,
                        unfocusedBorderColor = Color(0xFFCBD5E1),
                        cursorColor = EmeraldPrimary,
                        focusedPlaceholderColor = Color(0xFF94A3B8),
                        unfocusedPlaceholderColor = Color(0xFF94A3B8)
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF0F172A),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Submit Proof Button
                BouncyButton(
                    onClick = {
                        onSubmit(
                            quest.id,
                            quest.getTitle(languageCode),
                            quest.category,
                            quest.xpReward,
                            capturedPhotoUri,
                            recordedAudioUri,
                            notesText.ifBlank { "Verified real-world quest execution!" }
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = EmeraldPrimary,
                    shadowColor = EmeraldDark
                ) {
                    Text(
                        text = "${BoonStrings.get("submit_action", languageCode)} (+${quest.xpReward} XP) 🎉",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
