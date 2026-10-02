package com.example.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import com.example.ui.components.BoonLogo
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.audio.AudioSynthesizer
import com.example.data.i18n.BoonStrings
import com.example.ui.components.BouncyButton
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import java.io.File
import java.io.FileOutputStream

@Composable
fun OnboardingDialog(
    languageCode: String,
    onComplete: (name: String, avatar: String, photoUri: String?) -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("Cosmo Explorer") }
    var selectedAvatar by remember { mutableStateOf("Fox") }
    var customPhotoUri by remember { mutableStateOf<String?>(null) }

    val mascots = listOf(
        Pair("Fox", "🦊"),
        Pair("Owl", "🦉"),
        Pair("Bear", "🐻"),
        Pair("Panda", "🐼"),
        Pair("Rabbit", "🐰"),
        Pair("Cat", "🐱"),
        Pair("Dragon", "🐲"),
        Pair("Robot", "🤖")
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            try {
                val file = File(context.cacheDir, "avatar_${System.currentTimeMillis()}.jpg")
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
                }
                customPhotoUri = file.absolutePath
                AudioSynthesizer.playCorrect()
            } catch (_: Exception) {}
        }
    }

    Dialog(onDismissRequest = { /* Must complete to proceed */ }) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Official Logo Emblem
                BoonLogo(size = 72.dp, elevation = 6.dp, shapeRadius = 20.dp)

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = BoonStrings.get("onboard_welcome", languageCode),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = EmeraldDark,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = BoonStrings.get("onboard_desc", languageCode),
                    fontSize = 12.sp,
                    color = Color(0xFF64748B),
                    textAlign = TextAlign.Center,
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Hero Name Input
                Text(
                    text = BoonStrings.get("onboard_name_label", languageCode),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Cosmo Explorer", color = Color(0xFF94A3B8)) },
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
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Mascot selector
                Text(
                    text = BoonStrings.get("onboard_mascot_label", languageCode),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(mascots) { (key, emoji) ->
                        val isSelected = selectedAvatar == key && customPhotoUri == null
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) EmeraldLight else Color(0xFFF1F5F9))
                                .border(
                                    width = if (isSelected) 2.5.dp else 1.dp,
                                    color = if (isSelected) EmeraldPrimary else Color.Transparent,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable {
                                    customPhotoUri = null
                                    selectedAvatar = key
                                    AudioSynthesizer.playPop()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(emoji, fontSize = 28.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Optional Custom Photo button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFE0F2FE))
                        .clickable { cameraLauncher.launch(null) }
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Default.CameraAlt, contentDescription = "Camera", tint = Color(0xFF0284C7))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (customPhotoUri != null) BoonStrings.get("onboard_photo_added", languageCode) else BoonStrings.get("onboard_custom_photo", languageCode),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0284C7)
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                // Start Adventure button
                BouncyButton(
                    onClick = {
                        onComplete(name.ifBlank { "Cosmo Explorer" }, selectedAvatar, customPhotoUri)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = EmeraldPrimary,
                    shadowColor = EmeraldDark
                ) {
                    Text(
                        text = BoonStrings.get("onboard_start_btn", languageCode),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
