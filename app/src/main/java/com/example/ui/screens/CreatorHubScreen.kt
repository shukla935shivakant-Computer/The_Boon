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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.i18n.BoonStrings
import com.example.data.local.CreatorQuestEntity
import com.example.ui.components.BouncyButton
import com.example.ui.theme.AmberGold
import com.example.ui.theme.AmberLight
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RoseAccent
import com.example.ui.theme.RoseLight
import com.example.ui.theme.SkyBlueAccent
import com.example.ui.theme.SkyBlueLight

@Composable
fun CreatorHubScreen(
    creatorQuests: List<CreatorQuestEntity>,
    languageCode: String,
    onCreateQuest: (title: String, category: String, instructions: String, xp: Int, proofType: String) -> Unit,
    onLike: (Long) -> Unit,
    onBookmark: (Long) -> Unit
) {
    var isCreateDialogOpen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Creator Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(AmberSecondary, Color(0xFFD97706))
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
                        text = BoonStrings.get("creator_banner_title", languageCode),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = BoonStrings.get("creator_banner_sub", languageCode),
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    BouncyButton(
                        onClick = { isCreateDialogOpen = true },
                        backgroundColor = Color.White,
                        shadowColor = Color(0xFFB45309),
                        cornerRadius = 14.dp,
                        elevation = 4.dp
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = AmberSecondary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = BoonStrings.get("creator_design_new", languageCode),
                                color = AmberSecondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("🎨", fontSize = 38.sp)
            }
        }

        // Section Title
        Text(
            text = BoonStrings.get("creator_community_feed", languageCode),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
        )

        if (creatorQuests.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🌟", fontSize = 44.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(BoonStrings.get("creator_empty_title", languageCode), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF334155))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(BoonStrings.get("creator_empty_desc", languageCode), fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 90.dp)
            ) {
                items(creatorQuests, key = { it.id }) { quest ->
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(3.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val catKey = when (quest.category.lowercase()) {
                                    "nature" -> "cat_nature"
                                    "chores" -> "cat_chores"
                                    "gratitude" -> "cat_gratitude"
                                    "art" -> "cat_art"
                                    "social" -> "cat_social"
                                    else -> "cat_all"
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(AmberLight)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(BoonStrings.get(catKey, languageCode), color = Color(0xFFB45309), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                Text("+${quest.xpReward} XP", color = EmeraldDark, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = quest.title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = quest.instructions,
                                fontSize = 12.sp,
                                color = Color(0xFF475569),
                                lineHeight = 17.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val proofLabel = when {
                                    quest.proofType.contains("Camera", ignoreCase = true) || quest.proofType.contains("Photo", ignoreCase = true) -> BoonStrings.get("creator_proof_camera", languageCode)
                                    quest.proofType.contains("Voice", ignoreCase = true) -> BoonStrings.get("creator_proof_voice", languageCode)
                                    else -> BoonStrings.get("creator_proof_written", languageCode)
                                }
                                Text("${BoonStrings.get("creator_proof_type", languageCode)} $proofLabel", fontSize = 11.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    // Like button
                                    Row(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(RoseLight)
                                            .clickable { onLike(quest.id) }
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Like", tint = RoseAccent, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("${quest.likesCount}", fontSize = 11.sp, color = RoseAccent, fontWeight = FontWeight.Bold)
                                    }

                                    // Bookmark button
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(SkyBlueLight)
                                            .clickable { onBookmark(quest.id) }
                                            .padding(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (quest.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                            contentDescription = "Bookmark",
                                            tint = SkyBlueAccent,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (isCreateDialogOpen) {
        CreateQuestDialog(
            languageCode = languageCode,
            onDismiss = { isCreateDialogOpen = false },
            onCreate = { title, cat, inst, xp, proof ->
                onCreateQuest(title, cat, inst, xp, proof)
                isCreateDialogOpen = false
            }
        )
    }
}

@Composable
fun CreateQuestDialog(
    languageCode: String,
    onDismiss: () -> Unit,
    onCreate: (title: String, category: String, instructions: String, xp: Int, proofType: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var selectedCat by remember { mutableStateOf("Nature") }
    var selectedXp by remember { mutableIntStateOf(80) }
    var selectedProof by remember { mutableStateOf("Camera Photo") }

    val categories = listOf("Nature", "Chores", "Gratitude", "Art", "Social")
    val proofs = listOf("Camera Photo", "Voice Note", "Written Discovery")

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(BoonStrings.get("creator_dialog_title", languageCode), fontSize = 16.sp, fontWeight = FontWeight.Black, color = AmberSecondary)
                    IconButton(onClick = onDismiss, modifier = Modifier.size(30.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(BoonStrings.get("creator_placeholder_title", languageCode), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text(BoonStrings.get("creator_placeholder_title", languageCode), fontSize = 12.sp, color = Color(0xFF94A3B8)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF0F172A),
                        unfocusedTextColor = Color(0xFF0F172A),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = AmberSecondary,
                        unfocusedBorderColor = Color(0xFFCBD5E1),
                        cursorColor = AmberSecondary,
                        focusedPlaceholderColor = Color(0xFF94A3B8),
                        unfocusedPlaceholderColor = Color(0xFF94A3B8)
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF0F172A),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(BoonStrings.get("creator_category_label", languageCode), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    categories.take(3).forEach { cat ->
                        val catKey = when (cat.lowercase()) {
                            "nature" -> "cat_nature"
                            "chores" -> "cat_chores"
                            "gratitude" -> "cat_gratitude"
                            "art" -> "cat_art"
                            "social" -> "cat_social"
                            else -> "cat_all"
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (selectedCat == cat) AmberSecondary else Color(0xFFE2E8F0))
                                .clickable { selectedCat = cat }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(BoonStrings.get(catKey, languageCode), fontSize = 11.sp, color = if (selectedCat == cat) Color.White else Color(0xFF334155), fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(BoonStrings.get("creator_instructions_label", languageCode), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    placeholder = { Text(BoonStrings.get("creator_placeholder_desc", languageCode), fontSize = 12.sp, color = Color(0xFF94A3B8)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color(0xFF0F172A),
                        unfocusedTextColor = Color(0xFF0F172A),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = AmberSecondary,
                        unfocusedBorderColor = Color(0xFFCBD5E1),
                        cursorColor = AmberSecondary,
                        focusedPlaceholderColor = Color(0xFF94A3B8),
                        unfocusedPlaceholderColor = Color(0xFF94A3B8)
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF0F172A),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(BoonStrings.get("creator_proof_label", languageCode), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    proofs.forEach { proof ->
                        val proofLabel = when {
                            proof.contains("Camera", ignoreCase = true) || proof.contains("Photo", ignoreCase = true) -> BoonStrings.get("creator_proof_camera", languageCode)
                            proof.contains("Voice", ignoreCase = true) -> BoonStrings.get("creator_proof_voice", languageCode)
                            else -> BoonStrings.get("creator_proof_written", languageCode)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (selectedProof == proof) EmeraldPrimary else Color(0xFFE2E8F0))
                                .clickable { selectedProof = proof }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(proofLabel, fontSize = 10.sp, color = if (selectedProof == proof) Color.White else Color(0xFF334155), fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                BouncyButton(
                    onClick = {
                        if (title.isNotBlank() && instructions.isNotBlank()) {
                            onCreate(title, selectedCat, instructions, selectedXp, selectedProof)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = AmberSecondary,
                    shadowColor = Color(0xFFB45309)
                ) {
                    Text(BoonStrings.get("creator_publish_btn", languageCode), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}
