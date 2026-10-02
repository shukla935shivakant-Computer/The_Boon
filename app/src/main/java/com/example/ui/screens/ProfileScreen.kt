package com.example.ui.screens

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.audio.AudioSynthesizer
import com.example.data.i18n.BoonStrings
import com.example.data.local.BadgeEntity
import com.example.data.local.CertificateEntity
import com.example.data.local.ProofSubmissionEntity
import com.example.data.local.UserProfileEntity
import com.example.data.repository.BoonRepository
import com.example.ui.components.BouncyButton
import com.example.ui.theme.AmberGold
import com.example.ui.theme.AmberLight
import com.example.ui.theme.AmberSecondary
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldLight
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RoseAccent
import com.example.ui.theme.SkyBlueAccent
import com.example.ui.theme.SkyBlueLight
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfileScreen(
    user: UserProfileEntity?,
    badges: List<BadgeEntity>,
    certificates: List<CertificateEntity>,
    submissions: List<ProofSubmissionEntity>,
    languageCode: String,
    onAvatarUpdated: (String, String?) -> Unit
) {
    val context = LocalContext.current
    var activeCertificate by remember { mutableStateOf<CertificateEntity?>(null) }

    val nextLevelXp = BoonRepository.getNextLevelTargetXp(user?.level ?: 1)
    val currentLevelMinXp = when (user?.level ?: 1) {
        1 -> 0
        2 -> 100
        3 -> 300
        4 -> 600
        else -> 1000
    }
    val progress = (((user?.xp ?: 0) - currentLevelMinXp).toFloat() / (nextLevelXp - currentLevelMinXp).coerceAtLeast(1).toFloat()).coerceIn(0f, 1f)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Hero Header Card
        item {
            Card(
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar & Mascot Circle
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(EmeraldLight)
                            .border(3.dp, EmeraldPrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        if (user?.customPhotoUri != null) {
                            AsyncImage(
                                model = user.customPhotoUri,
                                contentDescription = "Profile Photo",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            )
                        } else {
                            val emoji = when (user?.avatarKey) {
                                "Fox" -> "🦊"
                                "Owl" -> "🦉"
                                "Bear" -> "🐻"
                                "Panda" -> "🐼"
                                "Rabbit" -> "🐰"
                                "Cat" -> "🐱"
                                "Dragon" -> "🐲"
                                "Robot" -> "🤖"
                                else -> "🦊"
                            }
                            Text(emoji, fontSize = 46.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = user?.name ?: "Cosmo Explorer",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF0F172A)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "${BoonStrings.get("topbar_lvl", languageCode)} ${user?.level ?: 1} • ${BoonRepository.getLevelTitle(user?.level ?: 1, languageCode)}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldDark
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // XP Progress Bar
                    Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${user?.xp ?: 0} ${BoonStrings.get("xp_label", languageCode)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AmberSecondary)
                            Text("${BoonStrings.get("profile_next_level", languageCode)} $nextLevelXp ${BoonStrings.get("xp_label", languageCode)}", fontSize = 12.sp, color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            color = AmberSecondary,
                            trackColor = Color(0xFFE2E8F0)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Stats Row
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        StatPill(emoji = "🔥", value = "${user?.streakDays ?: 0} ${BoonStrings.get("profile_days", languageCode)}", label = BoonStrings.get("profile_streak_stat", languageCode))
                        StatPill(emoji = "📜", value = "${submissions.size}", label = BoonStrings.get("profile_solved_stat", languageCode))
                        StatPill(emoji = "🎖️", value = "${certificates.size}", label = BoonStrings.get("profile_certs_stat", languageCode))
                    }
                }
            }
        }

        // Section: Official 16:9 Landscape Certificates
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = BoonStrings.get("profile_certificates", languageCode),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = BoonStrings.get("profile_certificates_sub", languageCode),
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (certificates.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("📜", fontSize = 32.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(BoonStrings.get("profile_no_certs_title", languageCode), fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF334155))
                            Text(BoonStrings.get("profile_no_certs_desc", languageCode), fontSize = 11.sp, color = Color.Gray, textAlign = TextAlign.Center)
                        }
                    }
                } else {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(certificates) { cert ->
                            CertificateThumbnailCard(
                                certificate = cert,
                                languageCode = languageCode,
                                onOpen = {
                                    AudioSynthesizer.playPop()
                                    activeCertificate = cert
                                }
                            )
                        }
                    }
                }
            }
        }

        // Section: Trophy Cabinet (Badges)
        item {
            Spacer(modifier = Modifier.height(18.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = BoonStrings.get("profile_trophy", languageCode),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF0F172A)
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(badges) { badge ->
                        BadgeCard(badge = badge, languageCode = languageCode)
                    }
                }
            }
        }

        // Section: Proof History Gallery
        item {
            Spacer(modifier = Modifier.height(18.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = BoonStrings.get("profile_proof_gallery", languageCode),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF0F172A)
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (submissions.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(BoonStrings.get("profile_no_proofs", languageCode), fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center)
                    }
                }
            }
        }

        // Submissions items
        items(submissions) { sub ->
            SubmissionHistoryCard(submission = sub)
        }
    }

    // Active Certificate Full 16:9 Landscape Generator Dialog
    if (activeCertificate != null) {
        LandscapeCertificateDialog(
            certificate = activeCertificate!!,
            userName = user?.name ?: "Cosmo Explorer",
            languageCode = languageCode,
            onDismiss = { activeCertificate = null }
        )
    }
}

@Composable
fun StatPill(emoji: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(emoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(2.dp))
        Text(value, fontWeight = FontWeight.Black, fontSize = 14.sp, color = Color(0xFF0F172A))
        Text(label, fontSize = 10.sp, color = Color(0xFF64748B))
    }
}

@Composable
fun CertificateThumbnailCard(
    certificate: CertificateEntity,
    languageCode: String,
    onOpen: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp),
        modifier = Modifier
            .width(220.dp)
            .clickable { onOpen() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFFFBEB))
                    .border(2.dp, AmberGold, RoundedCornerShape(12.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🏅", fontSize = 22.sp)
                    Text(
                        BoonRepository.getCertificateTitle(certificate.title, languageCode),
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF78350F)
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text("${BoonStrings.get("cert_serial", languageCode)} ${certificate.certificateNo}", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Text("${BoonStrings.get("cert_date", languageCode)} ${certificate.dateEarned}", fontSize = 10.sp, color = Color.Gray)
        }
    }
}

@Composable
fun BadgeCard(badge: BadgeEntity, languageCode: String) {
    val title = when (languageCode.lowercase()) {
        "hi" -> badge.titleHi
        "es" -> badge.titleEs
        "fr" -> badge.titleFr
        "zh" -> badge.titleZh
        else -> badge.titleEn
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.width(130.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(if (badge.isUnlocked) Color(0xFFFEF3C7) else Color(0xFFE2E8F0)),
                contentAlignment = Alignment.Center
            ) {
                if (badge.isUnlocked) {
                    Text(badge.iconEmoji, fontSize = 26.sp)
                } else {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Locked", tint = Color.Gray, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = if (badge.isUnlocked) Color(0xFF0F172A) else Color.Gray,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = if (badge.isUnlocked) BoonStrings.get("badge_unlocked", languageCode) else BoonStrings.get("badge_locked", languageCode),
                fontSize = 10.sp,
                color = if (badge.isUnlocked) EmeraldDark else Color.Gray,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun SubmissionHistoryCard(submission: ProofSubmissionEntity) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (submission.photoUri != null) {
                AsyncImage(
                    model = submission.photoUri,
                    contentDescription = "Photo Proof",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(EmeraldLight),
                    contentAlignment = Alignment.Center
                ) {
                    Text("📷", fontSize = 22.sp)
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = submission.questTitle,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = submission.notes,
                    fontSize = 11.sp,
                    color = Color(0xFF475569),
                    maxLines = 2
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFFEF3C7))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("+${submission.xpEarned} XP", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB45309))
            }
        }
    }
}

// Official 16:9 Landscape Certificate Generator Dialog
@Composable
fun LandscapeCertificateDialog(
    certificate: CertificateEntity,
    userName: String,
    languageCode: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    fun generateAndSaveCertificateBitmap(cert: CertificateEntity, name: String, shareAfter: Boolean) {
        try {
            // High-resolution 16:9 landscape canvas (1920x1080)
            val width = 1920
            val height = 1080
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(bitmap)

            // Warm Cream Background
            canvas.drawColor(android.graphics.Color.parseColor("#FFFBEB"))

            val paint = Paint(Paint.ANTI_ALIAS_FLAG)

            // Ornate Outer Gold Border
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 16f
            paint.color = android.graphics.Color.parseColor("#D97706")
            canvas.drawRect(40f, 40f, (width - 40).toFloat(), (height - 40).toFloat(), paint)

            // Inner Fine Emerald Border
            paint.strokeWidth = 6f
            paint.color = android.graphics.Color.parseColor("#10B981")
            canvas.drawRect(70f, 70f, (width - 70).toFloat(), (height - 70).toFloat(), paint)

            // Certificate Header
            paint.style = Paint.Style.FILL
            paint.textAlign = Paint.Align.CENTER
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            paint.color = android.graphics.Color.parseColor("#047857")
            paint.textSize = 68f
            canvas.drawText(BoonStrings.get("cert_official_title", languageCode), (width / 2).toFloat(), 200f, paint)

            // Subtitle
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            paint.color = android.graphics.Color.parseColor("#64748B")
            paint.textSize = 34f
            canvas.drawText(BoonStrings.get("cert_alliance", languageCode), (width / 2).toFloat(), 260f, paint)

            // "This honors that"
            paint.color = android.graphics.Color.parseColor("#334155")
            paint.textSize = 36f
            canvas.drawText(BoonStrings.get("cert_honors", languageCode), (width / 2).toFloat(), 370f, paint)

            // Recipient Name
            paint.typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC)
            paint.color = android.graphics.Color.parseColor("#0F172A")
            paint.textSize = 86f
            canvas.drawText(name, (width / 2).toFloat(), 480f, paint)

            // Line under name
            paint.strokeWidth = 4f
            paint.color = android.graphics.Color.parseColor("#D97706")
            canvas.drawLine((width / 2 - 400).toFloat(), 510f, (width / 2 + 400).toFloat(), 510f, paint)

            // Criteria
            paint.style = Paint.Style.FILL
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            paint.color = android.graphics.Color.parseColor("#1E293B")
            paint.textSize = 38f
            canvas.drawText(BoonStrings.get("cert_achieved", languageCode), (width / 2).toFloat(), 590f, paint)

            val localizedCertTitle = BoonRepository.getCertificateTitle(cert.title, languageCode)
            val localizedCertCriteria = BoonRepository.getCertificateCriteria(cert.criteria, languageCode)

            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            paint.color = android.graphics.Color.parseColor("#047857")
            paint.textSize = 52f
            canvas.drawText(localizedCertTitle, (width / 2).toFloat(), 665f, paint)

            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            paint.color = android.graphics.Color.parseColor("#475569")
            paint.textSize = 32f
            canvas.drawText(localizedCertCriteria, (width / 2).toFloat(), 730f, paint)

            // Golden Sprout Seal Emblem on the left
            paint.color = android.graphics.Color.parseColor("#F59E0B")
            canvas.drawCircle(320f, 880f, 90f, paint)
            paint.color = android.graphics.Color.parseColor("#FEF3C7")
            canvas.drawCircle(320f, 880f, 75f, paint)
            paint.color = android.graphics.Color.parseColor("#047857")
            paint.textSize = 50f
            paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            canvas.drawText("🌱", 320f, 895f, paint)

            // Date & Serial No
            paint.textAlign = Paint.Align.LEFT
            paint.textSize = 28f
            paint.color = android.graphics.Color.parseColor("#64748B")
            canvas.drawText("${BoonStrings.get("cert_date", languageCode)} ${cert.dateEarned}", 440f, 870f, paint)
            canvas.drawText("${BoonStrings.get("cert_serial", languageCode)} ${cert.certificateNo}", 440f, 910f, paint)

            // Signature line on the right
            paint.textAlign = Paint.Align.CENTER
            paint.strokeWidth = 3f
            paint.color = android.graphics.Color.parseColor("#64748B")
            canvas.drawLine((width - 500).toFloat(), 880f, (width - 150).toFloat(), 880f, paint)
            paint.textSize = 26f
            canvas.drawText(BoonStrings.get("cert_signature", languageCode), (width - 325).toFloat(), 920f, paint)

            // Save to MediaStore (Pictures)
            val fileTitle = "Boon_Certificate_${cert.certificateNo}"
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$fileTitle.png")
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Boon")
                }
            }

            val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                context.contentResolver.openOutputStream(uri)?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                }
                AudioSynthesizer.playCorrect()
                Toast.makeText(context, "Certificate saved to Pictures/Boon!", Toast.LENGTH_SHORT).show()

                if (shareAfter) {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "image/png"
                        putExtra(Intent.EXTRA_STREAM, uri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share Official Certificate"))
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Could not save certificate: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = BoonStrings.get("profile_certificates", languageCode),
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = EmeraldDark
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(30.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                val previewCertTitle = BoonRepository.getCertificateTitle(certificate.title, languageCode)
                val previewCertCriteria = BoonRepository.getCertificateCriteria(certificate.criteria, languageCode)

                // 16:9 Landscape Canvas Preview
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFFFBEB))
                        .border(3.dp, AmberGold, RoundedCornerShape(16.dp))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(BoonStrings.get("cert_alliance", languageCode), fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color(0xFFD97706), letterSpacing = 2.sp)
                            Text(BoonStrings.get("cert_official_title", languageCode), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(BoonStrings.get("cert_honors", languageCode), fontSize = 10.sp, color = Color.Gray)
                            Text(userName, fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F172A))
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(previewCertTitle, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AmberSecondary)
                            Text(previewCertCriteria, fontSize = 9.sp, color = Color(0xFF475569), textAlign = TextAlign.Center, maxLines = 2)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🌱", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Column {
                                    Text("${BoonStrings.get("cert_serial", languageCode)} ${certificate.certificateNo}", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                                    Text("${BoonStrings.get("cert_date", languageCode)} ${certificate.dateEarned}", fontSize = 8.sp, color = Color.Gray)
                                }
                            }
                            Text(BoonStrings.get("cert_seal", languageCode), fontSize = 9.sp, fontWeight = FontWeight.Bold, color = EmeraldDark)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Download PNG and Print / Share Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    BouncyButton(
                        onClick = {
                            generateAndSaveCertificateBitmap(certificate, userName, shareAfter = false)
                        },
                        backgroundColor = EmeraldPrimary,
                        shadowColor = EmeraldDark,
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Download, contentDescription = "Download", tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(BoonStrings.get("cert_download", languageCode), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }

                    BouncyButton(
                        onClick = {
                            generateAndSaveCertificateBitmap(certificate, userName, shareAfter = true)
                        },
                        backgroundColor = SkyBlueAccent,
                        shadowColor = Color(0xFF0369A1),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(BoonStrings.get("cert_share", languageCode), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
